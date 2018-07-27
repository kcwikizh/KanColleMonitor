/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.start2.processor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import java.io.IOException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Start2;
import static kcwiki.x.kcscanner.tools.ConstantValue.LINESEPARATOR;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author x5171
 */
@Component
public class Start2Utils {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Start2Utils.class);
    
    public JsonNode getPatch(String scrStr, String tarStr) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode source = mapper.valueToTree(start2pojo(scrStr));
        JsonNode target = mapper.valueToTree(start2pojo(tarStr));
        JsonNode patch = JsonDiff.asJson(source, target);
        if(patch.size() != 0){
            return patch;
        }
        return null;
    }
    
    public Start2 start2pojo(String rawStart2) {
        ObjectMapper objectMapper = new ObjectMapper();
        Start2 start2 = null;
        try {
            start2 = objectMapper.readValue(rawStart2,
                new TypeReference<Start2>(){});
        } catch (IOException ex) {
            LOG.error("获取Start2原始数据转换为POJO时发生错误，数据格式可能有更改。错误代码为： {}{}", 
                    LINESEPARATOR,
                    ExceptionUtils.getStackTrace(ex));
        }
        return start2;
    }
    
    public String start2str(Start2 start2) {
        ObjectMapper objectMapper = new ObjectMapper();
        String start2str = null;
        try {
            start2str = objectMapper.writeValueAsString(start2);
        } catch (IOException ex) {
            LOG.error("Start2对象转换为JSON时发生错误。错误代码为： {}{}", 
                    LINESEPARATOR,
                    ExceptionUtils.getStackTrace(ex));
        }
        return start2str;
    }
    
    public String object2str(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(object);
        } catch (IOException ex) {
            LOG.error("POJO对象转换为JSON时发生错误。错误代码为： {}{}", 
                    LINESEPARATOR,
                    ExceptionUtils.getStackTrace(ex));
        }
        return json;
    }
    
    public Start2 jsonnode2start2(JsonNode start2Node) {
        ObjectMapper objectMapper = new ObjectMapper();
        Start2 start2 = null;
        try {
            start2 = objectMapper.treeToValue(start2Node, Start2.class);
        } catch (IOException ex) {
            LOG.error("start2Node数据转换为POJO时发生错误。错误代码为： {}{}", 
                    LINESEPARATOR,
                    ExceptionUtils.getStackTrace(ex));
        }
        return start2;
    }
}
