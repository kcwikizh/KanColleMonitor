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
import kcwiki.x.kcscanner.tools.JsonUtils;
import org.slf4j.LoggerFactory;

/**
 *
 * @author x5171
 */
public class Start2Utils extends JsonUtils {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Start2Utils.class);
    
    /**
     * 
     * @param scrStr    旧Start2文件
     * @param tarStr    最新的Start2文件
     * @return  Patch数据
     */
    public static JsonNode getPatch(String scrStr, String tarStr) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode source = mapper.valueToTree(start2pojo(scrStr));
        JsonNode target = mapper.valueToTree(start2pojo(tarStr));
        JsonNode patch = JsonDiff.asJson(source, target);
        if(patch.size() > 0){
            return patch;
        }
        return null;
    }
    
    public static Start2 start2pojo(String rawStart2) {
        try {
            return json2object(rawStart2,
                new TypeReference<Start2>(){}, null);
        } catch (IOException ex) {
            LOG.error("获取Start2原始数据转换为POJO时发生错误，数据格式可能有更改。错误代码为： {}{}", 
                    LINESEPARATOR,
                    ExceptionUtils.getStackTrace(ex));
        }
        return null;
    }
    
    public static String start2str(Start2 start2) {
        return object2json(start2, null);
    }
    
    public static Start2 jsonnode2start2(JsonNode start2Node) {
        try {
            return jsonnode2object(start2Node, Start2.class, null);
        } catch (IOException ex) {
            LOG.error("start2Node数据转换为POJO时发生错误。错误代码为： {}{}", 
                    LINESEPARATOR,
                    ExceptionUtils.getStackTrace(ex));
        }
        return null;
    }
}
