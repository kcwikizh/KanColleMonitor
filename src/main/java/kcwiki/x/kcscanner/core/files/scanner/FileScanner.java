/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.files.scanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import kcwiki.x.kcscanner.cache.inmem.AppDataCache;
import kcwiki.x.kcscanner.cache.inmem.RuntimeValue;
import kcwiki.x.kcscanner.core.entity.CombinedShipEntity;
import kcwiki.x.kcscanner.database.entity.FileDataEntity;
import kcwiki.x.kcscanner.httpclient.HttpClientConfig;
import kcwiki.x.kcscanner.httpclient.HttpUtils;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_ship;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Start2;
import kcwiki.x.kcscanner.initializer.AppConfigs;
import kcwiki.x.kcscanner.tools.CommontUtils;
import kcwiki.x.kcscanner.tools.ConstantValue;
import static kcwiki.x.kcscanner.tools.ConstantValue.FILESEPARATOR;
import static kcwiki.x.kcscanner.tools.ConstantValue.TEMP_FOLDER;
import kcwiki.x.kcscanner.types.FileTypes;
import org.apache.commons.io.FileUtils;
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
    
    public List<FileDataEntity> preScan(String listfile) {
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
        String tempFolder = String.format("%s%s%s", TEMP_FOLDER, FILESEPARATOR, "prescan");
        if(!new File(tempFolder).exists())
                new File(tempFolder).mkdirs();
//        String url;
//        File _file;
//        String host = appConfigs.getKcserver_host();
//        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
//        RequestConfig requestConfig = httpClientConfig.makeProxyConfig(false);
        Date date = new Date();
        List<List<String>> _list = Lists.partition(downloadList, 10);
            CompletableFuture[] cfs  = _list.stream()
                .map(item -> CompletableFuture.supplyAsync(() -> downloadFile(item, tempFolder, date), executorService)
                                .whenComplete((s, e) -> {
                                    System.out.println("任务"+"核心文件下载"+"完成!result="+s.size()+"，异常 e="+e+","+new Date());
                                    result.addAll(s);
                                })
            ).toArray(CompletableFuture[]::new);
        if(cfs != null && cfs.length > 0){
            CompletableFuture.allOf(cfs).join();
            cfs = null;
        }
//        for(String line:lines) {
//            String prePath = line.substring(0, line.lastIndexOf("/"));
//            if(line.startsWith("http")){
//                url = line;
//                line = line.replace("http://", "");
//                prePath = line.substring(line.indexOf("/")+1, line.lastIndexOf("/"));
//            }else
//                url = String.format("http://%s/%s", host, line);
//            _file = new File(url);
//            
//            String filename = _file.getName();
//            String folder = String.format("%s/%s", tempFolder, prePath);
//            String path = String.format("%s/%s.png", folder, filename);
////            FileDataEntity fileDataEntity = HttpUtils.scanFile(url, closeableHttpClient, requestConfig, _file.getParent(), _file.getName());
//            FileDataEntity fileDataEntity = HttpUtils.downloadAndGetData(url, folder, filename, requestConfig, closeableHttpClient);
//            if(fileDataEntity == null)
//                continue;
//            fileDataEntity.setItemid(-1);
//            fileDataEntity.setFilename(filename);
//            fileDataEntity.setHash(CommontUtils.getFileHex(path));
//            fileDataEntity.setPath(line);
//            fileDataEntity.setTimestamp(date);
//            fileDataEntity.setType(FileTypes.Core);
//            result.add(fileDataEntity);
//        }
        return result;
    }
    
    private List<FileDataEntity> downloadFile(List<String> list, String tempFolder, Date date) {
        
        String url;
        File _file;
        String host = appConfigs.getKcserver_host();
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        RequestConfig requestConfig = httpClientConfig.makeProxyConfig(false);
        List<FileDataEntity> result = new ArrayList<>();
        for(String line:list) {
            String prePath = line.substring(0, line.lastIndexOf("/"));
            if(line.startsWith("http")){
                url = line;
                line = line.replace("http://", "");
                prePath = line.substring(line.indexOf("/")+1, line.lastIndexOf("/"));
            }else
                url = String.format("http://%s/%s", host, line);
            _file = new File(url);
            
            String filename = _file.getName();
            String folder = String.format("%s/%s", tempFolder, prePath);
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
            fileDataEntity.setType(FileTypes.Core);
            if(fileDataEntity.getHash() == null)
                continue;
            result.add(fileDataEntity);
        }
        return result;
    }
    
    public List<FileDataEntity> fileScan(List<FileDataEntity> list) {
        List<FileDataEntity> result = new ArrayList<>();
        
        String tempFolder = String.format("%s%s%s", TEMP_FOLDER, FILESEPARATOR, "prescan");
        if(!new File(tempFolder).exists())
                new File(tempFolder).mkdirs();
        String url;
        File _file;
        String host = appConfigs.getKcserver_host();
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        RequestConfig requestConfig = httpClientConfig.makeProxyConfig(false);
        for(FileDataEntity _fileDataEntity:list) {
            url = String.format("http://%s/%s", host, _fileDataEntity.getPath());
            _file = new File(url);
            boolean isModified = HttpUtils.checkModified(url, _fileDataEntity.getLastmodified(), closeableHttpClient, requestConfig);
            if(!isModified)
                continue;
            FileDataEntity fileDataEntity = HttpUtils.scanFile(url, closeableHttpClient, requestConfig, url2path(_fileDataEntity.getPath()), _file.getName());
            if(fileDataEntity == null)
                continue;
            fileDataEntity.setFilename(_file.getName());
            fileDataEntity.setPath(_fileDataEntity.getPath());
            result.add(fileDataEntity);
        }
        return result;
    }
    
    private String url2path(String url) {
        File file = new File(url);
        return file.getParent();
    }
    
}
