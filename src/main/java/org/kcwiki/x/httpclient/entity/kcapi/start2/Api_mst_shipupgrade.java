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
public class Api_mst_shipupgrade
{
    private Integer api_id;

    private Integer api_current_ship_id;

    private Integer api_original_ship_id;

    private Integer api_upgrade_type;

    private Integer api_upgrade_level;

    private Integer api_drawing_count;

    private Integer api_catapult_count;

    private Integer api_report_count;

    private Integer api_sortno;

    public void setApi_id(Integer api_id){
        this.api_id = api_id;
    }
    public Integer getApi_id(){
        return this.api_id;
    }
    public void setApi_current_ship_id(Integer api_current_ship_id){
        this.api_current_ship_id = api_current_ship_id;
    }
    public Integer getApi_current_ship_id(){
        return this.api_current_ship_id;
    }
    public void setApi_original_ship_id(Integer api_original_ship_id){
        this.api_original_ship_id = api_original_ship_id;
    }
    public Integer getApi_original_ship_id(){
        return this.api_original_ship_id;
    }
    public void setApi_upgrade_type(Integer api_upgrade_type){
        this.api_upgrade_type = api_upgrade_type;
    }
    public Integer getApi_upgrade_type(){
        return this.api_upgrade_type;
    }
    public void setApi_upgrade_level(Integer api_upgrade_level){
        this.api_upgrade_level = api_upgrade_level;
    }
    public Integer getApi_upgrade_level(){
        return this.api_upgrade_level;
    }
    public void setApi_drawing_count(Integer api_drawing_count){
        this.api_drawing_count = api_drawing_count;
    }
    public Integer getApi_drawing_count(){
        return this.api_drawing_count;
    }
    public void setApi_catapult_count(Integer api_catapult_count){
        this.api_catapult_count = api_catapult_count;
    }
    public Integer getApi_catapult_count(){
        return this.api_catapult_count;
    }
    public void setApi_report_count(Integer api_report_count){
        this.api_report_count = api_report_count;
    }
    public Integer getApi_report_count(){
        return this.api_report_count;
    }
    public void setApi_sortno(Integer api_sortno){
        this.api_sortno = api_sortno;
    }
    public Integer getApi_sortno(){
        return this.api_sortno;
    }
}
