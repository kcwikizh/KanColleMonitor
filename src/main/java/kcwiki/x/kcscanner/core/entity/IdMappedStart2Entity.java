/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_bgm;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_equip_exslot_ship;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_furniture;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_furnituregraph;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_maparea;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_mapbgm;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_mapinfo;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_mission;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_payitem;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_ship;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_shipgraph;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_shipupgrade;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_slotitem;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_slotitem_equiptype;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_stype;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_useitem;

/**
 *
 * @author iHaru
 */
public class IdMappedStart2Entity {
    
    private Map<Integer, Api_mst_ship> api_mst_ship = new HashMap<>();

    private Map<Integer, Api_mst_shipgraph> api_mst_shipgraph = new HashMap<>();

    private Map<Integer, Api_mst_slotitem_equiptype> api_mst_slotitem_equiptype = new HashMap<>();

    private Map<Integer, Api_mst_equip_exslot_ship> api_mst_equip_exslot_ship = new HashMap<>();

    private Map<Integer, Api_mst_stype> api_mst_stype = new HashMap<>();

    private Map<Integer, Api_mst_slotitem> api_mst_slotitem = new HashMap<>();

    private Map<Integer, Api_mst_furniture> api_mst_furniture = new HashMap<>();

    private Map<Integer, Api_mst_furnituregraph> api_mst_furnituregraph = new HashMap<>();

    private Map<Integer, Api_mst_useitem> api_mst_useitem = new HashMap<>();

    private Map<Integer, Api_mst_payitem> api_mst_payitem = new HashMap<>();

    private Map<Integer, Api_mst_maparea> api_mst_maparea = new HashMap<>();

    private Map<Integer, Api_mst_mapinfo> api_mst_mapinfo = new HashMap<>();

    private Map<Integer, Api_mst_mapbgm> api_mst_mapbgm = new HashMap<>();

    private Map<Integer, Api_mst_mission> api_mst_mission = new HashMap<>();

    private Map<Integer, List<Api_mst_shipupgrade>> api_mst_shipupgrade = new HashMap<>();

    private Map<Integer, Api_mst_bgm> api_mst_bgm = new HashMap<>();

    /**
     * @return the api_mst_ship
     */
    public Map<Integer, Api_mst_ship> getApi_mst_ship() {
        return api_mst_ship;
    }

    /**
     * @param api_mst_ship the api_mst_ship to set
     */
    public void setApi_mst_ship(Map<Integer, Api_mst_ship> api_mst_ship) {
        this.api_mst_ship = api_mst_ship;
    }

    /**
     * @return the api_mst_shipgraph
     */
    public Map<Integer, Api_mst_shipgraph> getApi_mst_shipgraph() {
        return api_mst_shipgraph;
    }

    /**
     * @param api_mst_shipgraph the api_mst_shipgraph to set
     */
    public void setApi_mst_shipgraph(Map<Integer, Api_mst_shipgraph> api_mst_shipgraph) {
        this.api_mst_shipgraph = api_mst_shipgraph;
    }

    /**
     * @return the api_mst_slotitem_equiptype
     */
    public Map<Integer, Api_mst_slotitem_equiptype> getApi_mst_slotitem_equiptype() {
        return api_mst_slotitem_equiptype;
    }

    /**
     * @param api_mst_slotitem_equiptype the api_mst_slotitem_equiptype to set
     */
    public void setApi_mst_slotitem_equiptype(Map<Integer, Api_mst_slotitem_equiptype> api_mst_slotitem_equiptype) {
        this.api_mst_slotitem_equiptype = api_mst_slotitem_equiptype;
    }

    /**
     * @return the api_mst_equip_exslot_ship
     */
    public Map<Integer, Api_mst_equip_exslot_ship> getApi_mst_equip_exslot_ship() {
        return api_mst_equip_exslot_ship;
    }

    /**
     * @param api_mst_equip_exslot_ship the api_mst_equip_exslot_ship to set
     */
    public void setApi_mst_equip_exslot_ship(Map<Integer, Api_mst_equip_exslot_ship> api_mst_equip_exslot_ship) {
        this.api_mst_equip_exslot_ship = api_mst_equip_exslot_ship;
    }

    /**
     * @return the api_mst_stype
     */
    public Map<Integer, Api_mst_stype> getApi_mst_stype() {
        return api_mst_stype;
    }

    /**
     * @param api_mst_stype the api_mst_stype to set
     */
    public void setApi_mst_stype(Map<Integer, Api_mst_stype> api_mst_stype) {
        this.api_mst_stype = api_mst_stype;
    }

    /**
     * @return the api_mst_slotitem
     */
    public Map<Integer, Api_mst_slotitem> getApi_mst_slotitem() {
        return api_mst_slotitem;
    }

