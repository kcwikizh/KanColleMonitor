/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.pathutils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author iHaru
 */
//https://github.com/TeamFleet/Ooyodo/blob/master/libs/commons/get-pic-url-ship.js
//https://github.com/TeamFleet/Ooyodo/blob/master/libs/commons/get-pic-url-equipment.js
public class BaseUrl {
     
    protected static int[] resource = {6657, 5699, 3371, 8909, 7719, 6229, 5449, 8561, 2987, 5501, 3127, 9319, 4365, 9811, 9927, 2423, 3439, 1865, 5925, 4409, 5509, 1517, 9695, 9255, 5325, 3691, 5519, 6949, 5607, 9539, 4133, 7795, 5465, 2659, 6381, 6875, 4019, 9195, 5645, 2887, 1213, 1815, 8671, 3015, 3147, 2991, 7977, 7045, 1619, 7909, 4451, 6573, 4545, 8251, 5983, 2849, 7249, 7449, 9477, 5963, 2711, 9019, 7375, 2201, 5631, 4893, 7653, 3719, 8819, 5839, 1853, 9843, 9119, 7023, 5681, 2345, 9873, 6349, 9315, 3795, 9737, 4633, 4173, 7549, 7171, 6147, 4723, 5039, 2723, 7815, 6201, 5999, 5339, 4431, 2911, 4435, 3611, 4423, 9517, 3243};
    protected static int[] voice = {2475, 6547, 1471, 8691, 7847, 3595, 1767, 3311, 2507, 9651, 5321, 4473, 7117, 5947, 9489, 2669, 8741, 6149, 1301, 7297, 2975, 6413, 8391, 9705, 2243, 2091, 4231, 3107, 9499, 4205, 6013, 3393, 6401, 6985, 3683, 9447, 3287, 5181, 7587, 9353, 2135, 4947, 5405, 5223, 9457, 5767, 9265, 8191, 3927, 3061, 2805, 3273, 7331};
        
    
    private static Pattern p;
    private static Matcher m;
    
    public static int createKey(String type) {
        char e = 0;
        if (!StringUtils.isBlank(type))
            for (int i = 0; i < type.length(); i++)
                e += (byte) type.charAt(i);
        return e;
    }

    public static String  SuffixUtil_create(int itemID, String type) {
//        int r;
//        p=Pattern.compile("(\\d+)");
//        m=p.matcher(shipID);
//        if(m.find()){
//            r = Integer.valueOf(m.group(1));
//        } else {
//            return "";
//        }
        int s = createKey(type);
        int a = (null == type || 0 == type.length()) ? 1 : type.length();
        String rs = String.valueOf(17 * (itemID + 7) * resource[(s + itemID * a) % 100] % 8973 + 1000);
        return rs;
    }
    
    public static String getItemUrl4(String itemType, int itemID, String picType){
        switch (itemType.toLowerCase()) {
            case "ships": {
                itemType = "ship";
                break;
            }
            case "item":
                break;
            case "items":
                break;
            case "equipment":
                break;
            case "equipments": 
                itemType = "slot";
                break;
            default: 
                itemType = itemType.toLowerCase();
        }    
        String obfsStr = SuffixUtil_create(itemID, String.format("%s_%s", itemType, picType));
        return String.format("%s_%s", String.format("%04d", itemID), obfsStr);
//        return String.format("%s/kcs2/resources/%s/%s/"+ "%s_%s.png?version=%s", "", itemType, picType, String.format("%04d", itemID), obfsStr, picVersion);
    }
    
    public static String getItemUrl(String itemType, int itemID, String picType){
        switch (itemType.toLowerCase()) {
            case "ships": {
                itemType = "ship";
                break;
            }
            case "item":
                break;
            case "items":
                break;
            case "equipment":
                break;
            case "equipments": 
                itemType = "slot";
                break;
            default: 
                itemType = itemType.toLowerCase();
        }    
        String obfsStr = SuffixUtil_create(itemID, String.format("%s_%s", itemType, picType));
        return String.format("%s_%s", String.format("%03d", itemID), obfsStr);
//        return String.format("%s/kcs2/resources/%s/%s/"+ "%s_%s.png?version=%s", "", itemType, picType, String.format("%03d", itemID), obfsStr, picVersion);
    }


    public static void main(String[] args) {
        SuffixUtil_create(464, "ship_banner");
    }

}
