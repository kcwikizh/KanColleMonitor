/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.message.websocket;

import kcwiki.x.kcscanner.types.PublishStatus;
import kcwiki.x.kcscanner.types.PublishTypes;
import kcwiki.x.kcscanner.web.websocket.handler.AdministratorHandler;
import kcwiki.x.kcscanner.web.websocket.handler.GuestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author x5171
 */
@Component
@Scope("prototype")
public class MessagePublisher {
    static final Logger LOG = LoggerFactory.getLogger(MessagePublisher.class);
    
    @Autowired
    AdministratorHandler administratorHandler;
    @Autowired
    GuestHandler guestHandler;
    
    public void publish(String msg, PublishTypes publishTypes, PublishStatus publishStatus){
        switch (publishTypes) {
            case Admin:
                administratorHandler.sendMessageToAllUsers(fullMsgGen(msg, publishStatus));
                break;
            case Guest:
                guestHandler.sendMessageToAllUsers(fullMsgGen(msg, publishStatus));
                break;
            case All:
                String fullmsg = fullMsgGen(msg, publishStatus);
                administratorHandler.sendMessageToAllUsers(fullmsg);
                guestHandler.sendMessageToAllUsers(fullmsg);
                break;
            default:
                break;
        }
    }
    
    private String fullMsgGen(String msg, PublishStatus publishStatus) {
        return "";
    }
}
