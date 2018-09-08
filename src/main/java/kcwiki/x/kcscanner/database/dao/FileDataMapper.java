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
import kcwiki.x.kcscanner.database.entity.FileDataEntity;
import kcwiki.x.kcscanner.types.FileType;

/**
 *
 * @author x5171
 */
public interface FileDataMapper {
    @MapKey("path")
    Map<String, FileDataEntity> selectAllFileData(@Param("tablename") String tablename);
    @MapKey("path")
    Map<String, FileDataEntity> selectTypeFileData(@Param("tablename") String tablename, @Param("type") FileType type);
    
    int insertOne(@Param("tablename") String tablename, @Param("item") FileDataEntity fileDataEntity);
    int insertBatch(@Param("tablename") String tablename,@Param("list") List<FileDataEntity> list);
    int updateBatch(@Param("tablename") String tablename, @Param("list") List<FileDataEntity> list);
}
