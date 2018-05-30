/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kcwiki.x.httpclient;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpHead;
import org.apache.hc.client5.http.config.RequestConfig;

/**
 *
 * @author iTeam_VEP
 */
public class DefaultMethod {
    
    public static HttpHead getDefaultHeadMethod(String url, RequestConfig config) {
        HttpHead httpHead = new HttpHead(url);
        httpHead.setConfig(config);
        httpHead.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36");  
        httpHead.setHeader("Accept-Encoding", "gzip, deflate");  
        httpHead.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpHead.setHeader("Cache-Control", "max-age=0");  
        httpHead.setHeader("Pragma", "no-cache"); 
//        httpHead.setHeader("Connection", "keep-alive");  
        httpHead.setHeader("DNT", "1"); 
        
        return httpHead;
    }
    
    public static HttpGet getDefaultGetMethod(String url, RequestConfig config) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36");  
        httpGet.setHeader("Accept-Encoding", "gzip, deflate");  
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.setHeader("Cache-Control", "max-age=0");  
        httpGet.setHeader("Pragma", "no-cache"); 
//        httpGet.setHeader("Connection", "keep-alive");  
        httpGet.setHeader("DNT", "1");  
        
        return httpGet;
    }
    
}
