/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.tools;

import java.io.File;

/**
 *
 * @author x5171
 */
public final class ConstantValue {
    public final static String LINESEPARATOR = System.getProperty("line.separator", "\n");
    public final static String FILESEPARATOR = File.separator;
    public static final String CLASSPATH = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    public static final String WEBROOT = CLASSPATH.replace("/WEB-INF/classes", "");
    public final static String TEMP_FOLDER = 
            String.format("%s%s", System.getProperty("java.io.tmpdir"), "/kcscanner");
    
    public final static String SCANNAME_START2 = "Start2";
    public final static String SCANNAME_LASTMODIFIED = "Lastmodified";
    
}
