/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.database.dao;

import org.apache.ibatis.annotations.Param;

/**
 *
 * @author x5171
 */
public interface UtilsMapper {
    //https://blog.csdn.net/doctor_who2004/article/details/43025503
    //https://blog.csdn.net/shadow_zed/article/details/72897510
    //https://blog.csdn.net/m0_37981235/article/details/79131493
    
    int createSystemLogTable(@Param("tablename") String tablename);
    
    int createDataLastmodifiedTable(@Param("tablename") String tablename);
    
    int createDataStart2Table(@Param("tablename") String tablename);
    
    int createSystemScanTable(@Param("tablename") String tablename);
    
    int existTable(@Param("dbname") String dbname, @Param("tablename") String tablename);
    
    void truncateTable(@Param("tablename") String tablename);
    
}
