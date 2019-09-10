/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.start2.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import kcwiki.x.kcscanner.core.entity.CombinedFurnitureEntity;
import kcwiki.x.kcscanner.core.entity.CombinedShipEntity;
import kcwiki.x.kcscanner.core.entity.JsonPatchEntity;
import kcwiki.x.kcscanner.core.entity.JsonPatchListEntity;
import kcwiki.x.kcscanner.core.entity.IdMappedStart2Entity;
import kcwiki.x.kcscanner.core.entity.CombinedStart2Entity;
import kcwiki.x.kcscanner.core.entity.Start2PatchEntity;
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
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Start2;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author iHaru
 * https://blog.csdn.net/qq_27093465/article/details/62453581
 * https://blog.csdn.net/pinebud55/article/details/78193577
 */
@Component
@Scope("prototype")
public class Start2Analyzer {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Start2Analyzer.class);
    
    private final JsonPatchListEntity jsonPatchListEntity = new JsonPatchListEntity();
    private final Start2PatchEntity start2PatchEntity = new Start2PatchEntity();
    private final CombinedStart2Entity primaryStart2Entity = new CombinedStart2Entity();
    private final CombinedStart2Entity secondaryStart2Entity = new CombinedStart2Entity();
    
    /**
     * @param jsonPatchEntityList 暂时没用
     * @param secondaryStart2 旧的，用于作为对比的Start2数据
     * @param primaryStart2 最新的Start2数据
     * @return 
    */
    public Start2PatchEntity getDiffStart2(List<JsonPatchEntity> jsonPatchEntityList, Start2 secondaryStart2, Start2 primaryStart2){
        if(secondaryStart2 == null)
            secondaryStart2 = new Start2();
        if(primaryStart2 == null)
            return null;
        IdMappedStart2Entity _primaryStart2Entity = new IdMappedStart2Entity();
        IdMappedStart2Entity _secondaryStart2Entity = new IdMappedStart2Entity();
        Start2PreprocessingStage1(primaryStart2, _primaryStart2Entity);
        Start2PreprocessingStage1(secondaryStart2, _secondaryStart2Entity);
        Start2PreprocessingStage2(primaryStart2, _primaryStart2Entity, primaryStart2Entity);
        Start2PreprocessingStage2(secondaryStart2, _secondaryStart2Entity, secondaryStart2Entity);     
        diffData();
        return getStart2PatchEntity();
    }
    
    private void Start2PreprocessingStage1(Start2 start2, IdMappedStart2Entity mappedStart2Entity){
        if(start2.getApi_mst_ship() == null) {
            return;
        }
        
        mappedStart2Entity.setApi_mst_ship(
                start2.getApi_mst_ship().stream().collect(Collectors.toMap(Api_mst_ship::getApi_id, Function.identity()))
        );
        mappedStart2Entity.setApi_mst_shipgraph(
                start2.getApi_mst_shipgraph().stream().collect(Collectors.toMap(Api_mst_shipgraph::getApi_id, Function.identity()))
        );
        mappedStart2Entity.setApi_mst_shipupgrade(
                new HashMap<Integer, List<Api_mst_shipupgrade>>(){
                    {
                        start2.getApi_mst_shipupgrade().forEach(o -> {
                            List<Api_mst_shipupgrade> _list = get(o.getApi_id());
                            if(_list == null){
                                _list = new ArrayList<>();
                            }
                            _list.add(o);
                            put(o.getApi_id(), _list);
                        });
                    }
                }
        );
        mappedStart2Entity.setApi_mst_furniture(
                start2.getApi_mst_furniture().stream().collect(Collectors.toMap(Api_mst_furniture::getApi_id, Function.identity()))
        );
        mappedStart2Entity.setApi_mst_furnituregraph(
                start2.getApi_mst_furnituregraph().stream().collect(Collectors.toMap(Api_mst_furnituregraph::getApi_id, Function.identity()))
        );
    }
    
    private void Start2PreprocessingStage2(Start2 start2, IdMappedStart2Entity idMappedStart2Entity, CombinedStart2Entity combinedStart2Entity){
        if(start2.getApi_mst_ship() == null) {
            return;
        }
        
        combinedStart2Entity.setApi_mst_ship(
            new HashMap<String, List<CombinedShipEntity>>(){
                {
                    idMappedStart2Entity.getApi_mst_ship().forEach((k, v) -> {
                        List<CombinedShipEntity> _list = get(v.getApi_name());
                        if(_list == null){
                            _list = new ArrayList<>();
                        }
                        _list.add(
                                Start2Comparetor.shipCombination(
                                        v, 
                                        idMappedStart2Entity.getApi_mst_shipgraph().get(k), 
                                        idMappedStart2Entity.getApi_mst_shipupgrade().get(k)
                                )
                        );
                        put(
                                v.getApi_name(), 
                                _list
                        );
                    });
                }
            }
        );
        combinedStart2Entity.setApi_mst_slotitem_equiptype(
                start2.getApi_mst_slotitem_equiptype().stream().collect(Collectors.toMap(Api_mst_slotitem_equiptype::getApi_name, Function.identity()))
        );
        combinedStart2Entity.setApi_mst_equip_exslot_ship(
                start2.getApi_mst_equip_exslot_ship().stream().collect(Collectors.toMap(Api_mst_equip_exslot_ship::getApi_slotitem_id, Function.identity()))
        );
        combinedStart2Entity.setApi_mst_stype(
            new HashMap<String, List<Api_mst_stype>>(){
                {
                    start2.getApi_mst_stype().forEach(obj -> {
                        List<Api_mst_stype> _list = get(obj.getApi_name());
                        if(_list == null){
                            _list = new ArrayList<>();
                        }
                        _list.add(obj);
                        put(
                                obj.getApi_name(), 
                                _list
                        );
                    });
                }
            }
        );
        combinedStart2Entity.setApi_mst_slotitem(
            new HashMap<String, List<Api_mst_slotitem>>(){
                {
                    start2.getApi_mst_slotitem().forEach(obj -> {
                        List<Api_mst_slotitem> _list = get(obj.getApi_name());
                        if(_list == null){
                            _list = new ArrayList<>();
                        }
                        _list.add(obj);
                        put(
                                obj.getApi_name(), 
                                _list
                        );
                    });
                }
            }    
        );
        combinedStart2Entity.setApi_mst_furniture(
            new HashMap<String, CombinedFurnitureEntity>(){
                {
                    idMappedStart2Entity.getApi_mst_furniture().forEach((k, v) -> {
                        put(
                                v.getApi_title(), 
                                Start2Comparetor.furnitureCombination(
                                        v, 
                                        idMappedStart2Entity.getApi_mst_furnituregraph().get(k)
                                )
                        );
                    });
                }
            }
        );
        combinedStart2Entity.setApi_mst_useitem(
            new HashMap<String, List<Api_mst_useitem>>(){
                {
                    start2.getApi_mst_useitem().forEach(obj -> {
                        List<Api_mst_useitem> _list = get(obj.getApi_name());
                        if(_list == null){
                            _list = new ArrayList<>();
                        }
                        _list.add(obj);
                        put(
                                obj.getApi_name(), 
                                _list
                        );
                    });
                }
            } 
        );
        combinedStart2Entity.setApi_mst_payitem(
                start2.getApi_mst_payitem().stream().collect(Collectors.toMap(Api_mst_payitem::getApi_name, Function.identity()))
        );
        combinedStart2Entity.setApi_mst_item_shop(start2.getApi_mst_item_shop());
        combinedStart2Entity.setApi_mst_maparea(
                start2.getApi_mst_maparea().stream().collect(Collectors.toMap(Api_mst_maparea::getApi_id, Function.identity()))
        );
        combinedStart2Entity.setApi_mst_mapinfo(
                start2.getApi_mst_mapinfo().stream().collect(Collectors.toMap(Api_mst_mapinfo::getApi_id, Function.identity()))
        );
        combinedStart2Entity.setApi_mst_mapbgm(
                start2.getApi_mst_mapbgm().stream().collect(Collectors.toMap(Api_mst_mapbgm::getApi_id, Function.identity()))
        );
        combinedStart2Entity.setApi_mst_mission(
                start2.getApi_mst_mission().stream().collect(Collectors.toMap(Api_mst_mission::getApi_id, Function.identity()))
        );
        combinedStart2Entity.setApi_mst_const(start2.getApi_mst_const());
        combinedStart2Entity.setApi_mst_bgm(
            new HashMap<String, List<Api_mst_bgm>>(){
                {
                    start2.getApi_mst_bgm().forEach(obj -> {
                        List<Api_mst_bgm> _list = get(obj.getApi_name());
                        if(_list == null){
                            _list = new ArrayList<>();
                        }
                        _list.add(obj);
                        put(
                                obj.getApi_name(), 
                                _list
                        );
                    });
                }
            } 
        );
        
    }
    
    private void diffData() {
        primaryStart2Entity.getApi_mst_ship().forEach((k, v) -> {
//            if(k.equals("飛行場姫"))
//                System.out.print("");
            if(!secondaryStart2Entity.getApi_mst_ship().containsKey(k)){
                getStart2PatchEntity().getNewShip().addAll(v);
            } else {
                v.forEach(obj -> {
                    int id = -1;
                    for(CombinedShipEntity _obj:secondaryStart2Entity.getApi_mst_ship().get(k)){
                        id = _obj.getApi_id();
                        if(obj.equals(_obj)){
                            return;
                        }
                    }
                    if(obj.getApi_id().equals(id))
                        getStart2PatchEntity().getModifiedShip().add(obj);
                    else
                        getStart2PatchEntity().getNewShip().add(obj);
//                    secondaryStart2Entity.getApi_mst_ship().get(k).forEach(_obj -> {
//                        if(obj.getApi_id().equals(_obj.getApi_id()) && !obj.equals(_obj)){
//                            getStart2PatchEntity().getModifiedShip().add(obj);
//                        }
//                    });
                });
            }
        });
        primaryStart2Entity.getApi_mst_slotitem().forEach((k, v) -> {
            if(!secondaryStart2Entity.getApi_mst_slotitem().containsKey(k)){
                getStart2PatchEntity().getNewSlotitem().addAll(v);
            } else {
                getStart2PatchEntity().getModifiedSlotitem().addAll(
                        v.stream().filter(
                                item -> !secondaryStart2Entity.getApi_mst_slotitem()
                                        .get(k).contains(item)
                        )
                        .collect(Collectors.toList()).stream()
                        .distinct().collect(Collectors.toList())
                );
            }
        });
        primaryStart2Entity.getApi_mst_equip_exslot_ship().forEach((k, v) -> {
            if(!secondaryStart2Entity.getApi_mst_equip_exslot_ship().containsKey(k)){
                getStart2PatchEntity().getNewEquipExslotShip().add(v);
            } else {
                if(!secondaryStart2Entity.getApi_mst_equip_exslot_ship().get(k).equals(v)) {
                    getStart2PatchEntity().getModifiedEquipExslotShip().add(v);
                }
            }
        });
        primaryStart2Entity.getApi_mst_stype().forEach((k, v) -> {
            if(!secondaryStart2Entity.getApi_mst_stype().containsKey(k)){
                getStart2PatchEntity().getNewStype().addAll(v);
            } else {
                getStart2PatchEntity().getModifiedStype().addAll(
                        v.stream().filter(
                                item -> !secondaryStart2Entity.getApi_mst_stype()
                                        .get(k).contains(item)
                        )
                        .collect(Collectors.toList()).stream()
                        .distinct().collect(Collectors.toList())
                );
            }
        });
        primaryStart2Entity.getApi_mst_useitem().forEach((k, v) -> {
            if(!secondaryStart2Entity.getApi_mst_useitem().containsKey(k)){
                getStart2PatchEntity().getNewUseitem().addAll(v);
            } else {
                getStart2PatchEntity().getModifiedUseitem().addAll(
                        v.stream().filter(
                                item -> !secondaryStart2Entity.getApi_mst_useitem()
                                        .get(k).contains(item)
                        )
                        .collect(Collectors.toList()).stream()
                        .distinct().collect(Collectors.toList())
                );
            }
        });
        primaryStart2Entity.getApi_mst_payitem().forEach((k, v) -> {
            if(!secondaryStart2Entity.getApi_mst_payitem().containsKey(k)){
                getStart2PatchEntity().getNewPayitem().add(v);
            } else {
                if(!secondaryStart2Entity.getApi_mst_payitem().get(k).equals(v)) {
                    getStart2PatchEntity().getModifiedPayitem().add(v);
                }
            }
        });
        if(!primaryStart2Entity.getApi_mst_item_shop().equals(secondaryStart2Entity.getApi_mst_item_shop())){
            getStart2PatchEntity().setModifiedItemShop(primaryStart2Entity.getApi_mst_item_shop());
        }
        primaryStart2Entity.getApi_mst_maparea().forEach((k, v) -> {
            if(!secondaryStart2Entity.getApi_mst_maparea().containsKey(k)){
                getStart2PatchEntity().getNewMaparea().add(v);
            } else {
                if(!secondaryStart2Entity.getApi_mst_maparea().get(k).equals(v)) {
                    getStart2PatchEntity().getModifiedMaparea().add(v);
                }
            }
        });
        primaryStart2Entity.getApi_mst_mapinfo().forEach((k, v) -> {
            if(!secondaryStart2Entity.getApi_mst_mapinfo().containsKey(k)){
                getStart2PatchEntity().getNewMapinfo().add(v);
            } else {
                if(!secondaryStart2Entity.getApi_mst_mapinfo().get(k).equals(v)) {
                    getStart2PatchEntity().getModifiedMapinfo().add(v);
                }
            }
        });
        
        Map<Integer, Api_mst_mapbgm> _mapbgm = new HashMap<>();
        primaryStart2Entity.getApi_mst_mapbgm().forEach((k, v) -> {
            _mapbgm.put(v.getApi_id(), v);
            if(!secondaryStart2Entity.getApi_mst_mapbgm().containsKey(k)){
                getStart2PatchEntity().getNewMapbgm().put(v.getApi_id(), v);
                
            } else {
                if(!secondaryStart2Entity.getApi_mst_mapbgm().get(k).equals(v)) {
                    List<Integer> list = new ArrayList<>();
                    IntStream.rangeClosed(0, 1).forEach(index -> {
                        if(!secondaryStart2Entity.getApi_mst_mapbgm().get(k)
                                .getApi_map_bgm().get(index)
                                .equals(
                                        v.getApi_map_bgm().get(index)
                                )
                            )
                        {
                            list.add(v.getApi_map_bgm().get(index));
                        }else{
                            list.add(-1);
                        }
                    });
                    IntStream.rangeClosed(0, 1).forEach(index -> {
                        if(!secondaryStart2Entity.getApi_mst_mapbgm().get(k)
                                .getApi_boss_bgm().get(index)
                                .equals(
                                        v.getApi_boss_bgm().get(index)
                                )
                            )
                        {
                            list.add(v.getApi_boss_bgm().get(index));
                        }else{
                            list.add(-1);
                        }
                    });
                    if(!secondaryStart2Entity.getApi_mst_mapbgm().get(k)
                            .getApi_moving_bgm()
                            .equals(
                                    v.getApi_moving_bgm()
                            )
                        )
                    {
                        list.add(v.getApi_moving_bgm());
                    }else{
                        list.add(-1);
                    }
                    getStart2PatchEntity().getModifiedMapbgm().put(v.getApi_id(), list);
                }
            }
        });
        getStart2PatchEntity().getAllMapbgm().putAll(_mapbgm);
        _mapbgm.clear();
        
        primaryStart2Entity.getApi_mst_bgm().forEach((k, v) -> {
            if(!secondaryStart2Entity.getApi_mst_bgm().containsKey(k)){
                getStart2PatchEntity().getNewBgm().addAll(v);
            } else {
                getStart2PatchEntity().getModifiedBgm().addAll(
                        v.stream().filter(
                                item -> !secondaryStart2Entity.getApi_mst_bgm()
                                        .get(k).contains(item)
                        )
                        .collect(Collectors.toList())
                );
            }
        });
        primaryStart2Entity.getApi_mst_slotitem_equiptype().forEach((k, v) -> {
            if(!secondaryStart2Entity.getApi_mst_slotitem_equiptype().containsKey(k)){
                getStart2PatchEntity().getNewSlotitemEquiptype().add(v);
            } else {
                if(!secondaryStart2Entity.getApi_mst_slotitem_equiptype().get(k).equals(v)) {
                    getStart2PatchEntity().getModifiedSlotitemEquiptype().add(v);
                }
            }
        });
        if(!primaryStart2Entity.getApi_mst_const().equals(secondaryStart2Entity.getApi_mst_const())){
            getStart2PatchEntity().setModifiedConst(primaryStart2Entity.getApi_mst_const());
        }
        primaryStart2Entity.getApi_mst_furniture().forEach((k, v) -> {
            if(!secondaryStart2Entity.getApi_mst_furniture().containsKey(k)){
                getStart2PatchEntity().getNewFurniture().add(v);
            } else {
                if(!secondaryStart2Entity.getApi_mst_furniture().get(k).equals(v)) {
                    getStart2PatchEntity().getModifiedFurniture().add(v);
                }
            }
        });
    }
    
    private void getDiffStart2(List<JsonPatchEntity> jsonPatchEntityList) {
        jsonPatchEntityList.forEach(obj -> {
            String[] path = obj.getPath().split("/");
            switch(path[1]){
                case "api_mst_ship":
                    jsonPatchListEntity.getShipList().add(obj);
                    break;
                case "api_mst_shipgraph":
                    jsonPatchListEntity.getShipgraphList().add(obj);
                    break;
                case "api_mst_slotitem_equiptype":
                    jsonPatchListEntity.getSlotitemEquiptypeList().add(obj);
                    break;
                case "api_mst_equip_exslot":
                    jsonPatchListEntity.getEquipExslotList().add(obj);
                    break;
                case "api_mst_equip_exslot_ship":
                    jsonPatchListEntity.getEquipExslotShipList().add(obj);
                    break;
                case "api_mst_stype":
                    jsonPatchListEntity.getStypeList().add(obj);
                    break;
                case "api_mst_slotitem":
                    jsonPatchListEntity.getSlotitemList().add(obj);
                    break;
                case "api_mst_furniture":
                    jsonPatchListEntity.getFurnitureList().add(obj);
                    break;
                case "api_mst_furnituregraph":
                    jsonPatchListEntity.getFurnituregraphList().add(obj);
                    break;
                case "api_mst_useitem":
                    jsonPatchListEntity.getUseitemList().add(obj);
                    break;
                case "api_mst_payitem":
                    jsonPatchListEntity.getPayitemList().add(obj);
                    break;
                case "api_mst_item_shop":
                    jsonPatchListEntity.getItemShopList().add(obj);
                    break;
                case "api_mst_maparea":
                    jsonPatchListEntity.getMapareaList().add(obj);
                    break;
                case "api_mst_mapinfo":
                    jsonPatchListEntity.getMapinfoList().add(obj);
                    break;
                case "api_mst_mapbgm":
                    jsonPatchListEntity.getMapbgmList().add(obj);
                    break;
                case "api_mst_mission":
                    jsonPatchListEntity.getMissionList().add(obj);
                    break;
                case "api_mst_const":
                    jsonPatchListEntity.getConstList().add(obj);
                    break;
                case "api_mst_shipupgrade":
                    jsonPatchListEntity.getShipupgradeList().add(obj);
                    break;
                case "api_mst_bgm":
                    jsonPatchListEntity.getBgmList().add(obj);
                    break;
                default:
                    LOG.warn("分析Start2Patch数据时，检测到无法识别的JSON属性分支：{}", path[1]);
                   break; 
            }
        });
    }
    
    private void diffShip(){
        jsonPatchListEntity.getShipList().forEach(obj -> {
            String op = obj.getOp();
            String[] path = obj.getPath().split("/");
            switch(op){
                case "add":
                    
                    break;
                case "remove":
                    
                    break;
                default:
                    
                    break;
            }
        });
    }

    /**
     * @return the start2PatchEntity
     */
    public Start2PatchEntity getStart2PatchEntity() {
        return start2PatchEntity;
    }
    
}
