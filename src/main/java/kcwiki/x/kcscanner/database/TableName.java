/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.database;

/**
 *
 * @author iHaru
 */
public class TableName {
    public static String getSystemLogTable() {
        return String.format("log_system");
    }
    
    public static String getLastModifiedLogTable() {
        return String.format("log_lastmodified");
    }
    
    public static String getDataLastmodifiedTable() {
        return String.format("data_lastmodified");
    }
    
    public static String getDataStart2Table() {
        return String.format("data_start2");
    }
    
    public static String getSystemScanTable() {
        return String.format("system_scan");
    }
    
}
