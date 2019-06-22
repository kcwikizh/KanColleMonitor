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
public class Cost {

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
    @JsonProperty(value="燃料")
    private int fuel;
    
    @JsonProperty(value="弹药")
    private int ammo;
}
