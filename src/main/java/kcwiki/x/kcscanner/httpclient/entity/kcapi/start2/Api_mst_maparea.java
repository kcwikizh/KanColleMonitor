/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.httpclient.entity.kcapi.start2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 *
 * @author iHaru
 */
public class Api_mst_maparea
{
    private Integer api_id;

    private String api_name;

    private Integer api_type;
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Api_mst_maparea)){
            return false;
        }
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.api_id);
        hash = 47 * hash + Objects.hashCode(this.api_name);
        hash = 47 * hash + Objects.hashCode(this.api_type);
        return hash;
    }

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
    public void setApi_type(Integer api_type){
        this.api_type = api_type;
    }
    public Integer getApi_type(){
        return this.api_type;
    }
}
