/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.pathutils;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 *
 * @author iHaru
 */
public class SlotUtils extends BaseUrl {

    public static boolean SlotUtil_isEnemy(int t) {
        return t > 500;
    }

    public static String getPath(int shipID, String picType) {
        if(SlotUtil_isEnemy(shipID))
            return null;
        return getItemUrl("slot", shipID, picType);
    }
    
    public static String getWikiFileName(String wikiID, String picType) {
        String wikiType;
        switch(picType){
            case "card":
                wikiType = "Soubi%sHD";
                break;
            case "item_character":
                wikiType = "Soubi%sHDFairy";
                break;
            case "item_up":
                wikiType = "Soubi%sHDArmament";
                break;
            case "item_on":
                wikiType = "Soubi%sHDFull";
                break;
            default:
                return null;
        }
        try{
            return String.format(wikiType, wikiID);
        } catch (Exception ex) {
            ExceptionUtils.getStackTrace(ex);
        }
        return null;
    }

    public static void main(String[] args) {
        SuffixUtil_create(464, "ship_banner");
    }

}
