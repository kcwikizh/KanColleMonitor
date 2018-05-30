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
import java.util.Map;
public class Api_mst_stype
{
    private Integer api_id;

    private Integer api_sortno;

    private String api_name;

    private Integer api_scnt;

    private Integer api_kcnt;

    private Map<String, Integer> api_equip_type;

    public void setApi_id(Integer api_id){
        this.api_id = api_id;
    }
    public Integer getApi_id(){
        return this.api_id;
    }
    public void setApi_sortno(Integer api_sortno){
        this.api_sortno = api_sortno;
    }
    public Integer getApi_sortno(){
        return this.api_sortno;
    }
    public void setApi_name(String api_name){
        this.api_name = api_name;
    }
    public String getApi_name(){
        return this.api_name;
    }
    public void setApi_scnt(Integer api_scnt){
        this.api_scnt = api_scnt;
    }
    public Integer getApi_scnt(){
        return this.api_scnt;
    }
    public void setApi_kcnt(Integer api_kcnt){
        this.api_kcnt = api_kcnt;
    }
    public Integer getApi_kcnt(){
        return this.api_kcnt;
    }

    /**
     * @return the api_equip_type
     */
    public Map<String, Integer> getApi_equip_type() {
        return api_equip_type;
    }

    /**
     * @param api_equip_type the api_equip_type to set
     */
    public void setApi_equip_type(Map<String, Integer> api_equip_type) {
        this.api_equip_type = api_equip_type;
    }
}
