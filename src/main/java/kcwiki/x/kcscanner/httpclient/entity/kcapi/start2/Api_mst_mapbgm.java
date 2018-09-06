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
public class Api_mst_mapbgm
{
    private Integer api_id;

    private Integer api_maparea_id;

    private Integer api_no;

    private Integer api_moving_bgm;

    private List<Integer> api_map_bgm;

    private List<Integer> api_boss_bgm;
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Api_mst_mapbgm)){
            return false;
        }
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.api_id);
        hash = 13 * hash + Objects.hashCode(this.api_maparea_id);
        hash = 13 * hash + Objects.hashCode(this.api_no);
        hash = 13 * hash + Objects.hashCode(this.api_moving_bgm);
        hash = 13 * hash + Objects.hashCode(this.api_map_bgm);
        hash = 13 * hash + Objects.hashCode(this.api_boss_bgm);
        return hash;
    }

    public void setApi_id(Integer api_id){
        this.api_id = api_id;
    }
    public Integer getApi_id(){
        return this.api_id;
    }
    public void setApi_maparea_id(Integer api_maparea_id){
        this.api_maparea_id = api_maparea_id;
    }
    public Integer getApi_maparea_id(){
        return this.api_maparea_id;
    }
    public void setApi_no(Integer api_no){
        this.api_no = api_no;
    }
    public Integer getApi_no(){
        return this.api_no;
    }
    public void setApi_moving_bgm(Integer api_moving_bgm){
        this.api_moving_bgm = api_moving_bgm;
    }
    public Integer getApi_moving_bgm(){
        return this.api_moving_bgm;
    }
    public void setApi_map_bgm(List<Integer> api_map_bgm){
        this.api_map_bgm = api_map_bgm;
    }
    public List<Integer> getApi_map_bgm(){
        return this.api_map_bgm;
    }
    public void setApi_boss_bgm(List<Integer> api_boss_bgm){
        this.api_boss_bgm = api_boss_bgm;
    }
    public List<Integer> getApi_boss_bgm(){
        return this.api_boss_bgm;
    }
}
