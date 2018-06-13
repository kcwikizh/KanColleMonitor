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
public class Api_boko_max_ships
{
    private String api_string_value;

    private Integer api_int_value;

    public void setApi_string_value(String api_string_value){
        this.api_string_value = api_string_value;
    }
    public String getApi_string_value(){
        return this.api_string_value;
    }

    /**
     * @return the api_int_value
     */
    public Integer getApi_int_value() {
        return api_int_value;
    }

    /**
     * @param api_int_value the api_int_value to set
     */
    public void setApi_int_value(Integer api_int_value) {
        this.api_int_value = api_int_value;
    }
}
