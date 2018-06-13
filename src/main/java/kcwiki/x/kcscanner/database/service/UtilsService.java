/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.database.service;

import kcwiki.x.kcscanner.cache.inmem.AppDataCache;
import kcwiki.x.kcscanner.database.dao.UtilsMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author x5171
 */
@Service
public class UtilsService {
    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(UtilsService.class);
    @Autowired
    private UtilsMapper utilsMapper;
    
    public boolean createSystemLogTable(String tablename) {
        utilsMapper.createSystemLogTable(tablename);
        return addLog(tablename);
    }
    
    public boolean createDataLastmodifiedTable(String tablename) {
        utilsMapper.createDataLastmodifiedTable(tablename);
        return addLog(tablename);
    }
    
    public boolean createDataStart2Table(String tablename) {
        utilsMapper.createDataStart2Table(tablename);
        return addLog(tablename);
    }
    
    public boolean createSystemScanTable(String tablename) {
        utilsMapper.createSystemScanTable(tablename);
        return addLog(tablename);
    }
    
    public void truncateTable(String tablename) {
        if(existTable(tablename))
            utilsMapper.truncateTable(tablename);
    }
    
    public boolean existTable(String tablename) {
        int result = utilsMapper.existTable(AppDataCache.appConfigs.getMyprops_database_name(), tablename);
        return result>0;
    }
    
    private boolean addLog(String tablename) {
        if(existTable(tablename)) {
            LOG.info("创建数据库表: `{}`成功。", tablename);
            return true;
        } else {
            LOG.error("创建数据库表: `{}`失败。", tablename);
            return false;
        }
    }
}
