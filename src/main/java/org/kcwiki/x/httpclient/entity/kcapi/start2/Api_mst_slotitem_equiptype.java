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
public class Api_mst_slotitem_equiptype
{
    private Integer api_id;

    private String api_name;

    private Integer api_show_flg;

    public void setApi_id(Integer api_id){
        this.api_id = api_id;
    }
    public Integer getApi_id(){
        return this.api_id;
    }
    public void setApi_name(String api_name){
        this.api_name = api_name;
    }
    public String getApi_name(){
        return this.api_name;
    }
    public void setApi_show_flg(Integer api_show_flg){
        this.api_show_flg = api_show_flg;
    }
    public Integer getApi_show_flg(){
        return this.api_show_flg;
    }
}
