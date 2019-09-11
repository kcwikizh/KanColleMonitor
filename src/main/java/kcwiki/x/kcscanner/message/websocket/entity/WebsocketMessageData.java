/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.message.websocket.entity;

import kcwiki.x.kcscanner.message.websocket.types.ModuleType;
import kcwiki.x.kcscanner.types.MessageLevel;

/**
 *
 * @author iHaru
 */
public class WebsocketMessageData<T> {

    private ModuleType type;
    private MessageLevel level;
    private T payload;

    /**
     * @return the type
     */
    public ModuleType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(ModuleType type) {
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
