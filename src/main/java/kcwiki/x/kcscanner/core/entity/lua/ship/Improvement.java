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
public class Improvement {
    @JsonProperty(value="火力")
    private List<Integer> karyoku;
    
    @JsonProperty(value="雷装")
    private List<Integer> raisou;
    
    @JsonProperty(value="对空")
    private List<Integer> taiku;
    
    @JsonProperty(value="装甲")
    private List<Integer> soukou;

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
}
