/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.start2;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.exception.ExceptionUtils;
import kcwiki.x.kcscanner.cache.inmem.AppDataCache;
import kcwiki.x.kcscanner.cache.inmem.RuntimeValue;
import static kcwiki.x.kcscanner.constant.ConstantValue.SCANNAME_START2;
import kcwiki.x.kcscanner.core.downloader.Start2Downloader;
import kcwiki.x.kcscanner.core.downloader.entity.DownloadStatus;
import kcwiki.x.kcscanner.core.entity.CombinedShipEntity;
import kcwiki.x.kcscanner.core.entity.Start2PatchEntity;
import kcwiki.x.kcscanner.core.entity.lua.ship.Ship;
import kcwiki.x.kcscanner.core.entity.lua.ship.Slotitem;
import kcwiki.x.kcscanner.core.start2.processor.Start2Analyzer;
import kcwiki.x.kcscanner.core.start2.processor.Start2Utils;
import kcwiki.x.kcscanner.database.entity.FileDataEntity;
import kcwiki.x.kcscanner.database.entity.Start2DataEntity;
import kcwiki.x.kcscanner.database.service.FileDataService;
import kcwiki.x.kcscanner.database.service.LogService;
import kcwiki.x.kcscanner.database.service.Start2DataService;
import kcwiki.x.kcscanner.exception.BaseException;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_slotitem;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Start2;
import kcwiki.x.kcscanner.httpclient.impl.AutoLogin;
import kcwiki.x.kcscanner.httpclient.impl.UploadStart2;
import kcwiki.x.kcscanner.initializer.AppConfig;
import kcwiki.x.kcscanner.message.mail.EmailService;
import kcwiki.x.kcscanner.message.websocket.MessagePublisher;
import kcwiki.x.kcscanner.message.websocket.entity.DownLoadResult;
import kcwiki.x.kcscanner.message.websocket.types.ModuleType;
import kcwiki.x.kcscanner.types.FileType;
import kcwiki.x.kcscanner.types.MessageLevel;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.iharu.spring.SpringUtils;
import org.iharu.util.ZipCompressorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author iHaru
 */
@Component
public class Start2Controller {
    private static final Logger LOG = LoggerFactory.getLogger(Start2Controller.class);
    
    @Autowired
    private AppConfig appConfig;
    @Autowired
    RuntimeValue runtimeValue;
    @Autowired
    private Start2DataService start2DataService;
    @Autowired
    private UploadStart2 uploadStart2;
    @Autowired
    FileDataService fileDataService;
    @Autowired
    private LogService logService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private MessagePublisher messagePublisher;
    
    private Start2 start2Data = null;
    private Start2 prevStart2Data = null;
    private Date prevStart2DataTimestamp = null;
    private String downloadFolder;
    private String publishFolder;
    private String host;
    
    @PostConstruct
    public void initMethod() {
        host = appConfig.getKcserver_host();
        if(!host.startsWith("http"))
            host = "http://" + host;
        downloadFolder = runtimeValue.DOWNLOAD_FOLDER;
        publishFolder = runtimeValue.PUBLISH_FOLDER;
        if(!new File(downloadFolder).exists())
            new File(downloadFolder).mkdirs();
        if(!new File(publishFolder).exists())
            new File(publishFolder).mkdirs();
    }
    
    public boolean getLatestStart2Data() {
        Date date = new Date();
        String start2raw = fetchStart2OnlineData();

        if(!StringUtils.isBlank(start2raw)) {
            CompletableFuture.runAsync(() -> {
                StringBuilder sb = new StringBuilder(start2raw);
                boolean isUploaded = uploadStart2.upload(sb);
            });
            
            start2Data = Start2Utils.start2pojo(start2raw);
            AppDataCache.start2data = start2Data;
            Start2DataEntity start2DataEntity = start2DataService.getLatestData();
            messagePublisher.publish("start2获取成功", ModuleType.SystemInfo);
            if(start2DataEntity != null) {
                String prevStart2 = start2DataEntity.getData();
                prevStart2Data = Start2Utils.start2pojo(prevStart2);
                prevStart2DataTimestamp = start2DataEntity.getTimestamp();
                messagePublisher.publish("旧start2获取成功，时间戳为： "+prevStart2DataTimestamp, ModuleType.SystemInfo);
                JsonNode patch = Start2Utils.getPatch(prevStart2, start2raw);
                if(patch != null) {
                    messagePublisher.publish("patch条数为： "+patch.size(), ModuleType.SystemInfo);
                    insertStart2Data(start2raw, date);
                    return true;
                }
            } else {
                insertStart2Data(start2raw, date);
                return true;
            }
        } else {
            emailService.sendSimpleEmail(MessageLevel.ERROR, "扫描Start2数据时失败。");
        }
        return false;
    }
    
