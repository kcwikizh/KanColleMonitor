/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.web.security.controller.impl;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kcwiki.x.kcscanner.cache.inmem.AppDataCache;
import kcwiki.x.kcscanner.core.files.FileController;
import kcwiki.x.kcscanner.core.start2.Start2Controller;
import kcwiki.x.kcscanner.core.start2.processor.Start2Analyzer;
import kcwiki.x.kcscanner.core.start2.processor.Start2Utils;
import kcwiki.x.kcscanner.httpclient.HttpClientConfig;
import kcwiki.x.kcscanner.httpclient.HttpUtils;
import kcwiki.x.kcscanner.httpclient.entity.kcapi.start2.Start2;
import kcwiki.x.kcscanner.httpclient.impl.UploadStart2;
import kcwiki.x.kcscanner.initializer.AppConfigs;
import kcwiki.x.kcscanner.tools.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.hc.client5.http.config.RequestConfig;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author x5171
 */
@RestController
@RequestMapping("/api")
public class Api {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Api.class);
    
    @Autowired
    AppConfigs appConfigs; 
    @Autowired
    HttpClientConfig httpClientConfig;
    @Autowired
    FileController fileController;
    @Autowired
    Start2Controller start2Controller;
    @Autowired
    Start2Analyzer start2Analyzer;
    @Autowired
    UploadStart2 uploadStart2;
    
    @RequestMapping("/test")
    public String apitest(@RequestParam(value="name", defaultValue="World") String name) {
        return "Hello "+name;
    }
    
    @RequestMapping("/diffstart2")
    public String diffstart2(@RequestParam(value="src", defaultValue="2018090300") String src, @RequestParam(value="dest", defaultValue="2018090904") String dest) {
        RequestConfig requestConfig;
        if(appConfigs.isAllow_use_proxy()){
            requestConfig = httpClientConfig.makeProxyConfig(true);
        } else {
            requestConfig = httpClientConfig.makeProxyConfig(false);
        }
        String scrStr = HttpUtils.getHttpBody(String.format("http://api.kcwiki.moe/start2/%s", src), requestConfig);
        String destStr = HttpUtils.getHttpBody(String.format("http://api.kcwiki.moe/start2/%s", dest), requestConfig);
        if(StringUtils.isBlank(scrStr) || StringUtils.isBlank(destStr)){
            return "FAIL NULL";
        }
        start2Controller.downloadFile(Start2Utils.start2pojo(scrStr), Start2Utils.start2pojo(destStr));
        return "SUCCESS \r\n" + "src: " + src + "dest: " + dest;
    }
    
    @RequestMapping("/scanfile")
    public String scanfile(@RequestParam(value="password", defaultValue="World") String password) {
        if(!password.equals("password"))
            return "Fail";
        fileController.startScan(false);
        return "SUCCESS";
    }
    
    @RequestMapping("/checkStart2")
    public String checkStart2(@RequestParam(value="password", defaultValue="World") String password) {
        if(!password.equals("password"))
            return "Fail";
        start2Controller.getLatestStart2Data();
        return String.format("start2 status: \r\n"
                + "PrevStart2Data: \r\n %s"
                + "Start2Data: \r\n %s"
                + "PrevStart2DataTimestamp: \r\n %s", 
                start2Controller.getPrevStart2Data().toString(),
                start2Controller.getStart2Data().toString(),
                start2Controller.getPrevStart2DataTimestamp().toString()
        );
    }
    
    @RequestMapping("/getstart2")
    public String getstart2(@RequestParam(value="password", defaultValue="World") String password) {
        if(!password.equals("password"))
            return "Fail";
        start2Controller.getLatestStart2Data();
        return JsonUtils.object2json(AppDataCache.start2data, null);
    }
    
    @RequestMapping("/downloaddata")
    public String downloaddata(@RequestParam(value="password", defaultValue="World") String password) {
        if(!password.equals("password"))
            return "Fail";
        start2Controller.downloadFile(false);
        return "SUCCESS";
    }
    
}
