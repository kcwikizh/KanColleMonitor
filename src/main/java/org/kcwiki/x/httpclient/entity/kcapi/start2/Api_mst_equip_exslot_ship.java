/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kcwiki.x.httpclient.entity.kcapi.start2;

/**
 *
 * @author x5171
 */
import java.util.ArrayList;
import java.util.List;
public class Api_mst_equip_exslot_ship
{
    private Integer api_slotitem_id;

    private List<Integer> api_ship_ids;

    public void setApi_slotitem_id(Integer api_slotitem_id){
        this.api_slotitem_id = api_slotitem_id;
    }
    public Integer getApi_slotitem_id(){
        return this.api_slotitem_id;
    }
    public void setApi_ship_ids(List<Integer> api_ship_ids){
        this.api_ship_ids = api_ship_ids;
    }
    public List<Integer> getApi_ship_ids(){
        return this.api_ship_ids;
    }
}
