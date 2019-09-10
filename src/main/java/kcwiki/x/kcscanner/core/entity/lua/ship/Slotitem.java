/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.entity.lua.ship;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

/**
 *
 * @author iHaru
 */
public class Slotitem {
    
    @JsonProperty(value="ID")
    int id;
    
    @JsonProperty(value="日文名")
    String jp;
    
    @JsonProperty(value="中文名")
    String zh;
    
    @JsonProperty(value="类别")
    List<Integer> stype;
    
    @JsonProperty(value="稀有度")
    String ctype;
    
    @JsonProperty(value="状态")
    Map<String, Integer> data;
    
    @JsonProperty(value="属性")
    Map<String, Integer> slotitem;
    
    @JsonProperty(value="废弃")
    Map<String, Integer> cost;
    
    @JsonProperty(value="装备适用")
    List<String> drop;
    
    @JsonProperty(value="备注")
    String improvement;
    
    @JsonProperty(value="装备改修")
    Map<String, Object> scrap;
    
    @JsonProperty(value="改修备注")
    String remodel;
    
    @JsonProperty(value="日文WIKI")
    String wikiJp;
    
    @JsonProperty(value="英文WIKI")
    String wikiZh;
    
}
