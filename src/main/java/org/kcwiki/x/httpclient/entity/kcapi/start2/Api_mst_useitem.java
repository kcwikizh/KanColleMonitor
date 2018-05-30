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
public class Api_mst_useitem
{
    private Integer api_id;

    private Integer api_usetype;

    private Integer api_category;

    private String api_name;

    private List<String> api_description;

    private Integer api_price;

    public void setApi_id(Integer api_id){
        this.api_id = api_id;
    }
    public Integer getApi_id(){
        return this.api_id;
    }
    public void setApi_usetype(Integer api_usetype){
        this.api_usetype = api_usetype;
    }
    public Integer getApi_usetype(){
        return this.api_usetype;
    }
    public void setApi_category(Integer api_category){
        this.api_category = api_category;
    }
    public Integer getApi_category(){
        return this.api_category;
    }
    public void setApi_name(String api_name){
        this.api_name = api_name;
    }
    public String getApi_name(){
        return this.api_name;
    }
    public void setApi_description(List<String> api_description){
        this.api_description = api_description;
    }
    public List<String> getApi_description(){
        return this.api_description;
    }
    public void setApi_price(Integer api_price){
        this.api_price = api_price;
    }
    public Integer getApi_price(){
        return this.api_price;
    }
}
