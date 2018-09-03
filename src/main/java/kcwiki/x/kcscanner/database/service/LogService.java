/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.database.service;

import java.util.Date;
import org.apache.commons.lang3.exception.ExceptionUtils;
import kcwiki.x.kcscanner.database.TableName;
import kcwiki.x.kcscanner.database.dao.LogMapper;
import kcwiki.x.kcscanner.database.entity.log.LogEntity;
import kcwiki.x.kcscanner.exception.ExceptionBase;
import static kcwiki.x.kcscanner.tools.ConstantValue.LINESEPARATOR;
import kcwiki.x.kcscanner.types.MsgTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author x5171
 */
@Service
public class LogService {
    @Autowired
    private LogMapper logMapper;
    
    public boolean addLog(MsgTypes msgTypes, String msg) {
        LogEntity logEntity = new LogEntity();
        logEntity.setType(msgTypes);
        logEntity.setTimestamp(new Date());
        logEntity.setMessage(msg);
        logMapper.addLogMsg(TableName.getSystemLogTable(), logEntity);
        return true;
    }
    
    public boolean addLog(MsgTypes msgTypes, String signature, Throwable ex) {
        LogEntity logEntity = new LogEntity();
        logEntity.setType(msgTypes);
        logEntity.setTimestamp(new Date());
        logEntity.setSignature(signature);
        if(ex instanceof ExceptionBase) {
            ExceptionBase baseException = (ExceptionBase) ex;
            String rs = ExceptionUtils.getStackTrace(ex);
            rs = String.format("%s%s%s%s%s", 
                    baseException.getServiceType().getName(), 
                    LINESEPARATOR,
                    baseException.getMessage(), 
                    LINESEPARATOR, 
                    rs);
            logEntity.setException(rs);
        }else {
            logEntity.setException(ExceptionUtils.getStackTrace(ex));
        }
        logMapper.addLogMsg(TableName.getSystemLogTable(), logEntity);
        return true;
    }
    
}