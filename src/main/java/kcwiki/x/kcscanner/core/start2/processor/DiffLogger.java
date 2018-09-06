/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.start2.processor;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.LoggerFactory;

/**
 *
 * @author x5171
 */
public class DiffLogger {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(DiffLogger.class);
    
    private static Map<String, List<JsonNode>> ShipDiff = new ConcurrentHashMap();
    private static Map<String, List<JsonNode>> SlotitemDiff = new ConcurrentHashMap();
    
    private static void addToMap(Map<String, List<JsonNode>> map, String name, JsonNode item){
        List<JsonNode> _list = map.get(name);
        if(_list == null)
            _list = new ArrayList();
        _list.add(item);
        map.put(name, _list);
    }
    
    public static void addShipDiff(String name, JsonNode item){
        addToMap(ShipDiff, name, item);
    }
    
    public static void addSlotitemDiff(String name, JsonNode item){
        addToMap(ShipDiff, name, item);
    }
    
}
