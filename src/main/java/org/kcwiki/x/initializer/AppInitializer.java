/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kcwiki.x.initializer;

import org.kcwiki.x.database.dao.UtilsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author x5171
 */
@Component
public class AppInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(AppInitializer.class);
    
    @Autowired
    UtilsMapper utilsMapper;
    
    public void init(){
        LOG.info("existTable: {}", utilsMapper.existTable("log_system"));
        LOG.info("初始化完成。");
    }
    
}
