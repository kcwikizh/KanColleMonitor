/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.httpclient.entity.kcapi.start2;

/**
 *
 * @author x5171
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.EqualsExclude;
public class Api_mst_payitem
{
    @EqualsExclude
    private Integer api_id;

    private Integer api_type;

    private String api_name;

    private String api_description;

    private List<Integer> api_item;

    private Integer api_price;
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Api_mst_payitem)){
            return false;
        }
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.api_type);
        hash = 89 * hash + Objects.hashCode(this.api_name);
        hash = 89 * hash + Objects.hashCode(this.api_description);
        hash = 89 * hash + Objects.hashCode(this.api_item);
        hash = 89 * hash + Objects.hashCode(this.api_price);
        return hash;
    }

    public void setApi_id(Integer api_id){
        this.api_id = api_id;
    }
    public Integer getApi_id(){
        return this.api_id;
    }
    public void setApi_type(Integer api_type){
        this.api_type = api_type;
    }
    public Integer getApi_type(){
        return this.api_type;
    }
    public void setApi_name(String api_name){
        this.api_name = api_name;
    }
    public String getApi_name(){
        return this.api_name;
    }
    public void setApi_description(String api_description){
        this.api_description = api_description;
    }
    public String getApi_description(){
        return this.api_description;
    }
    public void setApi_item(List<Integer> api_item){
        this.api_item = api_item;
    }
    public List<Integer> getApi_item(){
        return this.api_item;
    }
    public void setApi_price(Integer api_price){
        this.api_price = api_price;
    }
    public Integer getApi_price(){
        return this.api_price;
    }
}
