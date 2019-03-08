/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core;

import kcwiki.x.kcscanner.core.files.FileController;
import kcwiki.x.kcscanner.core.start2.Start2Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author iHaru
 */
@Component
public class ControlCenter {
    private static final Logger LOG = LoggerFactory.getLogger(CoreInitializer.class);
    
    @Autowired
    FileController fileController;
    @Autowired
    Start2Controller start2Controller;
    
    public void initPeriodicTask(){
        
    }
    
    
}
