/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.tools;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import static kcwiki.x.kcscanner.tools.ConstantValue.LINESEPARATOR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author x5171
 */
public class JSONUtils {
    static final Logger LOG = LoggerFactory.getLogger(JSONUtils.class);
    
    public static <T> String object2json(T obj){
        ObjectMapper objectMapper = new ObjectMapper();   
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        String json = null;
        try { 
            json = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            LOG.error("尝试对数据转换为json字符串时发生错误。{}", LINESEPARATOR, ex);
        }
        return json;
    }
    
}
