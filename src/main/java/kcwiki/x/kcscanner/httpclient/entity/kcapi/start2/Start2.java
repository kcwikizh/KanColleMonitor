/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.httpclient.entity.kcapi.start2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 *
 * @author x5171
 */

//@JsonIgnoreProperties(ignoreUnknown = true)
public class Start2
{
    private List<Api_mst_ship> api_mst_ship;

    private List<Api_mst_shipgraph> api_mst_shipgraph;

    private List<Api_mst_slotitem_equiptype> api_mst_slotitem_equiptype;

    private List<Integer> api_mst_equip_exslot;

    private List<Api_mst_equip_exslot_ship> api_mst_equip_exslot_ship;

    private List<Api_mst_stype> api_mst_stype;

    private List<Api_mst_slotitem> api_mst_slotitem;

    private List<Api_mst_furniture> api_mst_furniture;

    private List<Api_mst_furnituregraph> api_mst_furnituregraph;

    private List<Api_mst_useitem> api_mst_useitem;

    private List<Api_mst_payitem> api_mst_payitem;

    private Api_mst_item_shop api_mst_item_shop;

    private List<Api_mst_maparea> api_mst_maparea;

    private List<Api_mst_mapinfo> api_mst_mapinfo;

    private List<Api_mst_mapbgm> api_mst_mapbgm;

    private List<Api_mst_mission> api_mst_mission;

    private Api_mst_const api_mst_const;

    private List<Api_mst_shipupgrade> api_mst_shipupgrade;

    private List<Api_mst_bgm> api_mst_bgm;
    
    private List<Api_mst_equip_ship> api_mst_equip_ship;

    private Integer api_register_status;

    public void setApi_mst_ship(List<Api_mst_ship> api_mst_ship){
        this.api_mst_ship = api_mst_ship;
    }
    public List<Api_mst_ship> getApi_mst_ship(){
        return this.api_mst_ship;
    }
    public void setApi_mst_shipgraph(List<Api_mst_shipgraph> api_mst_shipgraph){
        this.api_mst_shipgraph = api_mst_shipgraph;
    }
    public List<Api_mst_shipgraph> getApi_mst_shipgraph(){
        return this.api_mst_shipgraph;
    }
    public void setApi_mst_slotitem_equiptype(List<Api_mst_slotitem_equiptype> api_mst_slotitem_equiptype){
        this.api_mst_slotitem_equiptype = api_mst_slotitem_equiptype;
    }
    public List<Api_mst_slotitem_equiptype> getApi_mst_slotitem_equiptype(){
        return this.api_mst_slotitem_equiptype;
    }
    public void setApi_mst_equip_exslot(List<Integer> api_mst_equip_exslot){
        this.api_mst_equip_exslot = api_mst_equip_exslot;
    }
    public List<Integer> getApi_mst_equip_exslot(){
        return this.api_mst_equip_exslot;
    }
    public void setApi_mst_equip_exslot_ship(List<Api_mst_equip_exslot_ship> api_mst_equip_exslot_ship){
        this.api_mst_equip_exslot_ship = api_mst_equip_exslot_ship;
    }
    public List<Api_mst_equip_exslot_ship> getApi_mst_equip_exslot_ship(){
        return this.api_mst_equip_exslot_ship;
    }
    public void setApi_mst_stype(List<Api_mst_stype> api_mst_stype){
        this.api_mst_stype = api_mst_stype;
    }
    public List<Api_mst_stype> getApi_mst_stype(){
        return this.api_mst_stype;
    }
    public void setApi_mst_slotitem(List<Api_mst_slotitem> api_mst_slotitem){
        this.api_mst_slotitem = api_mst_slotitem;
    }
    public List<Api_mst_slotitem> getApi_mst_slotitem(){
        return this.api_mst_slotitem;
    }
    public void setApi_mst_furniture(List<Api_mst_furniture> api_mst_furniture){
        this.api_mst_furniture = api_mst_furniture;
    }
    public List<Api_mst_furniture> getApi_mst_furniture(){
        return this.api_mst_furniture;
    }
    public void setApi_mst_furnituregraph(List<Api_mst_furnituregraph> api_mst_furnituregraph){
        this.api_mst_furnituregraph = api_mst_furnituregraph;
    }
    public List<Api_mst_furnituregraph> getApi_mst_furnituregraph(){
        return this.api_mst_furnituregraph;
    }
    public void setApi_mst_useitem(List<Api_mst_useitem> api_mst_useitem){
        this.api_mst_useitem = api_mst_useitem;
    }
    public List<Api_mst_useitem> getApi_mst_useitem(){
        return this.api_mst_useitem;
    }
    public void setApi_mst_payitem(List<Api_mst_payitem> api_mst_payitem){
        this.api_mst_payitem = api_mst_payitem;
    }
    public List<Api_mst_payitem> getApi_mst_payitem(){
        return this.api_mst_payitem;
    }
    public void setApi_mst_item_shop(Api_mst_item_shop api_mst_item_shop){
        this.api_mst_item_shop = api_mst_item_shop;
    }
    public Api_mst_item_shop getApi_mst_item_shop(){
        return this.api_mst_item_shop;
    }
    public void setApi_mst_maparea(List<Api_mst_maparea> api_mst_maparea){
        this.api_mst_maparea = api_mst_maparea;
    }
    public List<Api_mst_maparea> getApi_mst_maparea(){
        return this.api_mst_maparea;
    }
    public void setApi_mst_mapinfo(List<Api_mst_mapinfo> api_mst_mapinfo){
        this.api_mst_mapinfo = api_mst_mapinfo;
    }
    public List<Api_mst_mapinfo> getApi_mst_mapinfo(){
        return this.api_mst_mapinfo;
    }
    public void setApi_mst_mapbgm(List<Api_mst_mapbgm> api_mst_mapbgm){
        this.api_mst_mapbgm = api_mst_mapbgm;
    }
    public List<Api_mst_mapbgm> getApi_mst_mapbgm(){
        return this.api_mst_mapbgm;
    }
    public void setApi_mst_mission(List<Api_mst_mission> api_mst_mission){
        this.api_mst_mission = api_mst_mission;
    }
    public List<Api_mst_mission> getApi_mst_mission(){
        return this.api_mst_mission;
    }
    public void setApi_mst_const(Api_mst_const api_mst_const){
        this.api_mst_const = api_mst_const;
    }
    public Api_mst_const getApi_mst_const(){
        return this.api_mst_const;
    }
    public void setApi_mst_shipupgrade(List<Api_mst_shipupgrade> api_mst_shipupgrade){
        this.api_mst_shipupgrade = api_mst_shipupgrade;
    }
    public List<Api_mst_shipupgrade> getApi_mst_shipupgrade(){
        return this.api_mst_shipupgrade;
    }
    public void setApi_mst_bgm(List<Api_mst_bgm> api_mst_bgm){
        this.api_mst_bgm = api_mst_bgm;
    }
    public List<Api_mst_bgm> getApi_mst_bgm(){
        return this.api_mst_bgm;
    }
    public void setApi_register_status(Integer api_register_status){
        this.api_register_status = api_register_status;
    }
    public Integer getApi_register_status(){
        return this.api_register_status;
    }
}
