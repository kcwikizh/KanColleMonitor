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
public class Api_mst_mapinfo
{
    private Integer api_id;

    private Integer api_maparea_id;

    private Integer api_no;

    private String api_name;

    private Integer api_level;

    private String api_opetext;

    private String api_infotext;

    private List<Integer> api_item;

    private String api_max_maphp;

    private String api_required_defeat_count;

    private List<Integer> api_sally_flag;

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
    public void setApi_name(String api_name){
        this.api_name = api_name;
    }
    public String getApi_name(){
        return this.api_name;
    }
    public void setApi_level(Integer api_level){
        this.api_level = api_level;
    }
    public Integer getApi_level(){
        return this.api_level;
    }
    public void setApi_opetext(String api_opetext){
        this.api_opetext = api_opetext;
    }
    public String getApi_opetext(){
        return this.api_opetext;
    }
    public void setApi_infotext(String api_infotext){
        this.api_infotext = api_infotext;
    }
    public String getApi_infotext(){
        return this.api_infotext;
    }
    public void setApi_item(List<Integer> api_item){
        this.api_item = api_item;
    }
    public List<Integer> getApi_item(){
        return this.api_item;
    }
    public void setApi_max_maphp(String api_max_maphp){
        this.api_max_maphp = api_max_maphp;
    }
    public String getApi_max_maphp(){
        return this.api_max_maphp;
    }
    public void setApi_required_defeat_count(String api_required_defeat_count){
        this.api_required_defeat_count = api_required_defeat_count;
    }
    public String getApi_required_defeat_count(){
        return this.api_required_defeat_count;
    }
    public void setApi_sally_flag(List<Integer> api_sally_flag){
        this.api_sally_flag = api_sally_flag;
    }
    public List<Integer> getApi_sally_flag(){
        return this.api_sally_flag;
    }
}
