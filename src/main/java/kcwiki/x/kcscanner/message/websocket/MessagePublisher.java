/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.message.websocket;

import kcwiki.x.kcscanner.message.websocket.entity.WebsocketMessageData;
import kcwiki.x.kcscanner.message.websocket.types.WebsocketMessageType;
import kcwiki.x.kcscanner.tools.JsonUtils;
import kcwiki.x.kcscanner.message.websocket.types.PublishTypes;
import kcwiki.x.kcscanner.types.MessageLevel;
import kcwiki.x.kcscanner.web.websocket.handler.AdministratorHandler;
import kcwiki.x.kcscanner.web.websocket.handler.GuestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author iHaru
 */
@Component
@Scope("prototype")
public class MessagePublisher<T> {
    private static final Logger LOG = LoggerFactory.getLogger(MessagePublisher.class);
    
    @Autowired
    AdministratorHandler administratorHandler;
    @Autowired
    GuestHandler guestHandler;
    
    public <T> void publish(T payload, PublishTypes publishTypes, WebsocketMessageType websocketMessageTypes, MessageLevel messageLevel) {
        switch (publishTypes) {
            case Admin:
                administratorHandler.sendMessageToAllUsers(fullMsgGen(payload, websocketMessageTypes, messageLevel));
                break;
            case Guest:
                guestHandler.sendMessageToAllUsers(fullMsgGen(payload, websocketMessageTypes, messageLevel));
                break;
            case All:
                String fullpayload = fullMsgGen(payload, websocketMessageTypes, messageLevel);
                administratorHandler.sendMessageToAllUsers(fullpayload);
                guestHandler.sendMessageToAllUsers(fullpayload);
                break;
            default:
                break;
        }
    }
    
    public <T> void publish(T payload, PublishTypes publishTypes, WebsocketMessageType websocketMessageType) {
        publish(payload, publishTypes, websocketMessageType, MessageLevel.INFO);
    }
    
    public <T> void publish(T payload, WebsocketMessageType websocketMessageType) {
        publish(payload, PublishTypes.Admin, websocketMessageType, MessageLevel.INFO);
    }
    
    public <T> void publish(T payload, PublishTypes publishTypes) {
        publish(payload, publishTypes, WebsocketMessageType.KanColleScanner_System_Info, MessageLevel.INFO);
    }
    
    public <T> void publish(T payload) {
        publish(payload, PublishTypes.Admin, WebsocketMessageType.KanColleScanner_System_Info, MessageLevel.INFO);
    }
    
    private <T> String fullMsgGen(T payload, WebsocketMessageType websocketMessageTypes, MessageLevel messageLevel) {
        WebsocketMessageData websocketMessageDataEntity = new WebsocketMessageData();
        websocketMessageDataEntity.setType(websocketMessageTypes);
        websocketMessageDataEntity.setPayload(payload);
        websocketMessageDataEntity.setLevel(messageLevel);
        return JsonUtils.object2json(websocketMessageDataEntity, null);
    }
}
