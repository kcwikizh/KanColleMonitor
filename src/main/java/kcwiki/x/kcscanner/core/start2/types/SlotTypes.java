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
public enum SlotTypes implements BaseStart2Enum {
    
    AirunitBanner("airunit_banner"),
    AirunitFairy("airunit_fairy"),
    AirunitName("airunit_name"),
    BtxtFlat("btxt_flat"),
    Card("card"),
    CardT("card_t"),
    ItemCharacter("item_character"),
    ItemOn("item_on"),
    ItemUp("item_up"),
    Remodel("remodel"),
    StatustopItem("statustop_item")
    ;
    
    private final String typeName;
    
    SlotTypes(String name) {
        this.typeName = name;
    }

    @Override
    public String getTypeName() {
        return this.typeName;
    }
}
