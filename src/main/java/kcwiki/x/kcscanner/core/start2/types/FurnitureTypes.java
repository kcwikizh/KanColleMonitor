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
public enum FurnitureTypes implements BaseStart2Enum {
    
    Movable("movable"),
    Normal("normal"),
    Outside("outside"),
    Picture("picture"),
    Reward("reward"),
    Scripts("scripts"),
    Thumbnail("thumbnail")
    ;
    
    private final String typeName;
    
    FurnitureTypes(String name) {
        this.typeName = name;
    }

    @Override
    public String getTypeName() {
        return this.typeName;
    }
}
