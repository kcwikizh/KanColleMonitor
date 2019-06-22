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
public class Scrap {
    @JsonProperty(value="燃料")
    private int fuel;
    
    @JsonProperty(value="弹药")
    private int ammo;
    
    @JsonProperty(value="燃料")
    private int steel;
    
    @JsonProperty(value="铝")
    private int bauxite;

    /**
     * @return the fuel
     */
    public int getFuel() {
        return fuel;
    }

    /**
     * @param fuel the fuel to set
     */
    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    /**
     * @return the ammo
     */
    public int getAmmo() {
        return ammo;
    }

    /**
     * @param ammo the ammo to set
     */
    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    /**
     * @return the steel
     */
    public int getSteel() {
        return steel;
    }

    /**
     * @param steel the steel to set
     */
    public void setSteel(int steel) {
        this.steel = steel;
    }

    /**
     * @return the bauxite
     */
    public int getBauxite() {
        return bauxite;
    }

    /**
     * @param bauxite the bauxite to set
     */
    public void setBauxite(int bauxite) {
        this.bauxite = bauxite;
    }
}
