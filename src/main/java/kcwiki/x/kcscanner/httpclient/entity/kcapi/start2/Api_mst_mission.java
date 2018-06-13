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
public class Api_mst_mission
{
    private Integer api_id;

    private String api_disp_no;

    private Integer api_maparea_id;

    private String api_name;

    private String api_details;

    private Integer api_time;

    private Integer api_deck_num;

    private Integer api_difficulty;

    private double api_use_fuel;

    private Integer api_use_bull;

    private List<Integer> api_win_item1;

    private List<Integer> api_win_item2;

    private Integer api_return_flag;
    
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
        hash = 13 * hash + Objects.hashCode(this.api_id);
        hash = 13 * hash + Objects.hashCode(this.api_disp_no);
        hash = 13 * hash + Objects.hashCode(this.api_maparea_id);
        hash = 13 * hash + Objects.hashCode(this.api_name);
        hash = 13 * hash + Objects.hashCode(this.api_details);
        hash = 13 * hash + Objects.hashCode(this.api_time);
        hash = 13 * hash + Objects.hashCode(this.api_deck_num);
        hash = 13 * hash + Objects.hashCode(this.api_difficulty);
        hash = 13 * hash + (int) (Double.doubleToLongBits(this.api_use_fuel) ^ (Double.doubleToLongBits(this.api_use_fuel) >>> 32));
        hash = 13 * hash + Objects.hashCode(this.api_use_bull);
        hash = 13 * hash + Objects.hashCode(this.api_win_item1);
        hash = 13 * hash + Objects.hashCode(this.api_win_item2);
        hash = 13 * hash + Objects.hashCode(this.api_return_flag);
        return hash;
    }

    public void setApi_id(Integer api_id){
        this.api_id = api_id;
    }
    public Integer getApi_id(){
        return this.api_id;
    }
    public void setApi_disp_no(String api_disp_no){
        this.api_disp_no = api_disp_no;
    }
    public String getApi_disp_no(){
        return this.api_disp_no;
    }
    public void setApi_maparea_id(Integer api_maparea_id){
        this.api_maparea_id = api_maparea_id;
    }
    public Integer getApi_maparea_id(){
        return this.api_maparea_id;
    }
    public void setApi_name(String api_name){
        this.api_name = api_name;
    }
    public String getApi_name(){
        return this.api_name;
    }
    public void setApi_details(String api_details){
        this.api_details = api_details;
    }
    public String getApi_details(){
        return this.api_details;
    }
    public void setApi_time(Integer api_time){
        this.api_time = api_time;
    }
    public Integer getApi_time(){
        return this.api_time;
    }
    public void setApi_deck_num(Integer api_deck_num){
        this.api_deck_num = api_deck_num;
    }
    public Integer getApi_deck_num(){
        return this.api_deck_num;
    }
    public void setApi_difficulty(Integer api_difficulty){
        this.api_difficulty = api_difficulty;
    }
    public Integer getApi_difficulty(){
        return this.api_difficulty;
    }
    public void setApi_use_fuel(double api_use_fuel){
        this.api_use_fuel = api_use_fuel;
    }
    public double getApi_use_fuel(){
        return this.api_use_fuel;
    }
    public void setApi_use_bull(Integer api_use_bull){
        this.api_use_bull = api_use_bull;
    }
    public Integer getApi_use_bull(){
        return this.api_use_bull;
    }
    public void setApi_win_item1(List<Integer> api_win_item1){
        this.api_win_item1 = api_win_item1;
    }
    public List<Integer> getApi_win_item1(){
        return this.api_win_item1;
    }
    public void setApi_win_item2(List<Integer> api_win_item2){
        this.api_win_item2 = api_win_item2;
    }
    public List<Integer> getApi_win_item2(){
        return this.api_win_item2;
    }
    public void setApi_return_flag(Integer api_return_flag){
        this.api_return_flag = api_return_flag;
    }
    public Integer getApi_return_flag(){
        return this.api_return_flag;
    }
}
