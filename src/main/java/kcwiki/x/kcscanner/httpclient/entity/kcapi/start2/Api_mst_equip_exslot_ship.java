/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.httpclient.entity.kcapi.start2;

/**
 *
 * @author iHaru
 */
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.EqualsExclude;
public class Api_mst_equip_exslot_ship
{
    @EqualsExclude
    private Integer api_slotitem_id;

    private List<Integer> api_ship_ids;
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Api_mst_equip_exslot_ship)){
            return false;
        }
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.api_ship_ids);
        return hash;
    }

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
