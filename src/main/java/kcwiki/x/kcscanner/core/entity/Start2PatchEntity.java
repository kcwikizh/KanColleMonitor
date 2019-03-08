/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_bgm;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_const;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_equip_exslot_ship;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_item_shop;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_maparea;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_mapbgm;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_mapinfo;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_mission;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_payitem;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_slotitem;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_slotitem_equiptype;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_stype;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_useitem;

/**
 *
 * @author iHaru
 */
public class Start2PatchEntity {
    private List<CombinedShipEntity> newShip = new ArrayList<>();
    private List<CombinedShipEntity> modifiedShip = new ArrayList<>();

    private List<Api_mst_slotitem_equiptype> newSlotitemEquiptype = new ArrayList<>();
    private List<Api_mst_slotitem_equiptype> modifiedSlotitemEquiptype = new ArrayList<>();

    private List<Integer> newEquipExslot = new ArrayList<>();
    private List<Integer> modifiedEquipExslot = new ArrayList<>();

    private List<Api_mst_equip_exslot_ship> newEquipExslotShip = new ArrayList<>();
    private List<Api_mst_equip_exslot_ship> modifiedEquipExslotShip = new ArrayList<>();

    private List<Api_mst_stype> newStype = new ArrayList<>();
    private List<Api_mst_stype> modifiedStype = new ArrayList<>();

    private List<Api_mst_slotitem> newSlotitem = new ArrayList<>();
    private List<Api_mst_slotitem> modifiedSlotitem = new ArrayList<>();

    private List<CombinedFurnitureEntity> newFurniture = new ArrayList<>();
    private List<CombinedFurnitureEntity> modifiedFurniture = new ArrayList<>();

    private List<Api_mst_useitem> newUseitem = new ArrayList<>();
    private List<Api_mst_useitem> modifiedUseitem = new ArrayList<>();

    private List<Api_mst_payitem> newPayitem = new ArrayList<>();
    private List<Api_mst_payitem> modifiedPayitem = new ArrayList<>();

    private Api_mst_item_shop newItemShop;
    private Api_mst_item_shop modifiedItemShop;

    private List<Api_mst_maparea> newMaparea = new ArrayList<>();
    private List<Api_mst_maparea> modifiedMaparea = new ArrayList<>();

    private List<Api_mst_mapinfo> newMapinfo = new ArrayList<>();
    private List<Api_mst_mapinfo> modifiedMapinfo = new ArrayList<>();

    private Map<Integer, Api_mst_mapbgm> newMapbgm = new HashMap<>();
    private Map<Integer, List<Integer>> modifiedMapbgm = new HashMap<>();
    private Map<Integer, Api_mst_mapbgm> allMapbgm = new HashMap<>();

    private List<Api_mst_mission> newMission = new ArrayList<>();
    private List<Api_mst_mission> modifiedMission = new ArrayList<>();

    private Api_mst_const newConst;
    private Api_mst_const modifiedConst;

    private List<Api_mst_bgm> newBgm = new ArrayList<>();
    private List<Api_mst_bgm> modifiedBgm = new ArrayList<>();

    /**
     * @return the newShip
     */
    public List<CombinedShipEntity> getNewShip() {
        return newShip;
    }

    /**
     * @param newShip the newShip to set
     */
    public void setNewShip(List<CombinedShipEntity> newShip) {
        this.newShip = newShip;
    }

    /**
     * @return the modifiedShip
     */
    public List<CombinedShipEntity> getModifiedShip() {
        return modifiedShip;
    }

    /**
     * @param modifiedShip the modifiedShip to set
     */
    public void setModifiedShip(List<CombinedShipEntity> modifiedShip) {
        this.modifiedShip = modifiedShip;
    }

    /**
     * @return the newSlotitemEquiptype
     */
    public List<Api_mst_slotitem_equiptype> getNewSlotitemEquiptype() {
        return newSlotitemEquiptype;
    }

    /**
     * @param newSlotitemEquiptype the newSlotitemEquiptype to set
     */
    public void setNewSlotitemEquiptype(List<Api_mst_slotitem_equiptype> newSlotitemEquiptype) {
        this.newSlotitemEquiptype = newSlotitemEquiptype;
    }

    /**
     * @return the modifiedSlotitemEquiptype
     */
    public List<Api_mst_slotitem_equiptype> getModifiedSlotitemEquiptype() {
        return modifiedSlotitemEquiptype;
    }

    /**
     * @param modifiedSlotitemEquiptype the modifiedSlotitemEquiptype to set
     */
    public void setModifiedSlotitemEquiptype(List<Api_mst_slotitem_equiptype> modifiedSlotitemEquiptype) {
        this.modifiedSlotitemEquiptype = modifiedSlotitemEquiptype;
    }

    /**
     * @return the newEquipExslot
     */
    public List<Integer> getNewEquipExslot() {
        return newEquipExslot;
    }

    /**
     * @param newEquipExslot the newEquipExslot to set
     */
    public void setNewEquipExslot(List<Integer> newEquipExslot) {
        this.newEquipExslot = newEquipExslot;
    }