    /**
     * @param api_mst_slotitem the api_mst_slotitem to set
     */
    public void setApi_mst_slotitem(Map<Integer, Api_mst_slotitem> api_mst_slotitem) {
        this.api_mst_slotitem = api_mst_slotitem;
    }

    /**
     * @return the api_mst_furniture
     */
    public Map<Integer, Api_mst_furniture> getApi_mst_furniture() {
        return api_mst_furniture;
    }

    /**
     * @param api_mst_furniture the api_mst_furniture to set
     */
    public void setApi_mst_furniture(Map<Integer, Api_mst_furniture> api_mst_furniture) {
        this.api_mst_furniture = api_mst_furniture;
    }

    /**
     * @return the api_mst_furnituregraph
     */
    public Map<Integer, Api_mst_furnituregraph> getApi_mst_furnituregraph() {
        return api_mst_furnituregraph;
    }

    /**
     * @param api_mst_furnituregraph the api_mst_furnituregraph to set
     */
    public void setApi_mst_furnituregraph(Map<Integer, Api_mst_furnituregraph> api_mst_furnituregraph) {
        this.api_mst_furnituregraph = api_mst_furnituregraph;
    }

    /**
     * @return the api_mst_useitem
     */
    public Map<Integer, Api_mst_useitem> getApi_mst_useitem() {
        return api_mst_useitem;
    }

    /**
     * @param api_mst_useitem the api_mst_useitem to set
     */
    public void setApi_mst_useitem(Map<Integer, Api_mst_useitem> api_mst_useitem) {
        this.api_mst_useitem = api_mst_useitem;
    }

    /**
     * @return the api_mst_payitem
     */
    public Map<Integer, Api_mst_payitem> getApi_mst_payitem() {
        return api_mst_payitem;
    }

    /**
     * @param api_mst_payitem the api_mst_payitem to set
     */
    public void setApi_mst_payitem(Map<Integer, Api_mst_payitem> api_mst_payitem) {
        this.api_mst_payitem = api_mst_payitem;
    }

    /**
     * @return the api_mst_maparea
     */
    public Map<Integer, Api_mst_maparea> getApi_mst_maparea() {
        return api_mst_maparea;
    }

    /**
     * @param api_mst_maparea the api_mst_maparea to set
     */
    public void setApi_mst_maparea(Map<Integer, Api_mst_maparea> api_mst_maparea) {
        this.api_mst_maparea = api_mst_maparea;
    }

    /**
     * @return the api_mst_mapinfo
     */
    public Map<Integer, Api_mst_mapinfo> getApi_mst_mapinfo() {
        return api_mst_mapinfo;
    }

    /**
     * @param api_mst_mapinfo the api_mst_mapinfo to set
     */
    public void setApi_mst_mapinfo(Map<Integer, Api_mst_mapinfo> api_mst_mapinfo) {
        this.api_mst_mapinfo = api_mst_mapinfo;
    }

    /**
     * @return the api_mst_mapbgm
     */
    public Map<Integer, Api_mst_mapbgm> getApi_mst_mapbgm() {
        return api_mst_mapbgm;
    }

    /**
     * @param api_mst_mapbgm the api_mst_mapbgm to set
     */
    public void setApi_mst_mapbgm(Map<Integer, Api_mst_mapbgm> api_mst_mapbgm) {
        this.api_mst_mapbgm = api_mst_mapbgm;
    }

    /**
     * @return the api_mst_mission
     */
    public Map<Integer, Api_mst_mission> getApi_mst_mission() {
        return api_mst_mission;
    }

    /**
     * @param api_mst_mission the api_mst_mission to set
     */
    public void setApi_mst_mission(Map<Integer, Api_mst_mission> api_mst_mission) {
        this.api_mst_mission = api_mst_mission;
    }

    /**
     * @return the api_mst_bgm
     */
    public Map<Integer, Api_mst_bgm> getApi_mst_bgm() {
        return api_mst_bgm;
    }

    /**
     * @param api_mst_bgm the api_mst_bgm to set
     */
    public void setApi_mst_bgm(Map<Integer, Api_mst_bgm> api_mst_bgm) {
        this.api_mst_bgm = api_mst_bgm;
    }

    /**
     * @return the api_mst_shipupgrade
     */
    public Map<Integer, List<Api_mst_shipupgrade>> getApi_mst_shipupgrade() {
        return api_mst_shipupgrade;
    }

    /**
     * @param api_mst_shipupgrade the api_mst_shipupgrade to set
     */
    public void setApi_mst_shipupgrade(Map<Integer, List<Api_mst_shipupgrade>> api_mst_shipupgrade) {
        this.api_mst_shipupgrade = api_mst_shipupgrade;
    }
}
