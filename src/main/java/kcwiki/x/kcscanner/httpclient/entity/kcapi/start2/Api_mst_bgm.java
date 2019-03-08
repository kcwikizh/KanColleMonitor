/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.httpclient.entity.kcapi.start2;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.EqualsExclude;

/**
 *
 * @author iHaru
 */
public class Api_mst_bgm
{
    @EqualsExclude
    private Integer api_id;

    private String api_name;
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Api_mst_bgm)){
            return false;
        }
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.api_name);
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
}
