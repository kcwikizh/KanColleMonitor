/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.database.service;

import java.util.List;
import kcwiki.x.kcscanner.database.TableName;
import kcwiki.x.kcscanner.database.dao.Start2DataMapper;
import kcwiki.x.kcscanner.database.entity.Start2DataEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author x5171
 */
@Service
public class Start2DataService {
    static final Logger LOG = LoggerFactory.getLogger(Start2DataService.class);
    
    @Autowired
    private Start2DataMapper start2DataMapper;
    @Autowired 
    private UtilsService utilsService;
    
    
    public List<Start2DataEntity> getAll() {
        return start2DataMapper.selectAllStart2Data(TableName.getDataStart2Table());
    }
    
    public Start2DataEntity getLatestData() {
        return start2DataMapper.selectLatestData(TableName.getDataStart2Table());
    }
    
    public int insertOne(Start2DataEntity start2DataEntity) {
        if(!utilsService.existTable(TableName.getDataStart2Table())){
            return -1;
        }
        return start2DataMapper.insertOne(TableName.getDataStart2Table(), start2DataEntity);
    }
    
    public int insertSelected(List<Start2DataEntity> list) {
        if(!utilsService.existTable(TableName.getDataStart2Table())){
            return -1;
        }
        return start2DataMapper.insertBatch(TableName.getDataStart2Table(), list);
    }
    
    public int updateSelected(List<Start2DataEntity> list) {
        if(!utilsService.existTable(TableName.getDataStart2Table())){
            return -1;
        }
        return start2DataMapper.updateBatch(TableName.getDataStart2Table(), list);
    }
}
