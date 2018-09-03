/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.start2.types;

/**
 *
 * @author x5171
 */
public enum Start2Types {
    
    bgm("bgm", BgmTypes.values()),
    furniture("furniture", FurnitureTypes.values()),
    ship("ship", ShipTypes.values()),
    slot("slot", SlotTypes.values()),
    useitem("useitem", UseitemTypes.values())
    ;
    
    private final String typeName;
    
    private final BaseStart2Enum[] subTypes;
    
    Start2Types(String typeName, BaseStart2Enum[] start2Enum) {
        this.typeName = typeName;
        this.subTypes = start2Enum;
    }

    public String getTypeName() {
        return this.typeName;
    }
    
    public BaseStart2Enum[] getSubTypes() {
        return this.subTypes;
    }
}
