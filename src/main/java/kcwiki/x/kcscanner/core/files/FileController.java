/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.files;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledFuture;
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
import kcwiki.x.kcscanner.types.FileType;
import kcwiki.x.kcscanner.types.MessageLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
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
    
    private boolean isStartDownload = false;
    
    private List<FileDataEntity> filePatchData = new ArrayList<>();
    
    public void startScan(boolean isPreScan) {
        String host = appConfigs.getKcserver_host();
        if(!host.startsWith("http"))
            host = "http://" + host;
        List<FileDataEntity> fileDataList = fileScanner.preScan(host, runtimeValue.FILE_SCANCORE);
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
                        fileDataService.insertSelected((List<FileDataEntity>) insertList);
                    }
                    if(!updateList.isEmpty()){
                        fileDataService.updateSelected((List<FileDataEntity>) updateList);
                    }  
                });
                if(!isPreScan){
                    LOG.info("broadcast");
                    broadcast((List<FileDataEntity>) insertList.clone(), (List<FileDataEntity>) updateList.clone());
                }
                
            } else {
                CompletableFuture.runAsync(() -> {
                    fileDataService.insertSelected(fileDataList);
                });
            }
        } else {
            emailService.sendSimpleEmail(MessageLevel.ERROR, "扫描文件数据时失败。");
        }
    }
    
    public void autoScan(){
        Map<String, String> _map = fileScanner.ScanKcsConstFile();
        if(isStartDownload)
            return;
        List<String> servers = fileScanner.ScanServer(_map);
        if(servers.isEmpty())
            return;
        isStartDownload = true;
        String host = appConfigs.getKcserver_host();
        if(!host.startsWith("http"))
            host = "http://" + host;
        Map<String, FileDataEntity> existDataList = fileDataService.getTypeData(FileType.Core);
        List<FileDataEntity> result = fileScanner.scan(host, existDataList);
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
                fileDataService.insertSelected((List<FileDataEntity>) insertList);
            }
            if(!updateList.isEmpty()){
                fileDataService.updateSelected((List<FileDataEntity>) updateList);
            }   
        });
        broadcast(insertList, updateList);
        isStartDownload = false;
    }
    
    //https://www.baeldung.com/spring-scheduled-tasks
    //https://www.baeldung.com/spring-task-scheduler
    private void initTask(){
        ConcurrentTaskExecutor concurrentTaskExecutor = new ConcurrentTaskExecutor();

    }
    
    private void broadcast(List<FileDataEntity> insertList, List<FileDataEntity> updateList) {
        
    }

    /**
     * @return the filePatchData
     */
    public List<FileDataEntity> getFilePatchData() {
        return filePatchData;
    }
    
    
}
