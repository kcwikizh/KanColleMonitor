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
public enum ShipTypes implements BaseStart2Enum {
    
    AlbumStatus("album_status"),
    Banner("banner"),
    BannerDmg("banner_dmg"),
    Card("card"),
    CardDmg("card_dmg"),
    CardRound("card_round"),
    CharacterFull("character_full"),
    CharacterFullDmg("character_full_dmg"),
    CharacterUp("character_up"),
    CharacterUpDmg("character_up_dmg"),
    Full("full"),
    FullDmg("full_dmg"),
    Icon_box("icon_box"),
    Remodel("remodel"),
    RemodelDmg("remodel_dmg"),
    SupplyCharacter("supply_character"),
    SupplyCharacterDmg("supply_character_dmg"),
    Special("special")
    ;
    
    private final String typeName;
    
    ShipTypes(String name) {
        this.typeName = name;
    }

    @Override
    public String getTypeName() {
        return this.typeName;
    }
    
}
