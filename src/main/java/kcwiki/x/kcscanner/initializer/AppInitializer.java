/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.initializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import kcwiki.x.kcscanner.cache.inmem.AppDataCache;
import kcwiki.x.kcscanner.cache.inmem.RuntimeValue;
import kcwiki.x.kcscanner.core.CoreInitializer;
import kcwiki.x.kcscanner.database.TableName;
import kcwiki.x.kcscanner.database.dao.UtilsMapper;
import kcwiki.x.kcscanner.database.service.SystemScanService;
import kcwiki.x.kcscanner.database.service.UtilsService;
import kcwiki.x.kcscanner.exception.BaseException;
import kcwiki.x.kcscanner.httpclient.HttpClientConfig;
import kcwiki.x.kcscanner.httpclient.HttpUtils;
import kcwiki.x.kcscanner.tools.ConstantValue;
import static kcwiki.x.kcscanner.tools.ConstantValue.LINESEPARATOR;
import kcwiki.x.kcscanner.types.ServiceTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author iHaru
 */
@Component
public class AppInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(AppInitializer.class);
    
    @Autowired
    AppConfigs appConfigs;
    @Autowired 
    RuntimeValue RUNTIMEValue;
    @Autowired
    UtilsMapper utilsMapper;
    @Autowired
    UtilsService utilsService;
    @Autowired
    SystemScanService systemScanService;
    @Autowired
    CoreInitializer coreInitializer;
    @Autowired
    HttpClientConfig httpClientConfig;
    
    boolean isInit = false;
    
    @PostConstruct
    public void initMethod() {
//        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "32");
        if(appConfigs == null){
            LOG.error("找不到程序主配置文件 程序初始化失败。");
        }
    }
    
    @PreDestroy
    public void destroyMethod() {
        
    }
    
    public void init(){
        LOG.info("KanColle Monitor: initialization started");
        isInit = true;
        long startTime=System.currentTimeMillis();
        AppDataCache.kcHost = appConfigs.getKcserver_host();
        checkDatabase();
        getKcServers();
        createFolder();
        AppDataCache.systemScanEntitys = systemScanService.getAll();
        coreInitializer.coreDataInit();
        long endTime=System.currentTimeMillis();
        LOG.info("Temp folder: {}", ConstantValue.TEMP_FOLDER);
        LOG.info("WebRoot folder: {}", RUNTIMEValue.WEBROOT_FOLDER);
        LOG.info("CoreListFilePath folder: {}", RUNTIMEValue.FILE_SCANCORE);
        LOG.info("AppRoot folder: {}", appConfigs.getSystem_user_dir());
        if (isInit) {
            AppDataCache.isAppInit = true;
            AppDataCache.isReadyReceive = true;
            AppDataCache.isScanTaskSuspend = false;
            LOG.info("KanColle Monitor: initialization completed in {} ms{}", endTime-startTime, LINESEPARATOR);
        } else {
            LOG.error("KanColle Monitor: initialization failed in {} ms{}", endTime-startTime, LINESEPARATOR);
            System.exit(0);
        }
    }
    
    private void getKcServers() {
        try {
            String repBody = HttpUtils.getHttpBody(appConfigs.getKcwiki_api_servers(), httpClientConfig.makeProxyConfig(false));
            LOG.debug(repBody);
            if(repBody == null)
                throw new BaseException(ServiceTypes.KanColleScanner, "尝试从KcApi获取服务器列表时发生错误。");
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
    
    private void createFolder(){
        File file;
        String filepath;
        filepath = ConstantValue.TEMP_FOLDER;
        file = new File(filepath);
        if(!file.exists()){
            file.mkdirs();
        }
        filepath = RUNTIMEValue.WORKSPACE_FOLDER;
        file = new File(filepath);
        if(!file.exists()){
            file.mkdirs();
        }
        filepath = RUNTIMEValue.PUBLISH_FOLDER;
        file = new File(filepath);
        if(!file.exists()){
            file.mkdirs();
        }
        filepath = RUNTIMEValue.DOWNLOAD_FOLDER;
        file = new File(filepath);
        if(!file.exists()){
            file.mkdirs();
        }
        filepath = RUNTIMEValue.PRIVATEDATA_FOLDER;
        file = new File(filepath);
        if(!file.exists()){
            file.mkdirs();
        }
        filepath = RUNTIMEValue.STORAGE_FOLDER;
        file = new File(filepath);
        if(!file.exists()){
            file.mkdirs();
        }
        filepath = RUNTIMEValue.TEMPLATE_FOLDER;
        file = new File(filepath);
        if(!file.exists()){
            file.mkdirs();
        }
    }
}
