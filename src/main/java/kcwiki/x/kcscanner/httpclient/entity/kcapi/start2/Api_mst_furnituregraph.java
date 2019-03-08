/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.httpclient.entity.kcapi.start2;

/**
 *
 * @author iHaru
 */
public class Api_mst_furnituregraph
{
    private Integer api_id;

    private Integer api_type;

    private Integer api_no;

    private String api_filename;

    private String api_version;

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
    public void setApi_no(Integer api_no){
        this.api_no = api_no;
    }
    public Integer getApi_no(){
        return this.api_no;
    }
    public void setApi_filename(String api_filename){
        this.api_filename = api_filename;
    }
    public String getApi_filename(){
        return this.api_filename;
    }
    public void setApi_version(String api_version){
        this.api_version = api_version;
    }
    public String getApi_version(){
        return this.api_version;
    }
}