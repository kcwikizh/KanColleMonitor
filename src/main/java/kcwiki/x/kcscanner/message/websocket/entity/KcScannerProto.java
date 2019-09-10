/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.message.websocket.entity;

import kcwiki.x.kcscanner.message.websocket.types.WebsocketMessageType;

/**
 *
 * @author iHaru
 */
public class KcScannerProto <T> {
    private WebsocketMessageType type;
    private T payload;
    
    public KcScannerProto (){}
    
    public KcScannerProto (WebsocketMessageType type, T payload) {
        this.type = type;
        this.payload = payload;
    }

    /**
     * @return the type
     */
    public WebsocketMessageType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(WebsocketMessageType type) {
        this.type = type;
    }

    /**
     * @return the payload
     */
    public T getPayload() {
        return payload;
    }

    /**
     * @param payload the payload to set
     */
    public void setPayload(T payload) {
        this.payload = payload;
    }
    
}
