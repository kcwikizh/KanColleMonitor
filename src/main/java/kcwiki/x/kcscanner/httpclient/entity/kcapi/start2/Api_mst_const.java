/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.httpclient.entity.kcapi.start2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import kcwiki.x.kcscanner.core.entity.CombinedShipEntity;

/**
 *
 * @author iHaru
 */
public class Api_mst_const
{
    private Api_boko_max_ships api_boko_max_ships;

    private Api_parallel_quest_max api_parallel_quest_max;

    private Api_dpflag_quest api_dpflag_quest;
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Api_mst_const)){
            return false;
        }
        ObjectMapper mapper = new ObjectMapper(); 
        JsonNode target = convertToNode(this, mapper);
        JsonNode source = convertToNode(obj, mapper);
        JsonNode patch = JsonDiff.asJson(source, target);
        return patch.size() == 0;
    }
    
    private JsonNode convertToNode(Object obj, ObjectMapper mapper){
        Map<String, Object> map = mapper.convertValue(obj, Map.class);
        JsonNode node = mapper.valueToTree(map);
        return node;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.api_boko_max_ships);
        hash = 13 * hash + Objects.hashCode(this.api_parallel_quest_max);
        hash = 13 * hash + Objects.hashCode(this.api_dpflag_quest);
        return hash;
    }

    public void setApi_boko_max_ships(Api_boko_max_ships api_boko_max_ships){
        this.api_boko_max_ships = api_boko_max_ships;
    }
    public Api_boko_max_ships getApi_boko_max_ships(){
        return this.api_boko_max_ships;
    }
    public void setApi_parallel_quest_max(Api_parallel_quest_max api_parallel_quest_max){
        this.api_parallel_quest_max = api_parallel_quest_max;
    }
    public Api_parallel_quest_max getApi_parallel_quest_max(){
        return this.api_parallel_quest_max;
    }
    public void setApi_dpflag_quest(Api_dpflag_quest api_dpflag_quest){
        this.api_dpflag_quest = api_dpflag_quest;
    }
    public Api_dpflag_quest getApi_dpflag_quest(){
        return this.api_dpflag_quest;
    }
}
