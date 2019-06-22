/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.entity.lua.slotitem;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

/**
 *
 * @author iHaru
 */
public class Cond {
    @JsonProperty(value="开发")
    int cond;
    
    @JsonProperty(value="改修")
    int stats;
    
    @JsonProperty(value="更新")
    int scrap;
    
    @JsonProperty(value="熟练")
    int refittable;
    
}
