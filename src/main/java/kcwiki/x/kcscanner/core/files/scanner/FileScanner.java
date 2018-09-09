/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.files.scanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import javax.script.ScriptException;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import kcwiki.x.kcscanner.cache.inmem.AppDataCache;
import kcwiki.x.kcscanner.cache.inmem.RuntimeValue;
import kcwiki.x.kcscanner.database.entity.FileDataEntity;
import kcwiki.x.kcscanner.httpclient.HttpClientConfig;
import kcwiki.x.kcscanner.httpclient.HttpUtils;
import kcwiki.x.kcscanner.initializer.AppConfigs;
import kcwiki.x.kcscanner.message.websocket.MessagePublisher;
import kcwiki.x.kcscanner.message.websocket.types.WebsocketMessageType;
import kcwiki.x.kcscanner.tools.CommontUtils;
import static kcwiki.x.kcscanner.tools.ConstantValue.TEMP_FOLDER;
import kcwiki.x.kcscanner.tools.ScriptUtils;
import kcwiki.x.kcscanner.types.FileType;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author x5171
 */
@Component
public class FileScanner {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(FileScanner.class);
    
    @Autowired
    AppConfigs appConfigs;  
    @Autowired
    RuntimeValue runtimeValue;
    @Autowired
    HttpClientConfig httpClientConfig;
    @Autowired
    ScriptUtils scriptEngineUtils;
    @Autowired
    MessagePublisher messagePublisher;
    
