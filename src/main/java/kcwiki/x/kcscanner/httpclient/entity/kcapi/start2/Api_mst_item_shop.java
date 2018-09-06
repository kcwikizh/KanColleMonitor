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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
public class Api_mst_item_shop
{
    private List<Integer> api_cabinet_1;

    private List<Integer> api_cabinet_2;

    @Override
    public boolean equals(Object obj) {
        if(obj == null || !(obj instanceof Api_mst_item_shop)){
            return false;
        }
        return EqualsBuilder.reflectionEquals(this, obj);
    }
    
    private JsonNode convertToNode(Object obj, ObjectMapper mapper){
        Map<String, Object> map = mapper.convertValue(obj, Map.class);
        JsonNode node = mapper.valueToTree(map);
        return node;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + Objects.hashCode(this.api_cabinet_1);
        hash = 17 * hash + Objects.hashCode(this.api_cabinet_2);
        return hash;
    }
    
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
