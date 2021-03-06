/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import kcwiki.x.kcscanner.cache.inmem.AppDataCache;
import static kcwiki.x.kcscanner.constant.ConstantValue.SCANNAME_LASTMODIFIED;
import static kcwiki.x.kcscanner.constant.ConstantValue.SCANNAME_START2;
import kcwiki.x.kcscanner.core.files.FileController;
import kcwiki.x.kcscanner.core.start2.Start2Controller;
import kcwiki.x.kcscanner.database.entity.SystemScanEntity;
import kcwiki.x.kcscanner.database.service.SystemScanService;
import kcwiki.x.kcscanner.initializer.AppConfig;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iharu.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author iHaru
 */
@Component
public class CoreInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(CoreInitializer.class);
    
    @Autowired
    AppConfig appConfig;
    @Autowired
    FileController fileController;
    @Autowired
    Start2Controller start2Controller;
    @Autowired
    SystemScanService systemScanService;
    @Autowired
    ControlCenter controlCenter;
    
    ObjectMapper objectMapper = new ObjectMapper();
    
    public void coreDataInit(){
        Date date = new Date();
        SystemScanEntity systemScanEntity = AppDataCache.systemScanEntitys.get(SCANNAME_START2);
        if(systemScanEntity == null || systemScanEntity.getInit() != 1) {
            start2Controller.getLatestStart2Data();
            AppDataCache.start2data = start2Controller.getStart2Data();
            start2Controller.downloadFile(true, false);
            
            systemScanEntity = new SystemScanEntity();
            systemScanEntity.setInit(1);
            systemScanEntity.setName(SCANNAME_START2);
            systemScanEntity.setLastmodified(date);
            systemScanEntity.setTimeline(addTimeLine(null, date.getTime()));
            systemScanService.insertOne(systemScanEntity);
            AppDataCache.systemScanEntitys.put(SCANNAME_START2, systemScanEntity);
        }
        systemScanEntity = AppDataCache.systemScanEntitys.get(SCANNAME_LASTMODIFIED);
        if(systemScanEntity == null || systemScanEntity.getInit() != 1) {
            fileController.startScan(true);
            systemScanEntity = new SystemScanEntity();
            systemScanEntity.setInit(1);
            systemScanEntity.setName(SCANNAME_LASTMODIFIED);
            systemScanEntity.setLastmodified(date);
            systemScanEntity.setTimeline(addTimeLine(null, date.getTime()));
            systemScanService.insertOne(systemScanEntity);
            AppDataCache.systemScanEntitys.put(SCANNAME_LASTMODIFIED, systemScanEntity);
        }
        
    }
    
    private String addTimeLine(String json, long timestamp){
        List<Long> _list = null;
        if(json == null){
            _list = new ArrayList();
        } else {
            try {
                _list = objectMapper.readValue(json,
                    new TypeReference<List<Long>>(){});
                _list.add(timestamp);
            } catch (IOException ex) {
                LOG.error(ExceptionUtils.getStackTrace(ex));
            }
        }
        return JsonUtils.object2json(_list);
    }
    
    
}
