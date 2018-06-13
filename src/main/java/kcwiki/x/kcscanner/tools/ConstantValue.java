/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.tools;

import java.io.File;
import kcwiki.x.kcscanner.cache.inmem.AppDataCache;

/**
 *
 * @author x5171
 */
public class ConstantValue {
    public final static String LINESEPARATOR = System.getProperty("line.separator", "\n");
    public final static String FILESEPARATOR = File.separator;
    public static final String CLASSPATH = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    public static final String WEBROOT = CLASSPATH.replace("/WEB-INF/classes", "");
    public final static String TEMP_FOLDER = 
            String.format("%s%s", System.getProperty("java.io.tmpdir"), "/kcscanner");

    public final static String PRIVATEDATA_FOLDER = 
            String.format("%s%s", CLASSPATH, AppDataCache.appConfigs.getFolder_privatedata());
    public final static String DOWNLOAD_FOLDER = 
            String.format("%s%s", CLASSPATH, AppDataCache.appConfigs.getFolder_download());
    public final static String TEMPLATE_FOLDER = 
            String.format("%s%s", CLASSPATH, AppDataCache.appConfigs.getFolder_template());
    
    public final static String PUBLISH_FOLDER = 
            String.format("%s%s", WEBROOT, AppDataCache.appConfigs.getFolder_template());
    public final static String WORKSPACE_FOLDER = 
            String.format("%s%s", WEBROOT, AppDataCache.appConfigs.getFolder_template());
    
    public final static String FILE_SCANCORE = 
            String.format("%s%s", CLASSPATH, AppDataCache.appConfigs.getFile_filelist());
    public final static String FILE_FFDEC = 
            String.format("%s%s", CLASSPATH, AppDataCache.appConfigs.getFile_ffdec());
    
    public final static String SCANNAME_START2 = "Start2";
    public final static String SCANNAME_LASTMODIFIED = "Lastmodified";
    
}
