/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.message.websocket;

import kcwiki.management.xcontrolled.websocket.XModuleWebsocketClient;
import kcwiki.management.xcontrolled.websocket.XModuleWebsocketClientCallBack;
import org.iharu.proto.websocket.WebsocketProto;
import org.iharu.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author iHaru
 */
@Component
public class XModuleCallBack extends XModuleWebsocketClientCallBack {
    private static final Logger LOG = LoggerFactory.getLogger(XModuleCallBack.class);
    
    @Override
    protected void moduleCallback(String paramTextMessage) {
        LOG.info("received string data: {}", paramTextMessage);
    }

    @Override
    protected void moduleCallback(WebsocketProto proto) {
        LOG.info("received proto data: {}", JsonUtils.object2json(proto));
    }

    @Override
    protected Logger getImplLogger() {
        return LOG;
    }
    
}
