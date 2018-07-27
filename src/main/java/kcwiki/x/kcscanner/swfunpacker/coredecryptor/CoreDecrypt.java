/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.swfunpacker.coredecryptor;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.annotation.PostConstruct;
import kcwiki.x.kcscanner.cache.inmem.RuntimeValue;
import kcwiki.x.kcscanner.initializer.AppConfigs;
import kcwiki.x.kcscanner.message.websocket.MessagePublisher;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author VEP
 */
@Component
public class CoreDecrypt {
    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(CoreDecrypt.class);
    
    @Autowired
    AppConfigs appConfigs;
    @Autowired
    RuntimeValue runtimeValue;
    @Autowired
    MessagePublisher messagePublisher;
    
    private String tppath;
    private String ffpath;
    private String kcserver;
    private String mapCode;
    private String soundCode;
    private BufferedReader Bfr;
    private boolean isMapNumNull = false;
    private final Runtime runtime = Runtime.getRuntime();
    public static HashMap<String, String> shipAddressList = new LinkedHashMap<>();
    public static HashMap<String, String> shipDataList = new LinkedHashMap<>();
    public static HashMap<String, String> mapAddressList = new LinkedHashMap<>();
    private static String timeStamp;
    
    @PostConstruct
    public void initMethod() {
        tppath = runtimeValue.TEMPLATE_FOLDER;
        ffpath = appConfigs.getFile_ffdec();
        kcserver = appConfigs.getKcserver_host();
    }
    
    
    
    
    public boolean getData(String map,String sound){
        
        return true;
    }
    
    public boolean ecryptMap(String str,String mapId) {
        return false;
    }

    public boolean ecryptSound(String str) {
        return false;
    }

    /**
     * @return the timeStamp
     */
    public static String getTimeStamp() {
        return timeStamp;
    }

    /**
     * @param aTimeStamp the timeStamp to set
     */
    public static void setTimeStamp(String aTimeStamp) {
        timeStamp = aTimeStamp;
    }

}
