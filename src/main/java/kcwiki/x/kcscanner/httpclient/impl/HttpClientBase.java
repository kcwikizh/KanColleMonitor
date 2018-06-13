/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.httpclient.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.Cookie;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.HttpHost;
import kcwiki.x.kcscanner.cache.inmem.AppDataCache;
import kcwiki.x.kcscanner.message.websocket.MessagePublisher;
import static kcwiki.x.kcscanner.tools.ConstantValue.LINESEPARATOR;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author x5171
 */
public class HttpClientBase {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(HttpClientBase.class);
    
    @Autowired
    protected MessagePublisher messagePublisher;
    
    //设置连接管理器
    protected PoolingHttpClientConnectionManager connManager ;
    protected RequestConfig config;
    protected BasicCookieStore cookieStore = new BasicCookieStore();
    protected List<Cookie> cookies = cookieStore.getCookies();
    protected CloseableHttpClient httpclient ;
    
    public void setConfig(boolean useProxy){
        if(useProxy) {
//            HttpHost proxy = new HttpHost(AppDataCache.appConfigs.getProxy_host(), AppDataCache.appConfigs.getProxy_port());
            HttpHost proxy = new HttpHost("127.0.0.1", 8888);
            config = RequestConfig.custom().setProxy(proxy).setRedirectsEnabled(true).build();
        } else {
            config = RequestConfig.custom().setRedirectsEnabled(false).build();
        }
    }
    
    protected String readStream(InputStream in){
        StringBuilder data = new StringBuilder();
        try {
            InputStreamReader isr = new InputStreamReader(in, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                data.append(line).append(LINESEPARATOR);
            }
            data = data.delete(data.length()-1, data.length());
        } catch (IOException ex) {
            LOG.error("");
        }
        return data.toString();
    }
    
}
