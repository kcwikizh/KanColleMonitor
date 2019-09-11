/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.message.websocket;

import java.security.NoSuchAlgorithmException;
import javax.annotation.PostConstruct;
import kcwiki.management.xcontrolled.message.websocket.XMessagePublisher;
import kcwiki.x.kcscanner.message.websocket.entity.KcScannerProto;
import kcwiki.x.kcscanner.message.websocket.types.ModuleType;
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
    
    @PostConstruct
    public void initMethod() throws NoSuchAlgorithmException {
        xMessagePublisher.connect(xModuleCallBack);
    }
    
    public void publish(KcScannerProto proto, ResultType resultType){
        xMessagePublisher.publishNonSystemMsg(resultType, proto);
    }
    
    public <T> void publish(T payload, ModuleType moduleType) {
        if(payload instanceof String)
            publish(new KcScannerProto(moduleType, (String) payload));
        else
            publish(new KcScannerProto(moduleType, JsonUtils.object2json(payload)));
    }
    
    public <T> void publish(T payload, ModuleType moduleType, ResultType resultType) {
        if(payload instanceof String)
            publish(new KcScannerProto(resultType, moduleType, (String) payload));
        else
            publish(new KcScannerProto(resultType, moduleType, JsonUtils.object2json(payload)));
    }
    
    public <T> void publish(KcScannerProto payload) {
        publish(payload, ResultType.SUCCESS);
    }
    
}