    /**
     * @return the modifiedEquipExslot
     */
    public List<Integer> getModifiedEquipExslot() {
        return modifiedEquipExslot;
    }

    /**
     * @param modifiedEquipExslot the modifiedEquipExslot to set
     */
    public void setModifiedEquipExslot(List<Integer> modifiedEquipExslot) {
        this.modifiedEquipExslot = modifiedEquipExslot;
    }

    /**
     * @return the newEquipExslotShip
     */
    public List<Api_mst_equip_exslot_ship> getNewEquipExslotShip() {
        return newEquipExslotShip;
    }

    /**
     * @param newEquipExslotShip the newEquipExslotShip to set
     */
    public void setNewEquipExslotShip(List<Api_mst_equip_exslot_ship> newEquipExslotShip) {
        this.newEquipExslotShip = newEquipExslotShip;
    }

    /**
     * @return the modifiedEquipExslotShip
     */
    public List<Api_mst_equip_exslot_ship> getModifiedEquipExslotShip() {
        return modifiedEquipExslotShip;
    }

    /**
     * @param modifiedEquipExslotShip the modifiedEquipExslotShip to set
     */
    public void setModifiedEquipExslotShip(List<Api_mst_equip_exslot_ship> modifiedEquipExslotShip) {
        this.modifiedEquipExslotShip = modifiedEquipExslotShip;
    }

    /**
     * @return the newStype
     */
    public List<Api_mst_stype> getNewStype() {
        return newStype;
    }

    /**
     * @param newStype the newStype to set
     */
    public void setNewStype(List<Api_mst_stype> newStype) {
        this.newStype = newStype;
    }

    /**
     * @return the modifiedStype
     */
    public List<Api_mst_stype> getModifiedStype() {
        return modifiedStype;
    }

    /**
     * @param modifiedStype the modifiedStype to set
     */
    public void setModifiedStype(List<Api_mst_stype> modifiedStype) {
        this.modifiedStype = modifiedStype;
    }

    /**
     * @return the newSlotitem
     */
    public List<Api_mst_slotitem> getNewSlotitem() {
        return newSlotitem;
    }

    /**
     * @param newSlotitem the newSlotitem to set
     */
    public void setNewSlotitem(List<Api_mst_slotitem> newSlotitem) {
        this.newSlotitem = newSlotitem;
    }

    /**
     * @return the modifiedSlotitem
     */
    public List<Api_mst_slotitem> getModifiedSlotitem() {
        return modifiedSlotitem;
    }

    /**
     * @param modifiedSlotitem the modifiedSlotitem to set
     */
    public void setModifiedSlotitem(List<Api_mst_slotitem> modifiedSlotitem) {
        this.modifiedSlotitem = modifiedSlotitem;
    }

    /**
     * @return the newFurniture
     */
    public List<CombinedFurnitureEntity> getNewFurniture() {
        return newFurniture;
    }

    /**
     * @param newFurniture the newFurniture to set
     */
    public void setNewFurniture(List<CombinedFurnitureEntity> newFurniture) {
        this.newFurniture = newFurniture;
    }

    /**
     * @return the modifiedFurniture
     */
    public List<CombinedFurnitureEntity> getModifiedFurniture() {
        return modifiedFurniture;
    }

    /**
     * @param modifiedFurniture the modifiedFurniture to set
     */
    public void setModifiedFurniture(List<CombinedFurnitureEntity> modifiedFurniture) {
        this.modifiedFurniture = modifiedFurniture;
    }

    /**
     * @return the newUseitem
     */
    public List<Api_mst_useitem> getNewUseitem() {
        return newUseitem;
    }

    /**
     * @param newUseitem the newUseitem to set
     */
    public void setNewUseitem(List<Api_mst_useitem> newUseitem) {
        this.newUseitem = newUseitem;
    }

    /**
     * @return the modifiedUseitem
     */
    public List<Api_mst_useitem> getModifiedUseitem() {
        return modifiedUseitem;
    }

    /**
     * @param modifiedUseitem the modifiedUseitem to set
     */
    public void setModifiedUseitem(List<Api_mst_useitem> modifiedUseitem) {
        this.modifiedUseitem = modifiedUseitem;
    }

    /**
     * @return the newPayitem
     */
    public List<Api_mst_payitem> getNewPayitem() {
        return newPayitem;
    }

    /**
     * @param newPayitem the newPayitem to set
     */
    public void setNewPayitem(List<Api_mst_payitem> newPayitem) {
        this.newPayitem = newPayitem;
    }

    /**
     * @return the modifiedPayitem
     */
    public List<Api_mst_payitem> getModifiedPayitem() {
        return modifiedPayitem;
    }

    /**
     * @param modifiedPayitem the modifiedPayitem to set
     */
    public void setModifiedPayitem(List<Api_mst_payitem> modifiedPayitem) {
        this.modifiedPayitem = modifiedPayitem;
    }

    /**
     * @return the newItemShop
     */
    public Api_mst_item_shop getNewItemShop() {
        return newItemShop;
    }

