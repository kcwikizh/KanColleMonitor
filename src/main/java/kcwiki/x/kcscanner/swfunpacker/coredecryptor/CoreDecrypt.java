/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.swfunpacker.coredecryptor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import kcwiki.x.kcscanner.cache.inmem.AppDataCache;
import kcwiki.x.kcscanner.message.websocket.MessagePublisher;
import static kcwiki.x.kcscanner.tools.ConstantValue.LINESEPARATOR;
import static kcwiki.x.kcscanner.tools.ConstantValue.TEMPLATE_FOLDER;
import kcwiki.x.kcscanner.types.PublishStatus;
import kcwiki.x.kcscanner.types.PublishTypes;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author VEP
 */
public class CoreDecrypt {
    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(CoreDecrypt.class);
    
    @Autowired
    MessagePublisher messagePublisher;
    
    private final String tppath;
    private final String ffpath;
    private String mapCode;
    private String soundCode;
    private BufferedReader Bfr;
    private final String kcserver;
    private boolean isMapNumNull=false;
    private final Runtime runtime;
    public static HashMap<String, String> shipAddressList = new LinkedHashMap<>();
    public static HashMap<String, String> shipDataList = new LinkedHashMap<>();
    public static HashMap<String, String> mapAddressList = new LinkedHashMap<>();
    private static String timeStamp;
    
    public CoreDecrypt(){
        this.tppath=TEMPLATE_FOLDER;
        this.ffpath=AppDataCache.appConfigs.getFile_ffdec();
        this.kcserver=AppDataCache.appConfigs.getKcserver_host();
        runtime=Runtime.getRuntime();
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
