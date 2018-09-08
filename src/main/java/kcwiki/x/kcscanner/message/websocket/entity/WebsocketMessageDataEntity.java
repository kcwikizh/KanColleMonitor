/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.message.websocket.entity;

import kcwiki.x.kcscanner.message.websocket.types.WebsocketMessageType;
import kcwiki.x.kcscanner.types.MessageLevel;

/**
 *
 * @author x5171
 */
public class WebsocketMessageDataEntity<T> {

    private WebsocketMessageType type;
    private MessageLevel level;
    private T payload;

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
     * @return the level
     */
    public MessageLevel getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(MessageLevel level) {
        this.level = level;
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