    /**
     * @param newItemShop the newItemShop to set
     */
    public void setNewItemShop(Api_mst_item_shop newItemShop) {
        this.newItemShop = newItemShop;
    }

    /**
     * @return the modifiedItemShop
     */
    public Api_mst_item_shop getModifiedItemShop() {
        return modifiedItemShop;
    }

    /**
     * @param modifiedItemShop the modifiedItemShop to set
     */
    public void setModifiedItemShop(Api_mst_item_shop modifiedItemShop) {
        this.modifiedItemShop = modifiedItemShop;
    }

    /**
     * @return the newMaparea
     */
    public List<Api_mst_maparea> getNewMaparea() {
        return newMaparea;
    }

    /**
     * @param newMaparea the newMaparea to set
     */
    public void setNewMaparea(List<Api_mst_maparea> newMaparea) {
        this.newMaparea = newMaparea;
    }

    /**
     * @return the modifiedMaparea
     */
    public List<Api_mst_maparea> getModifiedMaparea() {
        return modifiedMaparea;
    }

    /**
     * @param modifiedMaparea the modifiedMaparea to set
     */
    public void setModifiedMaparea(List<Api_mst_maparea> modifiedMaparea) {
        this.modifiedMaparea = modifiedMaparea;
    }

    /**
     * @return the newMapinfo
     */
    public List<Api_mst_mapinfo> getNewMapinfo() {
        return newMapinfo;
    }

    /**
     * @param newMapinfo the newMapinfo to set
     */
    public void setNewMapinfo(List<Api_mst_mapinfo> newMapinfo) {
        this.newMapinfo = newMapinfo;
    }

    /**
     * @return the modifiedMapinfo
     */
    public List<Api_mst_mapinfo> getModifiedMapinfo() {
        return modifiedMapinfo;
    }

    /**
     * @param modifiedMapinfo the modifiedMapinfo to set
     */
    public void setModifiedMapinfo(List<Api_mst_mapinfo> modifiedMapinfo) {
        this.modifiedMapinfo = modifiedMapinfo;
    }

    /**
     * @return the newMission
     */
    public List<Api_mst_mission> getNewMission() {
        return newMission;
    }

    /**
     * @param newMission the newMission to set
     */
    public void setNewMission(List<Api_mst_mission> newMission) {
        this.newMission = newMission;
    }

    /**
     * @return the modifiedMission
     */
    public List<Api_mst_mission> getModifiedMission() {
        return modifiedMission;
    }

    /**
     * @param modifiedMission the modifiedMission to set
     */
    public void setModifiedMission(List<Api_mst_mission> modifiedMission) {
        this.modifiedMission = modifiedMission;
    }

    /**
     * @return the newConst
     */
    public Api_mst_const getNewConst() {
        return newConst;
    }

    /**
     * @param newConst the newConst to set
     */
    public void setNewConst(Api_mst_const newConst) {
        this.newConst = newConst;
    }

    /**
     * @return the modifiedConst
     */
    public Api_mst_const getModifiedConst() {
        return modifiedConst;
    }

    /**
     * @param modifiedConst the modifiedConst to set
     */
    public void setModifiedConst(Api_mst_const modifiedConst) {
        this.modifiedConst = modifiedConst;
    }

    /**
     * @return the newBgm
     */
    public List<Api_mst_bgm> getNewBgm() {
        return newBgm;
    }

    /**
     * @param newBgm the newBgm to set
     */
    public void setNewBgm(List<Api_mst_bgm> newBgm) {
        this.newBgm = newBgm;
    }

    /**
     * @return the modifiedBgm
     */
    public List<Api_mst_bgm> getModifiedBgm() {
        return modifiedBgm;
    }

    /**
     * @param modifiedBgm the modifiedBgm to set
     */
    public void setModifiedBgm(List<Api_mst_bgm> modifiedBgm) {
        this.modifiedBgm = modifiedBgm;
    }

    /**
     * @return the modifiedMapbgm
     */
    public Map<Integer, List<Integer>> getModifiedMapbgm() {
        return modifiedMapbgm;
    }

    /**
     * @param modifiedMapbgm the modifiedMapbgm to set
     */
    public void setModifiedMapbgm(Map<Integer, List<Integer>> modifiedMapbgm) {
        this.modifiedMapbgm = modifiedMapbgm;
    }

    /**
     * @return the newMapbgm
     */
    public Map<Integer, Api_mst_mapbgm> getNewMapbgm() {
        return newMapbgm;
    }

    /**
     * @param newMapbgm the newMapbgm to set
     */
    public void setNewMapbgm(Map<Integer, Api_mst_mapbgm> newMapbgm) {
        this.newMapbgm = newMapbgm;
    }

    /**
     * @return the allMapbgm
     */
    public Map<Integer, Api_mst_mapbgm> getAllMapbgm() {
        return allMapbgm;
    }

    /**
     * @param allMapbgm the allMapbgm to set
     */
    public void setAllMapbgm(Map<Integer, Api_mst_mapbgm> allMapbgm) {
        this.allMapbgm = allMapbgm;
    }
    
}