    public List<FileDataEntity> preScan(String tempFolder, String host, String listfile) {
        List<String> downloadList = new ArrayList<>();
        List<FileDataEntity> result = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            downloadList = objectMapper.readValue(FileUtils.readFileToString(new File(listfile), StandardCharsets.UTF_8), 
                    new TypeReference<List<String>>(){});
        } catch (IOException ex) {
            LOG.error("preScan - 读取{}文件时失败。", listfile);
            LOG.error(ExceptionUtils.getStackTrace(ex));
        }
        Date date = new Date();
        List<List<String>> _list = Lists.partition(downloadList, 10);
            CompletableFuture[] cfs  = _list.stream()
                .map(item -> CompletableFuture.supplyAsync(() -> distributedDownloadFile(host, item, tempFolder, date), executorService)
                                .whenComplete((s, e) -> {
                                    System.out.println("任务"+"核心文件下载"+"完成!result="+s.size()+"，异常 e="+e+","+new Date());
                                    result.addAll(s);
                                })
            ).toArray(CompletableFuture[]::new);
        if(cfs != null && cfs.length > 0){
            CompletableFuture.allOf(cfs).join();
            cfs = null;
        }
        return result;
    }
    
    private List<FileDataEntity> distributedDownloadFile(String host, List<String> list, String tempFolder, Date date) {
        String url;
        File _file;
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        RequestConfig requestConfig;
        if(appConfigs.isAllow_use_proxy()){
            requestConfig = httpClientConfig.makeProxyConfig(true);
        } else {
            requestConfig = httpClientConfig.makeProxyConfig(false);
        }
        List<FileDataEntity> result = new ArrayList<>();
        for(final String line:list) {
            if(line.startsWith("http")){
                url = line;
            } else {
                url = String.format("%s/%s", host, line);
            }
            _file = new File(url);
            String _url = url.replace("https://", "").replace("http://", "");
            String parentPath = _url.substring(_url.indexOf("/")+1, _url.lastIndexOf("/"));
            String filename = _file.getName();
            String folder = String.format("%s/%s", tempFolder, parentPath);
            String path = String.format("%s/%s", folder, filename);
//            FileDataEntity fileDataEntity = HttpUtils.scanFile(url, closeableHttpClient, requestConfig, _file.getParent(), _file.getName());
            FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, filename, requestConfig, closeableHttpClient);
            if(fileDataEntity == null)
                continue;
            fileDataEntity.setItemid(-1);
            fileDataEntity.setFilename(filename);
            fileDataEntity.setHash(CommontUtils.getFileHex(path));
            fileDataEntity.setPath(line);
            fileDataEntity.setTimestamp(date);
            fileDataEntity.setType(FileType.Core);
            if(fileDataEntity.getHash() == null)
                continue;
            result.add(fileDataEntity);
        }
        return result;
    }
    
    private List<String> distributedPreDownload(String host, List<FileDataEntity> list){
        List<String> downloadList = new ArrayList<>();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        RequestConfig requestConfig;
        if(appConfigs.isAllow_use_proxy()){
            requestConfig = httpClientConfig.makeProxyConfig(true);
        } else {
            requestConfig = httpClientConfig.makeProxyConfig(false);
        }
        list.forEach(item -> {
            String url = null;
            final String path = item.getPath();
            if(path.startsWith("http")){
                url = path;
            } else {
                url = String.format("%s/%s", host, path);
            }
            boolean isModified = HttpUtils.checkModified(url, item.getLastmodified(), httpclient, requestConfig);
            if(isModified){
                downloadList.add(path);
            }
        });
        return downloadList;
    }
    
    public List<FileDataEntity> scan(String tempFolder, String host, Map<String, FileDataEntity> existDataList){
        List<String> preresult = new ArrayList<>();
        List<FileDataEntity> result = new ArrayList();
        ExecutorService executorService = Executors.newFixedThreadPool(16);

        CompletableFuture[] cfs  = 
                Lists.partition(
                        existDataList.values().stream().collect(Collectors.toList())
                        , 10
                )
                .stream().map(item -> CompletableFuture.supplyAsync(() -> distributedPreDownload(host, item), executorService)
                                .whenComplete((s, e) -> {
                                    System.out.println("任务"+"核心文件下载"+"完成!result="+s.size()+"，异常 e="+e+","+new Date());
                                    preresult.addAll(s);
                                })
            ).toArray(CompletableFuture[]::new);
        if(cfs != null && cfs.length > 0){
            CompletableFuture.allOf(cfs).join();
            cfs = null;
        }
        if(preresult.isEmpty())
            return result;
        
        Date date = new Date();
        List<List<String>> _list = Lists.partition(preresult, 10);
        cfs  = _list.stream()
            .map(item -> CompletableFuture.supplyAsync(() -> distributedDownloadFile(host, item, tempFolder, date), executorService)
            .whenComplete((s, e) -> {
                System.out.println("任务"+"核心文件下载"+"完成!result="+s.size()+"，异常 e="+e+","+new Date());
                result.addAll(s);
            })
        ).toArray(CompletableFuture[]::new);
        if(cfs != null && cfs.length > 0){
            CompletableFuture.allOf(cfs).join();
            cfs = null;
        }
        return result;
    }      
    
    public List<String> ScanServer(Map<String, String> serverList){
        List<String> result = new ArrayList();
        RequestConfig requestConfig;
        if(appConfigs.isAllow_use_proxy()){
            requestConfig = httpClientConfig.makeProxyConfig(true);
        } else {
            requestConfig = httpClientConfig.makeProxyConfig(false);
        }
        int version = (int) (Math.random()*90 + 10);
            if(AppDataCache.worldVersionCache.isEmpty()){
                serverList.forEach((k, v) -> {
                    String url = String.format("%s/%s?version=%d", v, "kcs2/version.json", version);
                    String content = HttpUtils.getHttpBody(url, requestConfig);
                    if(content == null)
                        return;
                    AppDataCache.worldVersionCache.put(k, content);
                });
            } else {
                serverList.forEach((k, v) -> {
                    String url = String.format("%s/%s?version=%d", v, "kcs2/version.json", version);
                    String content = HttpUtils.getHttpBody(url, requestConfig);
                    String precontent = AppDataCache.worldVersionCache.get(k);
                    if(content == null)
                        return;
                    if(precontent == null){
                        AppDataCache.worldVersionCache.put(k, content);
                        return;
                    }
                    boolean hasPatch = hasPatch(precontent, content);
                    if(hasPatch){
                        AppDataCache.worldVersionCache.put(k, content);
                        result.add(v);
                    }
                });
            }
        return result;
    }
    
    public Map<String, String> ScanKcsConstFile(){
        Map<String, String> result = new HashMap();
        try {
            scriptEngineUtils.initScriptEngine("http://203.104.209.7/gadget_html5/js/kcs_const.js", null); 
            ScriptObjectMirror obj = (ScriptObjectMirror) scriptEngineUtils.getScriptProperty("MaintenanceInfo");
            if(obj == null)
                return null;
            long st = (long) (double)  obj.get("StartDateTime");
            long et = (long) (double)  obj.get("EndDateTime");
            int _IsDoing = (int)  obj.get("IsDoing");
            int _IsEmergency = (int)  obj.get("IsEmergency");
            long duration = et - st;
            
            Locale exampleLocale = Locale.GERMANY;
            TimeZone zone = TimeZone.getTimeZone("UTC");

            Calendar theCalendar = Calendar.getInstance(zone, exampleLocale);
            theCalendar.setTime(new Date(duration));

            String MD = String.format("预计维护时间为：%d天 %d小时 %d分钟", theCalendar.get(Calendar.DAY_OF_MONTH)-1, theCalendar.get(Calendar.HOUR_OF_DAY), theCalendar.get(Calendar.MINUTE));
            
            if(AppDataCache.maintenanceInfo.isEmpty()){
                AppDataCache.maintenanceInfo.put("StartDateTime", String.valueOf(st));
                AppDataCache.maintenanceInfo.put("EndDateTime", String.valueOf(et));
                AppDataCache.maintenanceInfo.put("IsDoing", String.valueOf(_IsDoing));
                AppDataCache.maintenanceInfo.put("IsEmergency", String.valueOf(_IsEmergency));
            } else {
                StringBuilder sb = new StringBuilder();
                if(!AppDataCache.maintenanceInfo.get("StartDateTime").equals(String.valueOf(st))){
                    sb.append("开始维护时间为： ").append(date2string(st));
                }
                if(!AppDataCache.maintenanceInfo.get("EndDateTime").equals(String.valueOf(et))){
                    sb.append("结束维护时间为： ").append(date2string(et));
                }
                if(!AppDataCache.maintenanceInfo.get("IsDoing").equals(String.valueOf(_IsDoing))){
                    sb.append("目前状态为： ").append((_IsDoing == 1? "维护中":"维护完毕"));
                }
//                if(!AppDataCache.maintenanceInfo.get("IsEmergency").equals(String.valueOf(_IsEmergency))){
//                    sb.append("是否紧急维护： ").append((_IsEmergency == 1? "是":"否"));
//                }
                String msg = sb.toString();
                if(!StringUtils.isBlank(msg))
                    messagePublisher.publish(msg, WebsocketMessageType.KanColleScanner_Auto_FileScan);
            }
            
            
            obj = (ScriptObjectMirror) scriptEngineUtils.getScriptProperty("ConstServerInfo");
            obj.forEach((k, v) -> {
                String item = (String) v;
                if(k.contains("World_"))
                    result.put(k, (item.endsWith("/")?item.substring(0, item.length()-1):item));
            });
        } catch (ScriptException | NoSuchMethodException | FileNotFoundException ex) {
            LOG.error(ExceptionUtils.getStackTrace(ex));
        }
        return result;
    }
    
    public static boolean hasPatch(String scrStr, String tarStr) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode source = mapper.readTree(scrStr);
            JsonNode target = mapper.readTree(tarStr);
            JsonNode patch = JsonDiff.asJson(source, target);
            return patch.size() > 0;
        } catch (IOException ex) {
            LOG.error(ExceptionUtils.getStackTrace(ex));
        }
        return false;
    }
    
    private String date2string(long duration){
        Locale exampleLocale = Locale.JAPAN;
        TimeZone zone = TimeZone.getTimeZone("JST");
        Calendar theCalendar = Calendar.getInstance(zone, exampleLocale);
        theCalendar.setTime(new Date(duration));
        return theCalendar.get(Calendar.MONTH)+"月" 
                + theCalendar.get(Calendar.DAY_OF_MONTH)+"日" 
                + theCalendar.get(Calendar.HOUR_OF_DAY) + "时"
                + theCalendar.get(Calendar.MINUTE) + "分";
    }
    
    private String url2path(String url) {
        File file = new File(url);
        return file.getParent();
    }
    
}
