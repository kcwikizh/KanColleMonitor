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
import kcwiki.x.kcscanner.core.downloader.Start2Downloader;
import kcwiki.x.kcscanner.core.files.FileController;
import kcwiki.x.kcscanner.core.start2.Start2Controller;
import kcwiki.x.kcscanner.core.start2.processor.Start2Analyzer;
import kcwiki.x.kcscanner.core.start2.processor.Start2Utils;
import kcwiki.x.kcscanner.database.entity.SystemScanEntity;
import kcwiki.x.kcscanner.database.service.SystemScanService;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Start2;
import static kcwiki.x.kcscanner.tools.ConstantValue.LINESEPARATOR;
import static kcwiki.x.kcscanner.tools.ConstantValue.SCANNAME_LASTMODIFIED;
import static kcwiki.x.kcscanner.tools.ConstantValue.SCANNAME_START2;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
    @Autowired
    Start2Analyzer start2Analyzer;
    @Autowired
    Start2Downloader start2Downloader;
    @Autowired
    Start2Utils start2Utils;
    ObjectMapper objectMapper = new ObjectMapper();
    
    public void coreDataInit(){
        Date date = new Date();
        SystemScanEntity systemScanEntity = AppDataCache.systemScanEntitys.get(SCANNAME_START2);
        if(systemScanEntity == null || systemScanEntity.getInit() != 1) {
            start2Controller.getLatestStart2Data();
            AppDataCache.start2data = start2Controller.getStart2Data();
            start2Analyzer.getDiffStart2(null, new Start2(),  AppDataCache.start2data);
            start2Downloader.download(start2Analyzer.getStart2PatchEntity(), false);
            
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
            fileController.startScanner();
            systemScanEntity = new SystemScanEntity();
            systemScanEntity.setInit(1);
            systemScanEntity.setName(SCANNAME_LASTMODIFIED);
            systemScanEntity.setLastmodified(date);
            systemScanEntity.setTimeline(addTimeLine(null, date.getTime()));
            systemScanService.insertOne(systemScanEntity);
            AppDataCache.systemScanEntitys.put(SCANNAME_LASTMODIFIED, systemScanEntity);
        }
        controlCenter.startDownload();
    }
    
    private String addTimeLine(String json, long timestamp){
        List<Long> _list = null;
        if(json == null){
            _list = new ArrayList();
        } else {
            try {
                _list = objectMapper.readValue(json,
                    new TypeReference<List<Long>>(){});
            } catch (IOException ex) {
                LOG.error("获取Start2原始数据转换为POJO时发生错误，数据格式可能有更改。错误代码为： {}{}", 
                        LINESEPARATOR,
                        ExceptionUtils.getStackTrace(ex));
            }
        }
        _list.add(timestamp);
        return start2Utils.object2str(_list);
    }
}
