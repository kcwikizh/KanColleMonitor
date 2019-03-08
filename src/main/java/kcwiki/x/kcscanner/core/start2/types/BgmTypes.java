/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.start2.types;

/**
 *
 * @author iHaru
 */
public enum BgmTypes implements BaseStart2Enum {
    
    Battle("battle"),
    Fanfare("fanfare"),
    Port("port")
    ;
    
    private final String typeName;
    
    BgmTypes(String name) {
        this.typeName = name;
    }

    @Override
    public String getTypeName() {
        return this.typeName;
    }
    
}
