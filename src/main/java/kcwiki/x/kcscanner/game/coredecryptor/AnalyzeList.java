/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.game.coredecryptor;

import kcwiki.x.kcscanner.message.websocket.MessagePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author iTeam_VEP
 */
public class AnalyzeList {
    static final Logger LOG = LoggerFactory.getLogger(AnalyzeList.class);
    
    @Autowired
    MessagePublisher messagePublisher;
    
    public static void shipData() {
        
    }
    
    public static boolean voiceData(String str) {
        
        return true;
    } 
}
