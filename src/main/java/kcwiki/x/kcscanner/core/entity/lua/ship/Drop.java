/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.entity.lua.ship;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author iHaru
 */
public class Drop {
    @JsonProperty(value="掉落")
    private int drop;
    
    @JsonProperty(value="改造")
    private int remodel;
    
    @JsonProperty(value="建造")
    private int build;
    
    @JsonProperty(value="时间")
    private int time;

    /**
     * @return the drop
     */
    public int getDrop() {
        return drop;
    }

    /**
     * @param drop the drop to set
     */
    public void setDrop(int drop) {
        this.drop = drop;
    }

    /**
     * @return the remodel
     */
    public int getRemodel() {
        return remodel;
    }

    /**
     * @param remodel the remodel to set
     */
    public void setRemodel(int remodel) {
        this.remodel = remodel;
    }

    /**
     * @return the build
     */
    public int getBuild() {
        return build;
    }

    /**
     * @param build the build to set
     */
    public void setBuild(int build) {
        this.build = build;
    }

    /**
     * @return the time
     */
    public int getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(int time) {
        this.time = time;
    }
}
