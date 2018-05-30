/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kcwiki.x.cache.inmem;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.kcwiki.x.initializer.AppConfigs;

/**
 *
 * @author x5171
 */
public class AppDataCache {
    public static AppConfigs appConfigs;
    public static boolean isAppInit = false;
    public static boolean isReadyReceive = false;
    public static final String classpath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    
    public static final Map<Integer, String> gameWorlds = new ConcurrentHashMap<>();
    public static final Map<Integer, String> world_Num2Name = new HashMap<>();
    public static final Map<String, Map<String, String>> ConstantData = new HashMap<>();
    
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