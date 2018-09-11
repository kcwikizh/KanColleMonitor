/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kcwiki.httpclient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import org.kcwiki.initializer.MainServer;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.util.Timeout;

/**
 *
 * @author iTeam_VEP
 */
public class Config {
    
    
    public static RequestConfig makeProxyConfig(boolean needProxy) {
        HttpHost proxy = new HttpHost(MainServer.getProxyhost(), MainServer.getProxyport());
        RequestConfig config;
        if (needProxy) {
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
