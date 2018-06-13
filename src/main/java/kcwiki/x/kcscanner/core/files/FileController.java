/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.files;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kcwiki.x.kcscanner.cache.inmem.AppDataCache;
import kcwiki.x.kcscanner.core.CoreInitializer;
import kcwiki.x.kcscanner.core.files.analyzer.FileAnalyzer;
import kcwiki.x.kcscanner.core.files.scanner.FileScanner;
import kcwiki.x.kcscanner.database.entity.FileDataEntity;
import kcwiki.x.kcscanner.database.entity.SystemScanEntity;
import kcwiki.x.kcscanner.database.service.FileDataService;
import kcwiki.x.kcscanner.database.service.LogService;
import kcwiki.x.kcscanner.database.service.SystemScanService;
import kcwiki.x.kcscanner.message.mail.EmailService;
import static kcwiki.x.kcscanner.tools.ConstantValue.FILE_SCANCORE;
import static kcwiki.x.kcscanner.tools.ConstantValue.SCANNAME_LASTMODIFIED;
import kcwiki.x.kcscanner.types.MsgTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author x5171
 */
@Component
public class FileController {
    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);
    
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
    
    private List<FileDataEntity> filePatchData = new ArrayList<>();
    
    public void startScanner() {
        List<FileDataEntity> fileDataList = fileScanner.preScan(FILE_SCANCORE);
        if(fileDataList != null && !fileDataList.isEmpty()) {
            Map<String, FileDataEntity> existDataList = fileDataService.getAll();
            if(!existDataList.isEmpty()){
                List<FileDataEntity> insertList = new ArrayList<>();
                List<FileDataEntity> updateList = new ArrayList<>();
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
                if(!insertList.isEmpty()){
                    fileDataService.insertSelected(insertList);
                }
                if(!updateList.isEmpty()){
                    fileDataService.updateSelected(updateList);
                }
            } else {
                fileDataService.insertSelected(fileDataList);
            }
        } else {
            emailService.sendSimpleEmail(MsgTypes.ERROR, "扫描文件数据时失败。");
        }
    }

    /**
     * @return the filePatchData
     */
    public List<FileDataEntity> getFilePatchData() {
        return filePatchData;
    }
    
    
}
