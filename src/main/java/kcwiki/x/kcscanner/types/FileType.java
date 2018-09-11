/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.types;

/**
 *
 * @author x5171
 */
public enum FileType {
    Core("Core"),
    ShipVoice("ShipVoice"),
    Ship("Ship"),
    Slotitem("Slotitem"),
    Furniture("Furniture"),
    Useitem("Useitem"),
    Payitem("Payitem"),
    Mapbgm("Mapbgm"),
    Mapinfo("Mapinfo"),
    Bgm("Bgm")
    ;
    
    private String name;
    
    FileType(String name) {
        this.name = name;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
}
