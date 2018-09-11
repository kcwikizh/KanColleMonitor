/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.database.dao;

import org.apache.ibatis.annotations.Param;
import kcwiki.x.kcscanner.database.entity.log.LogEntity;

/**
 *
 * @author x5171
 */
//@MapperScan
public interface LogMapper {
    int addLogMsg(@Param("tablename") String tablename, @Param("item") LogEntity logEntity);
}
