/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.initializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.BooleanUtils;
import kcwiki.x.kcscanner.cache.inmem.AppDataCache;
import kcwiki.x.kcscanner.core.CoreInitializer;
import kcwiki.x.kcscanner.database.TableName;
import kcwiki.x.kcscanner.database.dao.UtilsMapper;
import kcwiki.x.kcscanner.database.service.SystemScanService;
import kcwiki.x.kcscanner.database.service.UtilsService;
import kcwiki.x.kcscanner.httpclient.HttpClientConfig;
import kcwiki.x.kcscanner.httpclient.HttpUtils;
import static kcwiki.x.kcscanner.tools.ConstantValue.LINESEPARATOR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author x5171
 */
@Component
public class AppInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(AppInitializer.class);
    
    @Autowired
    UtilsMapper utilsMapper;
    @Autowired
    AppConfigs appConfigs;
    @Autowired
    UtilsService utilsService;
    @Autowired
    SystemScanService systemScanService;
    @Autowired
    CoreInitializer coreInitializer;
    
    boolean isInit = false;
    
    public void init(){
        
        LOG.info("KanColle SenkaViewer: initialization started");
        AppDataCache.appConfigs = appConfigs;
        AppDataCache.systemScanEntitys = systemScanService.getAll();
        isInit = true;
        long startTime=System.currentTimeMillis();
        checkDatabase();
        getKcServers();
//        coreInitializer.coreDataInit();
        long endTime=System.currentTimeMillis();
        if (isInit) {
            LOG.info("KanColle SenkaViewer: initialization completed in {} ms{}", endTime-startTime, LINESEPARATOR);
        } else {
            LOG.error("KanColle SenkaViewer: initialization failed in {} ms{}", endTime-startTime, LINESEPARATOR);
            System.exit(0);
        }
    }
    
    private void getKcServers() {
        try {
            String repBody = HttpUtils.getHttpBody(AppDataCache.appConfigs.getKcwiki_api_servers(), HttpClientConfig.makeProxyConfig(false));
            LOG.debug(repBody);
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> servers = objectMapper.readValue(repBody,
                    new TypeReference<List<Map<String, Object>>>(){});
            servers.forEach((server) -> {
                AppDataCache.gameWorlds.put((Integer) server.get("id"), ((String) server.get("ip")).trim());
            });
            return;
        } catch (IOException ex) {
            LOG.error("系统初始化失败，获取KanColle游戏服务器列表时发生IOException。");
        }
        isInit = false;
    }
    
    private void checkDatabase() {
//        applicationContext.getAutowireCapableBeanFactory().autowireBean(utilsService);
        String tbname = TableName.getSystemLogTable();
        if(!utilsService.existTable(tbname)) {
            utilsService.createSystemLogTable(tbname);
        }
        tbname = TableName.getLastModifiedLogTable();
        if(!utilsService.existTable(tbname)) {
            utilsService.createDataLastmodifiedTable(tbname);
        }
        tbname = TableName.getDataLastmodifiedTable();
        if(!utilsService.existTable(tbname)) {
            utilsService.createDataLastmodifiedTable(tbname);
        }
        tbname = TableName.getDataStart2Table();
        if(!utilsService.existTable(tbname)) {
            utilsService.createDataStart2Table(tbname);
        }
        
        tbname = TableName.getSystemScanTable();
        if(!utilsService.existTable(tbname)) {
            utilsService.createSystemScanTable(tbname);
        }
    }
    
    
}
