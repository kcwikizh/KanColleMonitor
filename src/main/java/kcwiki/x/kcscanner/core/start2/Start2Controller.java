/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.start2;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.Date;
import org.apache.commons.lang3.exception.ExceptionUtils;
import kcwiki.x.kcscanner.cache.inmem.AppDataCache;
import kcwiki.x.kcscanner.core.start2.processor.Start2Utils;
import kcwiki.x.kcscanner.database.entity.Start2DataEntity;
import kcwiki.x.kcscanner.database.service.LogService;
import kcwiki.x.kcscanner.database.service.Start2DataService;
import kcwiki.x.kcscanner.database.service.SystemScanService;
import kcwiki.x.kcscanner.exception.ExceptionBase;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Start2;
import kcwiki.x.kcscanner.httpclient.impl.AutoLogin;
import kcwiki.x.kcscanner.initializer.AppConfigs;
import kcwiki.x.kcscanner.message.mail.EmailService;
import static kcwiki.x.kcscanner.tools.ConstantValue.SCANNAME_START2;
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
public class Start2Controller {
    private static final Logger LOG = LoggerFactory.getLogger(Start2Controller.class);
    
    @Autowired
    AppConfigs appConfigs;
    @Autowired
    AutoLogin autoLogin;
    @Autowired
    SystemScanService systemScanService;
    @Autowired
    Start2DataService start2DataService;
    @Autowired
    Start2Controller start2Controller;
    @Autowired
    Start2Utils start2Utils;
    @Autowired
    LogService logService;
    @Autowired
    EmailService emailService;
    
    private Start2 start2PatchData = null;
    
    public boolean getLatestStart2Data() {
        Date date = new Date();
        String start2raw = getStart2Data();
        if(start2raw != null) {
            Start2DataEntity latestData = start2DataService.getLatestData();
            if(latestData != null) {
                JsonNode patch = start2Utils.getPatch(latestData.getData(), start2raw);
                if(patch != null) {
                    start2PatchData = start2Utils.jsonnode2start2(patch);
                    insertStart2Data(start2raw, date);
                    return true;
                }
            } else {
                start2PatchData = start2Utils.start2pojo(start2raw);
                insertStart2Data(start2raw, date);
                return true;
            }
        } else {
            emailService.sendSimpleEmail(MsgTypes.ERROR, "扫描Start2数据时失败。");
        }
        return false;
    }
    
    private void insertStart2Data(String start2, Date date) {
        Start2DataEntity start2DataEntity = new Start2DataEntity();
        start2DataEntity.setData(start2);
        start2DataEntity.setTimestamp(date);
        start2DataService.insertOne(start2DataEntity);
    }
    
    public String getStart2Data() {
        autoLogin.setConfig(true);
        autoLogin.setUser_name(appConfigs.getKcserver_account_username());
        autoLogin.setUser_pwd(appConfigs.getKcserver_account_password());
        try{
            if(!autoLogin.netStart()) {
                LOG.warn("获取Start2数据时失败。");
                return null;
            }
        } catch (IOException | ExceptionBase ex) {
            if(ex instanceof ExceptionBase) {
                emailService.sendSimpleEmail(MsgTypes.DEBUG, ExceptionUtils.getStackTrace(ex));
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
     * @return the start2PatchData
     */
    public Start2 getStart2PatchData() {
        return start2PatchData;
    }
    
}
