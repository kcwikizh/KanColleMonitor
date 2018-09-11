/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.httpclient.entity.kcapi.start2;

import java.util.List;

/**
 *
 * @author x5171
 */
public class Api_mst_equip_ship {
    
    private int api_ship_id;

    private List<Integer> api_equip_type ;

    /**
     * @return the api_ship_id
     */
    public int getApi_ship_id() {
        return api_ship_id;
    }

    /**
     * @param api_ship_id the api_ship_id to set
     */
    public void setApi_ship_id(int api_ship_id) {
        this.api_ship_id = api_ship_id;
    }

    /**
     * @return the api_equip_type
     */
    public List<Integer> getApi_equip_type() {
        return api_equip_type;
    }

    /**
     * @param api_equip_type the api_equip_type to set
     */
    public void setApi_equip_type(List<Integer> api_equip_type) {
        this.api_equip_type = api_equip_type;
    }
}
