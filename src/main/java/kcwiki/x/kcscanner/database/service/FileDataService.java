/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.database.service;

import java.util.List;
import java.util.Map;
import kcwiki.x.kcscanner.database.TableName;
import kcwiki.x.kcscanner.database.dao.FileDataMapper;
import kcwiki.x.kcscanner.database.entity.FileDataEntity;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author x5171
 */
@Service
public class FileDataService {
    static final Logger LOG = LoggerFactory.getLogger(FileDataService.class);
    
    @Autowired
    private FileDataMapper fileDataMapper;
    @Autowired 
    private UtilsService utilsService;
    
    
    public Map<String, FileDataEntity> getAll() {
        return fileDataMapper.selectAllFileData(TableName.getDataLastmodifiedTable());
    }
    
    public int insertOne(FileDataEntity fileDataEntity) {
        if(!utilsService.existTable(TableName.getDataLastmodifiedTable())){
            return -1;
        }
        return fileDataMapper.insertOne(TableName.getDataLastmodifiedTable(), fileDataEntity);
    }
    
    public int insertSelected(List<FileDataEntity> list) {
        if(!utilsService.existTable(TableName.getDataLastmodifiedTable())){
            return -1;
        }
        try{
            fileDataMapper.insertBatch(TableName.getLastModifiedLogTable(), list);
        } catch (Exception ex) {
            LOG.error(ExceptionUtils.getStackTrace(ex));
        }
        return fileDataMapper.insertBatch(TableName.getDataLastmodifiedTable(), list);
    }
    
    public int updateSelected(List<FileDataEntity> list) {
        if(!utilsService.existTable(TableName.getDataLastmodifiedTable())){
            return -1;
        }
        return fileDataMapper.updateBatch(TableName.getDataLastmodifiedTable(), list);
    }
}
