/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.database.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import kcwiki.x.kcscanner.database.entity.SystemScanEntity;

/**
 *
 * @author x5171
 */
public interface SystemScanMapper {
    @MapKey("name")
    Map<String, SystemScanEntity> selectAllScanData(@Param("tablename") String tablename);

    int insertOne(@Param("tablename") String tablename, @Param("item") SystemScanEntity systemScanEntity);
    int insertBatch(@Param("tablename") String tablename,@Param("list") List<SystemScanEntity> list);
    int updateOne(@Param("tablename") String tablename, @Param("item") SystemScanEntity systemScanEntity);
    int updateBatch(@Param("tablename") String tablename, @Param("list") List<SystemScanEntity> list);
}