    /**
     * 
     * @param source 旧的Start2数据
     * @param target 新的Start2数据
     * @param isPreDownload
     * @param isSeasonal 
     */
    private void downloadFile(Start2 source, Start2 target, boolean isPreDownload, boolean isSeasonal, Map<Integer, String> kcdata){
        if(target == null){
            LOG.error("target Start2 can not be null。");
            return;
        }
        if(source == null)
            source = new Start2();
        if(new File(downloadFolder).exists()) {
            try {
                FileUtils.deleteDirectory(new File(downloadFolder));
                new File(downloadFolder).mkdirs();
            } catch (IOException ex) {
                LOG.error("无法删除downloadFolder。\r\n {}", ExceptionUtils.getStackTrace(ex));
            }
        }
        Start2Analyzer start2Analyzer = SpringUtils.getBean(Start2Analyzer.class);
        Start2Downloader start2Downloader = SpringUtils.getBean(Start2Downloader.class);
        start2Downloader.setSeasonal(isSeasonal);
        start2Downloader.setGameid2wikiid(kcdata);
        
        Start2PatchEntity start2PatchEntity = start2Analyzer.getDiffStart2(null, source,  target);
        if(start2PatchEntity != null){
            start2Downloader.setDownloadFolder(downloadFolder);
            start2Downloader.download(start2PatchEntity, isPreDownload);
//            genWikiLuaTable(start2PatchEntity);
            drawConclusion(start2Downloader, isPreDownload);
            saveData(start2Downloader, isPreDownload);
            start2Downloader.getDownloadResult().clear();
            start2Downloader.getFileResult().clear();
        }
    }
    
    /**
     * 
     * @param source 旧用于对比的Start2文件
     * @param target 最新用于下载资源的Start2文件
     * @param isSeasonal
     * @param kcdata
     */
    public void downloadFile(Start2 source, Start2 target, boolean isSeasonal, Map<Integer, String> kcdata) {
        downloadFile(source ,target, false, isSeasonal, kcdata);
    }
    
    public void downloadFile(boolean isPreScan, boolean isSeasonal){
        messagePublisher.publish("原旧Start2文件时间戳为： "+prevStart2DataTimestamp.toString(), ModuleType.SystemInfo);
        Start2DataEntity start2DataEntity = start2DataService.getLatestData();
        messagePublisher.publish("现旧Start2文件时间戳为： "+start2DataEntity.getTimestamp().toString(), ModuleType.SystemInfo);
        downloadFile(Start2Utils.start2pojo(start2DataEntity.getData()), start2Data, isPreScan, isSeasonal, null);
    }
    
    public void downloadFile(boolean isSeasonal){
        downloadFile(Start2Utils.start2pojo(start2DataService.getLatestData().getData()) , start2Data, false, isSeasonal, null);
    }
    
    private void genWikiLuaTable(Start2PatchEntity start2PatchEntity){
        Map<Integer, Ship> ships = new LinkedHashMap();
        Map<Integer, Slotitem> slotitems = new LinkedHashMap();
        start2PatchEntity.getNewShip().forEach(item -> {
            Ship ship = new Ship();
            ship.setId(item.getApi_id());
            ship.setSortno(item.getApi_sortno());
            ship.setJp(item.getApi_name());
            ship.setYomi(item.getApi_yomi());
            ship.setStype(item.getApi_stype());
            
            
            
        });
        start2PatchEntity.getNewSlotitem().forEach(item->{
        
        });
    }
    
    private void drawConclusion(Start2Downloader start2Downloader, boolean isPreDownload){
        Map<FileType, List<DownloadStatus>> downloadResult = start2Downloader.getDownloadResult();
        Map<FileType, List<FileDataEntity>> fileResult = start2Downloader.getFileResult();
//        LOG.info("downloadResult: {}", downloadResult.size());
        downloadResult.forEach((k, v) -> {
            List<DownloadStatus> insertList = new ArrayList();
            List<DownloadStatus> updateList = new ArrayList();
            v.forEach(item -> {
                if(!item.isIsSuccess())
                    return;
                if(item.isIsNew()){
                    insertList.add(item);
                } else {
                    updateList.add(item);
                }
            });
            int successCount = fileResult.get(k).size();
            int sumCount = v.size();
            messagePublisher.publish("Start2文件 "+k.getName()+" 下载完成："+successCount+"/"+sumCount, ModuleType.DownloadLog);
            broadcast(copyFiles(publishFolder, insertList, updateList), k);
        });
        LOG.info("start2文件下载完成");
        ZipCompressorUtils.createZip(downloadFolder, runtimeValue.WORKSPACE_FOLDER, "editorialfile.zip");
        messagePublisher.publish("start2文件打包完成 请前往下载", ModuleType.SystemInfo);
        LOG.info("start2文件打包完成 请前往下载");
    }
    
