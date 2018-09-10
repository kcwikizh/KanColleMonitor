/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import javax.annotation.PostConstruct;
import kcwiki.x.kcscanner.cache.inmem.RuntimeValue;
import kcwiki.x.kcscanner.core.files.processor.FileAnalyzer;
import kcwiki.x.kcscanner.core.files.scanner.FileScanner;
import kcwiki.x.kcscanner.database.entity.FileDataEntity;
import kcwiki.x.kcscanner.database.service.FileDataService;
import kcwiki.x.kcscanner.database.service.LogService;
import kcwiki.x.kcscanner.database.service.SystemScanService;
import kcwiki.x.kcscanner.initializer.AppConfigs;
import kcwiki.x.kcscanner.message.mail.EmailService;
import kcwiki.x.kcscanner.message.websocket.MessagePublisher;
import kcwiki.x.kcscanner.message.websocket.entity.DownLoadResult;
import kcwiki.x.kcscanner.message.websocket.types.WebsocketMessageType;
import static kcwiki.x.kcscanner.tools.ConstantValue.TEMP_FOLDER;
import kcwiki.x.kcscanner.types.FileType;
import kcwiki.x.kcscanner.types.MessageLevel;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.stereotype.Component;

/**
 *
 * @author x5171
 */
@Component
public class FileController {
    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);
    
    @Autowired
    AppConfigs appConfigs;
    @Autowired
    RuntimeValue runtimeValue;
    @Autowired
    FileScanner fileScanner;
    @Autowired
    FileDataService fileDataService;
    @Autowired
    SystemScanService systemScanService;
    @Autowired
    FileAnalyzer fileAnalyzer;
    @Autowired
    LogService logService;
    @Autowired
    EmailService emailService;
    @Autowired
    private MessagePublisher messagePublisher;
    
    private boolean isStartDownload = false;
    private String downloadFolder;
    private String publishFolder;
    private String host;
    
    private List<FileDataEntity> filePatchData = new ArrayList<>();
    
    @PostConstruct
    public void initMethod() {
        host = appConfigs.getKcserver_host();
        if(!host.startsWith("http"))
            host = "http://" + host;
        downloadFolder = runtimeValue.DOWNLOAD_FOLDER;
        publishFolder = runtimeValue.PUBLISH_FOLDER;
    }
    
    public void startScan(boolean isPreScan) {
        String tempFolder;
        if(isPreScan){
            tempFolder = String.format("%s/%s", TEMP_FOLDER, "prescan");
        } else {
            tempFolder = String.format("%s/%s", TEMP_FOLDER, "scan");
        }
        if(!new File(tempFolder).exists())
                new File(tempFolder).mkdirs();
        List<FileDataEntity> fileDataList = fileScanner.preScan(tempFolder, host, runtimeValue.FILE_SCANCORE);
        if(fileDataList != null && !fileDataList.isEmpty()) {
            Map<String, FileDataEntity> existDataList = fileDataService.getTypeData(FileType.Core);
            if(!existDataList.isEmpty()){
                ArrayList<FileDataEntity> insertList = new ArrayList<>();
                ArrayList<FileDataEntity> updateList = new ArrayList<>();
                fileDataList.forEach(f -> {
                    String path = f.getPath();
                    if(existDataList.containsKey(path)){
                        if(!existDataList.get(path).getHash().equals(f.getHash())){
                            updateList.add(f);
                        }
                    } else {
                        insertList.add(f);
                    }
                });
                
                CompletableFuture.runAsync(() -> {
                    if(!insertList.isEmpty()){
                        fileDataService.insertSelected((List<FileDataEntity>) insertList.clone());
                    }
                    if(!updateList.isEmpty()){
                        fileDataService.updateSelected((List<FileDataEntity>) updateList.clone());
                    }  
                });
                if(!isPreScan){
                    broadcast(copyFiles(tempFolder, publishFolder, insertList, updateList), FileType.Core);
                }
            } else {
                fileDataService.insertSelected(fileDataList);
            }
        } else {
            emailService.sendSimpleEmail(MessageLevel.ERROR, "扫描文件数据时失败。");
        }
    }
    
    public void autoScan(){
        Map<String, String> _map = fileScanner.ScanKcsConstFile();
        if(_map == null || isStartDownload)
            return;
        List<String> servers = fileScanner.ScanServer(_map);
        if(servers.isEmpty())
            return;
        String tempFolder = String.format("%s/%s", TEMP_FOLDER, "autoscan");
        if(!new File(tempFolder).exists())
                new File(tempFolder).mkdirs();
        isStartDownload = true;
        
        Map<String, FileDataEntity> existDataList = fileDataService.getTypeData(FileType.Core);
        List<FileDataEntity> result = fileScanner.scan(tempFolder, host, existDataList);
        ArrayList<FileDataEntity> insertList = new ArrayList<>();
        ArrayList<FileDataEntity> updateList = new ArrayList();
        result.forEach(item -> {
            String path = item.getPath();
            if(existDataList.containsKey(path)){
                if(!existDataList.get(path).getHash().equals(item.getHash())){
                    updateList.add(item);
                }
            } else {
                insertList.add(item);
            }
        });
        CompletableFuture.runAsync(() -> {
            if(!insertList.isEmpty()){
                fileDataService.insertSelected((List<FileDataEntity>) insertList.clone());
            }
            if(!updateList.isEmpty()){
                fileDataService.updateSelected((List<FileDataEntity>) updateList.clone());
            }  
        });
        broadcast(copyFiles(tempFolder, publishFolder, insertList, updateList), FileType.Core);
        isStartDownload = false;
    }
    
    private Map<String, List<String>> copyFiles(String srcRoot, String destRoot, List<FileDataEntity> insertList, List<FileDataEntity> updateList){
        List<String> newfile = new ArrayList();
        List<String> modifiedfile = new ArrayList();
        Map<String, List<String>> result = new HashMap();
        if(!insertList.isEmpty()){
            insertList.forEach(item -> {
                String path = item.getPath();
                String parentPath;
                if(path.startsWith("http")){
                    path = path.replace("https://", "").replace("http://", "");
                    parentPath = path.substring(path.indexOf("/")+1, path.lastIndexOf("/"));
                } else {
                    parentPath = path.substring(0, path.lastIndexOf("/"));
                }
                String relativePath = String.format("%s/%s", parentPath, item.getFilename());
                String srcfile = String.format("%s/%s", srcRoot, relativePath);
                relativePath = String.format("corefile/new/%s", relativePath);
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
                String path = item.getPath();
                String parentPath;
                if(path.startsWith("http")){
                    path = path.replace("https://", "").replace("http://", "");
                    parentPath = path.substring(path.indexOf("/")+1, path.lastIndexOf("/"));
                } else {
                    parentPath = path.substring(0, path.lastIndexOf("/"));
                }
                String relativePath = String.format("%s/%s", parentPath, item.getFilename());
                String srcfile = String.format("%s/%s", srcRoot, relativePath);
                relativePath = String.format("corefile/modified/%s", relativePath);
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
    
    //https://www.baeldung.com/spring-scheduled-tasks
    //https://www.baeldung.com/spring-task-scheduler
    private void initTask(){
        ConcurrentTaskExecutor concurrentTaskExecutor = new ConcurrentTaskExecutor();
//        concurrentTaskExecutor.execute(r);
    }
    
    private void broadcast(Map<String, List<String>> fileList, FileType fileType) {
        LOG.info("broadcast");
        if(fileList.containsKey("New")){
            DownLoadResult downLoadResult = new DownLoadResult();
            downLoadResult.setType(fileType);
            downLoadResult.setFilelist(fileList.get("New"));
            messagePublisher.publish(downLoadResult, WebsocketMessageType.KanColleScanner_Download_Result);
        }
        if(fileList.containsKey("Modified")){
            DownLoadResult downLoadResult = new DownLoadResult();
            downLoadResult.setType(fileType);
            downLoadResult.setFilelist(fileList.get("Modified"));
            messagePublisher.publish(downLoadResult, WebsocketMessageType.KanColleScanner_Download_Result);
        }
    }

    /**
     * @return the filePatchData
     */
    public List<FileDataEntity> getFilePatchData() {
        return filePatchData;
    }
    
    
}
