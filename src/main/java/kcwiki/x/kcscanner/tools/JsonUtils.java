/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.tools;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import java.io.IOException;
import java.util.logging.Level;
import kcwiki.x.kcscanner.core.files.scanner.FileScanner;
import static kcwiki.x.kcscanner.tools.ConstantValue.LINESEPARATOR;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iHaru
 */
public class JsonUtils<T> {
    static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);
    
    public static <T> String object2json(T obj, ObjectMapper objectMapper){
        if(objectMapper == null)
            objectMapper = new ObjectMapper();
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            LOG.error("对象{}尝试转换为JSON时发生错误。错误代码为： {}{}", 
                    obj,
                    LINESEPARATOR,
                    ExceptionUtils.getStackTrace(ex));
        }
        return null;
    }
    
    public static <T> T json2object(String json, TypeReference typeReference, ObjectMapper objectMapper) throws IOException {
        if(objectMapper == null)
            objectMapper = new ObjectMapper();
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper.readValue(json, typeReference);
    }
    
    public static <T> T jsonnode2object(JsonNode start2Node, Class<T> clz, ObjectMapper objectMapper) throws JsonProcessingException {
        if(objectMapper == null)
            objectMapper = new ObjectMapper();
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper.treeToValue(start2Node, clz);
    }
    
    public static JsonNode json2jsonnode(String json, ObjectMapper objectMapper) throws IOException {
        if(objectMapper == null)
            objectMapper = new ObjectMapper();
        return objectMapper.readTree(json);
    }
    
    public static JsonNode diffObject(Object source, Object target, ObjectMapper objectMapper) {
        if(objectMapper == null)
            objectMapper = new ObjectMapper();
        JsonNode targetNode = objectMapper.valueToTree(target);
        JsonNode sourceNode = objectMapper.valueToTree(source);
        return JsonDiff.asJson(sourceNode, targetNode);
    }
    
}
