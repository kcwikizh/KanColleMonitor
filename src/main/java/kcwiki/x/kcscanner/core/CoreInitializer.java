/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core;

import kcwiki.x.kcscanner.cache.inmem.AppDataCache;
import kcwiki.x.kcscanner.core.files.FileController;
import kcwiki.x.kcscanner.core.start2.Start2Controller;
import kcwiki.x.kcscanner.database.entity.SystemScanEntity;
import kcwiki.x.kcscanner.database.service.SystemScanService;
import static kcwiki.x.kcscanner.tools.ConstantValue.SCANNAME_LASTMODIFIED;
import static kcwiki.x.kcscanner.tools.ConstantValue.SCANNAME_START2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author x5171
 */
@Component
public class CoreInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(CoreInitializer.class);
    
    @Autowired
    FileController fileController;
    @Autowired
    Start2Controller start2Controller;
    @Autowired
    SystemScanService systemScanService;
    @Autowired
    ControlCenter controlCenter;
    
    public void coreDataInit(){
        SystemScanEntity systemScanEntity = AppDataCache.systemScanEntitys.get(SCANNAME_START2);
        if(systemScanEntity.getInit() != 1) {
            start2Controller.startScanner();
            systemScanEntity.setInit(1);
            systemScanService.updateOne(systemScanEntity);
        }
        systemScanEntity = AppDataCache.systemScanEntitys.get(SCANNAME_LASTMODIFIED);
        if(systemScanEntity.getInit() != 1) {
            fileController.startScanner();
            systemScanEntity.setInit(1);
            systemScanService.updateOne(systemScanEntity);
        }
        controlCenter.startDownload();
    }
    
    
}
