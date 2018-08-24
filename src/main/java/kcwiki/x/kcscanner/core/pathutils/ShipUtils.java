/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.pathutils;

/**
 *
 * @author x5171
 */
public class ShipUtils extends BaseUrl {
    
    //舰娘可用图片类型 敌舰没有dmg类型
    String[] ShipPicType = {
        "full",
        "full_dmg",
        "supply_character",
        "supply_character_dmg",
        "remodel",
        "remodel_dmg",
        "banner",
        "banner_dmg",
        "card",
        "card_dmg",
    };

    public static boolean ShipUtil_isEnemy(int t) {
        return t > 1500;
    }

    public static String getPath(int shipID, String picType, int picVersion) {
        if(ShipUtil_isEnemy(shipID) && picType.contains("_dmg"))
            return null;
        return getItemUrl("ship", shipID, picType, picVersion);
    }
    
    public static String getShipVoice(int shipID, String filename, int voiceType){
        String o = "/kcs/sound";
//        if (1 == isNaN(i)) {
//                if ("tutorial" != t) {
//                    var n = parseInt(e, 10);
//                    e = u.MathUtil.zeroPadding(n, 3)
//                }
//                return a.default.settings.path_root + "resources/voice/" + t + "/" + e + ".mp3"
//            }
//            var o = a.default.settings.voice_root;
//            null == o && (o = a.default.settings.path_root + "resources/voice");
            int voicename;
            if (9997 != shipID && 9998 != shipID && 9999 != shipID) {
                //判断api_mst_shipgraph里是否有shipID这个舰娘
//                var l = a.default.model.ship_graph.get(t);
//                if (null == l)
//                    return;
                voicename = (int) (voiceType <= 53 ? (17 * (shipID + 7) * voice[voiceType - 1] % 99173 + 1e5) : voiceType);
                return o + "/kc" + filename + "/" + voicename + ".mp3";
            }
        return null; 
    }

    public static void main(String[] args) {
        SuffixUtil_create(464, "ship_banner");
    }

}
