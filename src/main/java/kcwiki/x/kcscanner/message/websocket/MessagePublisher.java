/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.message.websocket;

import java.security.NoSuchAlgorithmException;
import javax.annotation.PostConstruct;
import kcwiki.management.xcontrolled.configuration.XModuleConfig;
import kcwiki.management.xcontrolled.core.XModuleController;
import kcwiki.management.xcontrolled.message.websocket.XMessagePublisher;
import kcwiki.x.kcscanner.message.websocket.entity.KcScannerProto;
import kcwiki.x.kcscanner.message.websocket.entity.WebsocketMessageData;
import kcwiki.x.kcscanner.message.websocket.types.WebsocketMessageType;
import kcwiki.x.kcscanner.message.websocket.types.PublishTypes;
import kcwiki.x.kcscanner.types.MessageLevel;
import kcwiki.x.kcscanner.web.websocket.handler.AdministratorHandler;
import kcwiki.x.kcscanner.web.websocket.handler.GuestHandler;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.type.ResultType;
import org.iharu.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author iHaru
 */
@Component
//@Scope("prototype")
public class MessagePublisher<T> {
    private static final Logger LOG = LoggerFactory.getLogger(MessagePublisher.class);

    @Autowired
    XModuleCallBack xModuleCallBack;
    @Autowired
    XMessagePublisher xMessagePublisher;
//    @Autowired
//    AdministratorHandler administratorHandler;
//    @Autowired
//    GuestHandler guestHandler;
    
    @PostConstruct
    public void initMethod() throws NoSuchAlgorithmException {
        xMessagePublisher.connect(xModuleCallBack);
    }
    
    public <T> void publish(T payload, PublishTypes publishTypes, WebsocketMessageType websocketMessageTypes, MessageLevel messageLevel) {
        switch (publishTypes) {
            case Admin:
                WebsocketMessageData fullpayload = fullMsgGen(payload, websocketMessageTypes, messageLevel);
//                administratorHandler.sendMessageToAllUsers(JsonUtils.object2json(fullpayload, null));
                xMessagePublisher.publishNonSystemMsg(ResultType.SUCCESS, new KcScannerProto(websocketMessageTypes, payload));
                break;
            case Guest:
                fullpayload = fullMsgGen(payload, websocketMessageTypes, messageLevel);
//                guestHandler.sendMessageToAllUsers(JsonUtils.object2json(fullpayload, null));
                xMessagePublisher.publishNonSystemMsg(ResultType.SUCCESS, new KcScannerProto(websocketMessageTypes, payload));
                break;
            case All:
                fullpayload = fullMsgGen(payload, websocketMessageTypes, messageLevel);
//                administratorHandler.sendMessageToAllUsers(JsonUtils.object2json(fullpayload, null));
//                guestHandler.sendMessageToAllUsers(JsonUtils.object2json(fullpayload, null));
                xMessagePublisher.publishNonSystemMsg(ResultType.SUCCESS, new KcScannerProto(websocketMessageTypes, payload));
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
    
    private <T> WebsocketMessageData fullMsgGen(T payload, WebsocketMessageType websocketMessageTypes, MessageLevel messageLevel) {
        WebsocketMessageData websocketMessageDataEntity = new WebsocketMessageData();
        websocketMessageDataEntity.setType(websocketMessageTypes);
        websocketMessageDataEntity.setPayload(payload);
        websocketMessageDataEntity.setLevel(messageLevel);
        return websocketMessageDataEntity;
    }
}
