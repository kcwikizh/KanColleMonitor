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
 */
public class Data {

    /**
     * @return the taikyu
     */
    public List<Integer> getTaikyu() {
        return taikyu;
    }

    /**
     * @param taikyu the taikyu to set
     */
    public void setTaikyu(List<Integer> taikyu) {
        this.taikyu = taikyu;
    }

    /**
     * @return the karyoku
     */
    public List<Integer> getKaryoku() {
        return karyoku;
    }

    /**
     * @param karyoku the karyoku to set
     */
    public void setKaryoku(List<Integer> karyoku) {
        this.karyoku = karyoku;
    }

    /**
     * @return the raisou
     */
    public List<Integer> getRaisou() {
        return raisou;
    }

    /**
     * @param raisou the raisou to set
     */
    public void setRaisou(List<Integer> raisou) {
        this.raisou = raisou;
    }

    /**
     * @return the taiku
     */
    public List<Integer> getTaiku() {
        return taiku;
    }

    /**
     * @param taiku the taiku to set
     */
    public void setTaiku(List<Integer> taiku) {
        this.taiku = taiku;
    }

    /**
     * @return the soukou
     */
    public List<Integer> getSoukou() {
        return soukou;
    }

    /**
     * @param soukou the soukou to set
     */
    public void setSoukou(List<Integer> soukou) {
        this.soukou = soukou;
    }

    /**
     * @return the taisen
     */
    public List<Integer> getTaisen() {
        return taisen;
    }

    /**
     * @param taisen the taisen to set
     */
    public void setTaisen(List<Integer> taisen) {
        this.taisen = taisen;
    }

    /**
     * @return the kaihi
     */
    public List<Integer> getKaihi() {
        return kaihi;
    }

    /**
     * @param kaihi the kaihi to set
     */
    public void setKaihi(List<Integer> kaihi) {
        this.kaihi = kaihi;
    }

    /**
     * @return the sakuteki
     */
    public List<Integer> getSakuteki() {
        return sakuteki;
    }

    /**
     * @param sakuteki the sakuteki to set
     */
    public void setSakuteki(List<Integer> sakuteki) {
        this.sakuteki = sakuteki;
    }

    /**
     * @return the luck
     */
    public List<Integer> getLuck() {
        return luck;
    }

    /**
     * @param luck the luck to set
     */
    public void setLuck(List<Integer> luck) {
        this.luck = luck;
    }

    /**
     * @return the sokuryoku
     */
    public int getSokuryoku() {
        return sokuryoku;
    }

    /**
     * @param sokuryoku the sokuryoku to set
     */
    public void setSokuryoku(int sokuryoku) {
        this.sokuryoku = sokuryoku;
    }

    /**
     * @return the shatei
     */
    public int getShatei() {
        return shatei;
    }

    /**
     * @param shatei the shatei to set
     */
    public void setShatei(int shatei) {
        this.shatei = shatei;
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
    
    @JsonProperty(value="耐久")
    private List<Integer> taikyu;
    
    @JsonProperty(value="火力")
    private List<Integer> karyoku;
    
    @JsonProperty(value="雷装")
    private List<Integer> raisou;
    
    @JsonProperty(value="对空")
    private List<Integer> taiku;
    
    @JsonProperty(value="装甲")
    private List<Integer> soukou;
    
    @JsonProperty(value="对潜")
    private List<Integer> taisen;
    
    @JsonProperty(value="回避")
    private List<Integer> kaihi;
    
    @JsonProperty(value="索敌")
    private List<Integer> sakuteki;
    
    @JsonProperty(value="运")
    private List<Integer> luck;
    
    @JsonProperty(value="速力")
    private int sokuryoku;
    
    @JsonProperty(value="射程")
    private int shatei;
    
    @JsonProperty(value="稀有")
    private int 稀有度;
    
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
}
