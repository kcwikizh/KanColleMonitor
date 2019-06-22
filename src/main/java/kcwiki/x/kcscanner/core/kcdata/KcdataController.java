/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.kcdata;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import kcwiki.x.kcscanner.core.kcdata.entity.ship.KcdataShip;
import kcwiki.x.kcscanner.httpclient.HttpClientConfig;
import kcwiki.x.kcscanner.httpclient.HttpUtils;
import kcwiki.x.kcscanner.initializer.AppConfig;
import org.apache.hc.client5.http.config.RequestConfig;
import org.iharu.util.JsonUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author iHaru
 */
@Component
public class KcdataController {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(KcdataController.class);
    
    @Autowired
    AppConfig appConfig;  
    @Autowired
    HttpClientConfig httpClientConfig;
    private RequestConfig requestConfig;
    
    private List<KcdataShip> shipData = null;
    
    @PostConstruct
    public void initMethod() {
        if(appConfig.isAllow_use_proxy()){
            requestConfig = httpClientConfig.makeProxyConfig(true);
        } else {
            requestConfig = httpClientConfig.makeProxyConfig(false);
        }
    }
    
    public Map<Integer, String> getShipWikiID() {
        if(shipData == null) {
            String _json = HttpUtils.getHttpBody("http://kcwikizh.github.io/kcdata/ship/all.json", requestConfig);
            if(_json == null) {
                LOG.error("尝试下载kcdada-ship数据失败。");
                return null;
            }
            try{
                shipData = JsonUtils.json2object(_json, new TypeReference<List<KcdataShip>>(){});
            } catch (IOException ex) {
                LOG.error("尝试解析kcdada-ship数据失败。");
                return null;
            }
        }
        Map<Integer, String> data = new HashMap();
        shipData.forEach(item -> {
            data.put(item.getId(), item.getWiki_id());
        });
        return data;
    }
}
