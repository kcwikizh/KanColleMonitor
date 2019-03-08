/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.start2.processor;

import java.util.List;
import kcwiki.x.kcscanner.core.entity.CombinedFurnitureEntity;
import kcwiki.x.kcscanner.core.entity.CombinedShipEntity;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_furniture;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_furnituregraph;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_ship;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_shipgraph;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Api_mst_shipupgrade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

/**
 *
 * @author iHaru
 */
public class Start2Comparetor {
    private final static Logger LOG = LoggerFactory.getLogger(Start2Comparetor.class);
    
    public static CombinedShipEntity shipCombination(Api_mst_ship api_mst_ship, Api_mst_shipgraph api_mst_shipgraph, List<Api_mst_shipupgrade> api_mst_shipupgrade) {
        CombinedShipEntity shipEntity = new CombinedShipEntity();
        try{
            if(api_mst_ship != null){
                BeanUtils.copyProperties(api_mst_ship, shipEntity);
            }
            if(api_mst_shipgraph != null){
                BeanUtils.copyProperties(api_mst_shipgraph, shipEntity);
            }
        } catch(BeansException ex) {
            LOG.error("结合对象时发生错误。ID为：{}", api_mst_ship==null? null:api_mst_ship.getApi_id());
            return null;
        }
        if(api_mst_shipupgrade != null){
            shipEntity.setApi_mst_shipupgrade(api_mst_shipupgrade);
        }
        return shipEntity;
    }
    
    public static CombinedFurnitureEntity furnitureCombination(Api_mst_furniture api_mst_furniture, Api_mst_furnituregraph api_mst_furnituregraph) {
        CombinedFurnitureEntity combinedFurnitureEntity = new CombinedFurnitureEntity();
        try{
            if(api_mst_furniture != null){
                BeanUtils.copyProperties(api_mst_furniture, combinedFurnitureEntity);
            }
            if(api_mst_furnituregraph != null){
                BeanUtils.copyProperties(api_mst_furnituregraph, combinedFurnitureEntity);
            }
        } catch(BeansException ex) {
            LOG.error("结合对象时发生错误。ID为：{}", api_mst_furniture==null? null:api_mst_furniture.getApi_id());
            return null;
        }
        return combinedFurnitureEntity;
    }
    
}
