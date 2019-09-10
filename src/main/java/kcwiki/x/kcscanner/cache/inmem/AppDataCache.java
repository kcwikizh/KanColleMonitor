/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.cache.inmem;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import kcwiki.x.kcscanner.database.entity.SystemScanEntity;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Start2;

/**
 *
 * @author iHaru
 */
public class AppDataCache {
    public static Map<String, SystemScanEntity> systemScanEntitys = null;
    public static boolean isAppInit = false;
    public static boolean isReadyReceive = false;
    public static boolean isScanTaskSuspend = true;
    
    public static String kcHost = null;
    
    public static Start2 start2data = null;
    public static boolean isDownloadShipVoice = false;
    
    public static final Map<Integer, String> gameWorlds = new ConcurrentHashMap<>();
    public static final Map<Integer, String> world_Num2Name = new ConcurrentHashMap<>();
    public static final Map<String, String> stringCache = new ConcurrentHashMap<>();
    public static final Map<String, String> worldVersionCache = new ConcurrentHashMap<>();
    public static final Map<String, String> maintenanceInfo = new ConcurrentHashMap<>();
    public static final Map<String, String> dataHashCache = new ConcurrentHashMap<>();
    
    public static final Set<String> existTables = new HashSet<>();
    
    public static final Map<Integer, String> responseCache = new ConcurrentHashMap<>();
    
    static{
        world_Num2Name.put(1, "横须贺镇守府");
        world_Num2Name.put(2, "呉镇守府");
        world_Num2Name.put(3, "佐世保镇守府");
        world_Num2Name.put(4, "舞鹤镇守府");
        world_Num2Name.put(5, "大凑警备府");
        world_Num2Name.put(6, "トラック泊地");
        world_Num2Name.put(7, "リンガ泊地");
        world_Num2Name.put(8, "ラバウル基地");
        world_Num2Name.put(9, "ショートランド泊地");
        world_Num2Name.put(10, "ブイン基地");
        world_Num2Name.put(11, "タウイタウイ泊地");
        world_Num2Name.put(12, "パラオ泊地");
        world_Num2Name.put(13, "ブルネイ泊地");
        world_Num2Name.put(14, "単冠湾泊地");
        world_Num2Name.put(15, "幌筵泊地");
        world_Num2Name.put(16, "宿毛湾泊地");
        world_Num2Name.put(17, "鹿屋基地");
        world_Num2Name.put(18, "岩川基地");
        world_Num2Name.put(19, "佐伯湾泊地");
        world_Num2Name.put(20, "柱岛泊地");
    }
}
