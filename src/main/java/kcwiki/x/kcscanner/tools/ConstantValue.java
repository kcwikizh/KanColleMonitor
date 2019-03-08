/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.tools;

import java.io.File;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iHaru
 */
public final class ConstantValue {
    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ConstantValue.class);
    
    public final static String LINESEPARATOR = System.getProperty("line.separator", "\n");
    public final static String FILESEPARATOR = File.separator;
    public static final String CLASSPATH;
    public final static String TEMP_FOLDER = 
            String.format("%s/%s", System.getProperty("java.io.tmpdir"), "kcscanner");
    
    public final static String SCANNAME_START2 = "Start2";
    public final static String SCANNAME_LASTMODIFIED = "Lastmodified";
    
    static{
        String _CLASSPATH = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        LOG.debug("CLASSPATH: {}", _CLASSPATH);
        if(_CLASSPATH.startsWith("/") && _CLASSPATH.contains(":")){
            CLASSPATH = _CLASSPATH.substring(1);
        } else if(_CLASSPATH.startsWith("file:")) {
            CLASSPATH = _CLASSPATH.substring(6);
        } else {
            CLASSPATH = _CLASSPATH;
        }
    }
    
}
