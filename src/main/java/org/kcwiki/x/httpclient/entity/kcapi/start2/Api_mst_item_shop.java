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
public class Api_mst_item_shop
{
    private List<Integer> api_cabinet_1;

    private List<Integer> api_cabinet_2;

    public void setApi_cabinet_1(List<Integer> api_cabinet_1){
        this.api_cabinet_1 = api_cabinet_1;
    }
    public List<Integer> getApi_cabinet_1(){
        return this.api_cabinet_1;
    }
    public void setApi_cabinet_2(List<Integer> api_cabinet_2){
        this.api_cabinet_2 = api_cabinet_2;
    }
    public List<Integer> getApi_cabinet_2(){
        return this.api_cabinet_2;
    }
}
