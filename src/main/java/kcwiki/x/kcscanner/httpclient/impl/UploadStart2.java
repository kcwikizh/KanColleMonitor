/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.httpclient.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import kcwiki.x.kcscanner.initializer.AppConfig;
import kcwiki.x.kcscanner.message.websocket.types.PublishTypes;
import kcwiki.x.kcscanner.message.websocket.types.ModuleType;
import kcwiki.x.kcscanner.types.MessageLevel;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.iharu.type.ResultType;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author iHaru
 */
@Component
@Scope("prototype")
public class UploadStart2 extends BaseHttpClient {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(UploadStart2.class);
    
    @Autowired
    AppConfig appConfig;
    
    public boolean upload(StringBuilder buffer) {
        String startdata = buffer.toString();
        if(startdata.contains("svdata")){
            startdata = startdata.substring(startdata.indexOf("svdata=")+66, startdata.length()-1);
        }
        
        try {
                
                try (CloseableHttpClient _httpclient = HttpClients.custom().build()) {
                    final List<NameValuePair> nvps = new ArrayList<>();
                    HttpPost httpPost = new HttpPost(appConfig.getKcwiki_api_upload_start2_url());
                    httpPost.setHeader("Host", "acc.kcwiki.org");
                    httpPost.setHeader("connection", "keep-alive");
                    httpPost.setHeader("Cache-Control", "max-age=0");
                    httpPost.setHeader("Upgrade-Insecure-Requests", "1");
                    httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36");
                    httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                    httpPost.setHeader("DNT", "1");
                    httpPost.setHeader("Accept-Encoding", "gzip, deflate");
                    httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
                    nvps.add(new BasicNameValuePair("password", appConfig.getKcwiki_api_upload_start2_token()));
                    nvps.add(new BasicNameValuePair("data", startdata));
                    httpPost.setEntity(new UrlEncodedFormEntity(nvps));
                    
                    try (CloseableHttpResponse response = _httpclient.execute(httpPost)) {
                        InputStream in=response.getEntity().getContent();
                        String retVal = readStream(in);
                        ModuleType type = ModuleType.UploadStart2;
                        if(retVal.contains("data invalid")){
                            messagePublisher.publish("上传的start2数据不合法！",type, ResultType.ERROR);
                        }
                        if(retVal.contains("duplicate start2 data")){
                            messagePublisher.publish("上传的start2数据已经存在。",type);
                        }
                        if(retVal.contains("success")){
                            messagePublisher.publish("start2上传成功！",type);
                        }
                    } catch (IOException ex) {
                        LOG.error(ExceptionUtils.getStackTrace(ex));
                        return false;
                    }
                }
                
                return true;
        } catch (IOException ex) {
                LOG.error("");
                return false;
        }
    }
}
