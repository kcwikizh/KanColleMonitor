/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.database.service;

import java.util.List;
import java.util.Map;
import kcwiki.x.kcscanner.database.TableName;
import kcwiki.x.kcscanner.database.entity.SystemScanEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import kcwiki.x.kcscanner.database.dao.SystemScanMapper;
import org.springframework.stereotype.Service;

/**
 *
 * @author x5171
 */
@Service
public class SystemScanService {
    static final Logger LOG = LoggerFactory.getLogger(SystemScanService.class);
    
    @Autowired
    private SystemScanMapper systemScanMapper;
    @Autowired 
    private UtilsService utilsService;
    
    
    public Map<String, SystemScanEntity> getAll() {
        return systemScanMapper.selectAllScanData(TableName.getSystemScanTable());
    }
    
    public int insertOne(SystemScanEntity playerEOEntity) {
        if(!utilsService.existTable(TableName.getSystemScanTable())){
            return -1;
        }
        return systemScanMapper.insertOne(TableName.getSystemScanTable(), playerEOEntity);
    }
    
    public int insertSelected(List<SystemScanEntity> list) {
        if(!utilsService.existTable(TableName.getSystemScanTable())){
            return -1;
        }
        return systemScanMapper.insertBatch(TableName.getSystemScanTable(), list);
    }
    
    public int updateOne(SystemScanEntity systemScanEntity) {
        if(!utilsService.existTable(TableName.getSystemScanTable())){
            return -1;
        }
        return systemScanMapper.updateOne(TableName.getSystemScanTable(), systemScanEntity);
    }
    
    public int updateSelected(List<SystemScanEntity> list) {
        if(!utilsService.existTable(TableName.getSystemScanTable())){
            return -1;
        }
        return systemScanMapper.updateBatch(TableName.getSystemScanTable(), list);
    }
}
