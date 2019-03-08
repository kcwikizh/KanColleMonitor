/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.database.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import kcwiki.x.kcscanner.database.entity.Start2DataEntity;

/**
 *
 * @author iHaru
 */
public interface Start2DataMapper {
    List<Start2DataEntity> selectAllStart2Data(@Param("tablename") String tablename);
    Start2DataEntity selectLatestData(@Param("tablename") String tablename);

    int insertOne(@Param("tablename") String tablename, @Param("item") Start2DataEntity fileDataEntity);
    int insertBatch(@Param("tablename") String tablename,@Param("list") List<Start2DataEntity> list);
    int updateBatch(@Param("tablename") String tablename, @Param("list") List<Start2DataEntity> list);
}
