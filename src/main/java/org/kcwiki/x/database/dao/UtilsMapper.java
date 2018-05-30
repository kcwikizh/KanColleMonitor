/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kcwiki.x.database.dao;

import org.apache.ibatis.annotations.Param;

/**
 *
 * @author x5171
 */
public interface UtilsMapper {
    //https://blog.csdn.net/doctor_who2004/article/details/43025503
    //https://blog.csdn.net/shadow_zed/article/details/72897510
    //https://blog.csdn.net/m0_37981235/article/details/79131493
    
    int createSystemUploadUserTable(@Param("tablename") String tablename);
    
    int createSystemGameServersTable(@Param("tablename") String tablename);
    
    int createSystemEosTable(@Param("tablename") String tablename);
    
    int createSystemLogTable(@Param("tablename") String tablename);
    
    int createSenkaHistoryDataLogTable(@Param("tablename") String tablename);
    
    int createServerSenkaTable(@Param("tablename") String tablename);
    
    int createPlayerSenkaTable(@Param("tablename") String tablename);
    
    int createPlayerEOTable(@Param("tablename") String tablename);
    
    int existTable(String tablename);
    
    void truncateTable(@Param("tablename") String tablename);
    
}