    private void saveData(Start2Downloader start2Downloader, boolean isPreDownload) {
        Map<FileType, List<FileDataEntity>> fileResult = start2Downloader.getFileResult();
//        LOG.info("fileResult: {}", fileResult.size());
        ArrayList<FileDataEntity> insertList = new ArrayList();
        ArrayList<FileDataEntity> updateList = new ArrayList();
        for(FileType type:fileResult.keySet()){
            Map<String, FileDataEntity> existDataList = fileDataService.getTypeData(type);
            Set<String> existHashList = new HashSet();
            if(existDataList != null && !existDataList.isEmpty()){
                existDataList.forEach((k, v) -> {
                    existHashList.add(v.getHash());
                });
                fileResult.get(type).forEach(f -> {
                    String path = f.getPath();
                    String hash = f.getHash();
                    if(existHashList.contains(hash))
                        return;
                    if(existDataList.containsKey(path)){
                        if(!existDataList.get(path).getHash().equals(hash)){
                            updateList.add(f);
                        }
                    } else {
                        insertList.add(f);
                    }
                });
            } else {
                insertList.addAll(fileResult.get(type));
            }
            if(!insertList.isEmpty()){
                fileDataService.insertSelected(insertList);
                insertList.clear();
            }
            if(!updateList.isEmpty()){
                fileDataService.updateSelected(updateList);
                updateList.clear();
            }
        }
        LOG.info("文件数据保存完成");
    }
    
    private Map<String, List<String>> copyFiles(String destRoot, List<DownloadStatus> insertList, List<DownloadStatus> updateList){
        List<String> newfile = new ArrayList();
        List<String> modifiedfile = new ArrayList();
        Map<String, List<String>> result = new HashMap();
        if(!insertList.isEmpty()){
            insertList.forEach(item -> {
                String relativePath = String.format("%s/%s", item.getParentPath(), item.getFilename());
                String srcfile = item.getPath();
                relativePath = String.format("start2file/new/%s", relativePath);
                String destfile = String.format("%s/%s", destRoot, relativePath);
                try {
                    FileUtils.copyFile(new File(srcfile), new File(destfile));
                } catch (IOException ex) {
                    LOG.error("尝试拷贝文件失败。 src：{}， dest：{}", srcfile, destfile);
                    return;
                }
                newfile.add(relativePath);
            });
            result.put("New", newfile);
        }
        if(!updateList.isEmpty()){
            updateList.forEach(item -> {
                String relativePath = String.format("%s/%s", item.getParentPath(), item.getFilename());
                String srcfile = item.getPath();
                relativePath = String.format("start2file/modified/%s", relativePath);
                String destfile = String.format("%s/%s", destRoot, relativePath);
                try {
                    FileUtils.copyFile(new File(srcfile), new File(destfile));
                } catch (IOException ex) {
                    LOG.error("尝试拷贝文件失败。 src：{}， dest：{}", srcfile, destfile);
                    return;
                }
                modifiedfile.add(relativePath);
            });
            result.put("Modified", modifiedfile);
        }
        return result;
    }
    
    private void broadcast(Map<String, List<String>> fileList, FileType fileType) {
        LOG.debug("broadcast");
        if(fileList.containsKey("New")){
            DownLoadResult downLoadResult = new DownLoadResult();
            downLoadResult.setType(fileType);
            downLoadResult.setFilelist(fileList.get("New"));
//            messagePublisher.publish(downLoadResult, ModuleType.DownloadResult);
        }
        if(fileList.containsKey("Modified")){
            DownLoadResult downLoadResult = new DownLoadResult();
            downLoadResult.setType(fileType);
            downLoadResult.setFilelist(fileList.get("Modified"));
//            messagePublisher.publish(downLoadResult, ModuleType.DownloadResult);
        }
    }
    
    private void insertStart2Data(String start2, Date date) {
        Start2DataEntity start2DataEntity = new Start2DataEntity();
        start2DataEntity.setData(start2);
        start2DataEntity.setTimestamp(date);
        start2DataService.insertOne(start2DataEntity);
    }
    
    public String fetchStart2OnlineData() {
        AutoLogin autoLogin = SpringUtils.getBean(AutoLogin.class);
        if(appConfig.isAllow_use_proxy()){
            autoLogin.setConfig(true);
        }else{ 
            autoLogin.setConfig(false);
        }
        autoLogin.setUser_name(appConfig.getKcserver_account_username());
        autoLogin.setUser_pwd(appConfig.getKcserver_account_password());
        try{
            if(!autoLogin.netStart()) {
                LOG.warn("获取Start2数据时失败。");
                return null;
            }
        } catch (IOException | BaseException ex) {
            if(ex instanceof BaseException) {
                emailService.sendSimpleEmail(MessageLevel.DEBUG, ExceptionUtils.getStackTrace(ex));
            }
        }
        String rawStart2 = autoLogin.getBuffer().toString();
        if(rawStart2.contains("svdata")){
            rawStart2 = rawStart2.substring(rawStart2.indexOf("svdata=")+66, rawStart2.length()-1);
        }
        AppDataCache.stringCache.put(SCANNAME_START2, rawStart2);
        return rawStart2;
    }

    /**
     * @return the start2Data
     */
    public Start2 getStart2Data() {
        return start2Data;
    }

    /**
     * @return the prevStart2Data
     */
    public Start2 getPrevStart2Data() {
        return prevStart2Data;
    }

    /**
     * @return the prevStart2DataTimestamp
     */
    public Date getPrevStart2DataTimestamp() {
        return prevStart2DataTimestamp;
    }
    
}
