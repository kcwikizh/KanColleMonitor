/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.entity.lua.slotitem;

import kcwiki.x.kcscanner.core.entity.lua.ship.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

/**
 *
 * @author iHaru
 */
public class Slotitem {
    
    @JsonProperty(value="ID")
    private int id;
    
    @JsonProperty(value="日文名")
    private String jp;
    
    @JsonProperty(value="中文名")
    private String zh;
    
    @JsonProperty(value="类别")
    private List<Integer> type;
    
    @JsonProperty(value="稀有度")
    private String rarity;
    
    @JsonProperty(value="状态")
    private Map<String, Integer> cond;
    
    @JsonProperty(value="属性")
    private Map<String, Integer> stats;
    
    @JsonProperty(value="废弃")
    private Map<String, Integer> scrap;
    
    @JsonProperty(value="装备适用")
    private List<String> refittable;
    
    @JsonProperty(value="备注")
    private String notes;
    
    @JsonProperty(value="装备改修")
    private Map<String, Object> improvement;
    
    @JsonProperty(value="日文WIKI")
    private String wikiJp;
    
    @JsonProperty(value="英文WIKI")
    private String wikiZh;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the jp
     */
    public String getJp() {
        return jp;
    }

    /**
     * @param jp the jp to set
     */
    public void setJp(String jp) {
        this.jp = jp;
    }

    /**
     * @return the zh
     */
    public String getZh() {
        return zh;
    }

    /**
     * @param zh the zh to set
     */
    public void setZh(String zh) {
        this.zh = zh;
    }

    /**
     * @return the type
     */
    public List<Integer> getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(List<Integer> type) {
        this.type = type;
    }

    /**
     * @return the rarity
     */
    public String getRarity() {
        return rarity;
    }

    /**
     * @param rarity the rarity to set
     */
    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    /**
     * @return the cond
     */
    public Map<String, Integer> getCond() {
        return cond;
    }

    /**
     * @param cond the cond to set
     */
    public void setCond(Map<String, Integer> cond) {
        this.cond = cond;
    }

    /**
     * @return the stats
     */
    public Map<String, Integer> getStats() {
        return stats;
    }

    /**
     * @param stats the stats to set
     */
    public void setStats(Map<String, Integer> stats) {
        this.stats = stats;
    }

    /**
     * @return the scrap
     */
    public Map<String, Integer> getScrap() {
        return scrap;
    }

    /**
     * @param scrap the scrap to set
     */
    public void setScrap(Map<String, Integer> scrap) {
        this.scrap = scrap;
    }

    /**
     * @return the refittable
     */
    public List<String> getRefittable() {
        return refittable;
    }

    /**
     * @param refittable the refittable to set
     */
    public void setRefittable(List<String> refittable) {
        this.refittable = refittable;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return the improvement
     */
    public Map<String, Object> getImprovement() {
        return improvement;
    }

    /**
     * @param improvement the improvement to set
     */
    public void setImprovement(Map<String, Object> improvement) {
        this.improvement = improvement;
    }

    /**
     * @return the wikiJp
     */
    public String getWikiJp() {
        return wikiJp;
    }

    /**
     * @param wikiJp the wikiJp to set
     */
    public void setWikiJp(String wikiJp) {
        this.wikiJp = wikiJp;
    }

    /**
     * @return the wikiZh
     */
    public String getWikiZh() {
        return wikiZh;
    }

    /**
     * @param wikiZh the wikiZh to set
     */
    public void setWikiZh(String wikiZh) {
        this.wikiZh = wikiZh;
    }
    
}
