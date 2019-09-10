/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.httpclient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.util.Timeout;
import kcwiki.x.kcscanner.initializer.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author iTeam_VEP
 */
@Component
public class HttpClientConfig {
    
    @Autowired
    AppConfig appConfig;
    
    public RequestConfig makeProxyConfig(boolean needProxy) {
        RequestConfig config;
        if (needProxy) {
            HttpHost proxy = new HttpHost(appConfig.getProxy_host(), appConfig.getProxy_port());
            config = RequestConfig.custom()
                .setProxy(proxy)
                .setConnectionTimeout(Timeout.ofSeconds(5))
                .setConnectionRequestTimeout(Timeout.ofSeconds(30))
                .build();
        } else {
            config = RequestConfig.custom()
                .setConnectionTimeout(Timeout.ofSeconds(5))
                .setConnectionRequestTimeout(Timeout.ofSeconds(30))
                .build();
        }
        return config;
    }
    
    public static DateFormat GTMDateFormatter() {
        DateFormat sdf  = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf;
    }
    
}
