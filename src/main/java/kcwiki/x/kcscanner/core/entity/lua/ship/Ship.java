/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.entity.lua.ship;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 *
 * @author iHaru
 * https://github.com/kcwikizh/kcwiki-luatable
 * http://bot.kcwiki.moe/json/ships.json
 */
public class Ship {
    @JsonProperty(value="ID")
    private int id;
    
    @JsonProperty(value="图鉴号")
    private int sortno;
    
    @JsonProperty(value="日文名")
    private String jp;
    
    @JsonProperty(value="假名")
    private String yomi;
    
    @JsonProperty(value="中文名")
    private String zh;
    
    @JsonProperty(value="舰种")
    private int stype;
    
    @JsonProperty(value="级别")
    private List<Object> ctype;
    
    @JsonProperty(value="数据")
    private Data data;
    
    @JsonProperty(value="装备")
    private Slotitem slotitem;
    
    @JsonProperty(value="消耗")
    private Cost cost;
    
    @JsonProperty(value="获得")
    private Drop drop;
    
    @JsonProperty(value="改修")
    private Improvement improvement;
    
    @JsonProperty(value="解体")
    private Scrap scrap;
    
    @JsonProperty(value="改造")
    private Remodel remodel;
    
    @JsonProperty(value="画师")
    private String illustrator;
    
    @JsonProperty(value="声优")
    private String seiyuu;
    
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
     * @return the sortno
     */
    public int getSortno() {
        return sortno;
    }

    /**
     * @param sortno the sortno to set
     */
    public void setSortno(int sortno) {
        this.sortno = sortno;
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
     * @return the yomi
     */
    public String getYomi() {
        return yomi;
    }

    /**
     * @param yomi the yomi to set
     */
    public void setYomi(String yomi) {
        this.yomi = yomi;
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
     * @return the stype
     */
    public int getStype() {
        return stype;
    }

    /**
     * @param stype the stype to set
     */
    public void setStype(int stype) {
        this.stype = stype;
    }

    /**
     * @return the ctype
     */
    public List<Object> getCtype() {
        return ctype;
    }

    /**
     * @param ctype the ctype to set
     */
    public void setCtype(List<Object> ctype) {
        this.ctype = ctype;
    }

    /**
     * @return the data
     */
    public Data getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Data data) {
        this.data = data;
    }

    /**
     * @return the slotitem
     */
    public Slotitem getSlotitem() {
        return slotitem;
    }

    /**
     * @param slotitem the slotitem to set
     */
    public void setSlotitem(Slotitem slotitem) {
        this.slotitem = slotitem;
    }

    /**
     * @return the cost
     */
    public Cost getCost() {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(Cost cost) {
        this.cost = cost;
    }

    /**
     * @return the drop
     */
    public Drop getDrop() {
        return drop;
    }

    /**
     * @param drop the drop to set
     */
    public void setDrop(Drop drop) {
        this.drop = drop;
    }

    /**
     * @return the improvement
     */
    public Improvement getImprovement() {
        return improvement;
    }

    /**
     * @param improvement the improvement to set
     */
    public void setImprovement(Improvement improvement) {
        this.improvement = improvement;
    }

    /**
     * @return the scrap
     */
    public Scrap getScrap() {
        return scrap;
    }

    /**
     * @param scrap the scrap to set
     */
    public void setScrap(Scrap scrap) {
        this.scrap = scrap;
    }

    /**
     * @return the remodel
     */
    public Remodel getRemodel() {
        return remodel;
    }

    /**
     * @param remodel the remodel to set
     */
    public void setRemodel(Remodel remodel) {
        this.remodel = remodel;
    }

    /**
     * @return the illustrator
     */
    public String getIllustrator() {
        return illustrator;
    }

    /**
     * @param illustrator the illustrator to set
     */
    public void setIllustrator(String illustrator) {
        this.illustrator = illustrator;
    }

    /**
     * @return the seiyuu
     */
    public String getSeiyuu() {
        return seiyuu;
    }

    /**
     * @param seiyuu the seiyuu to set
     */
    public void setSeiyuu(String seiyuu) {
        this.seiyuu = seiyuu;
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
