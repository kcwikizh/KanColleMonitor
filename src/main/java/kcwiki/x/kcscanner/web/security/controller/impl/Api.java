/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.web.security.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import kcwiki.x.kcscanner.cache.inmem.AppDataCache;
import kcwiki.x.kcscanner.core.files.FileController;
import kcwiki.x.kcscanner.core.kcdata.KcdataController;
import kcwiki.x.kcscanner.core.start2.Start2Controller;
import kcwiki.x.kcscanner.core.start2.processor.Start2Analyzer;
import kcwiki.x.kcscanner.core.start2.processor.Start2Utils;
import kcwiki.x.kcscanner.httpclient.HttpClientConfig;
import kcwiki.x.kcscanner.httpclient.HttpUtils;
import kcwiki.x.kcscanner.httpclient.impl.UploadStart2;
import kcwiki.x.kcscanner.initializer.AppConfig;
import kcwiki.x.kcscanner.message.websocket.MessagePublisher;
import kcwiki.x.kcscanner.message.websocket.entity.DownLoadResult;
import kcwiki.x.kcscanner.message.websocket.types.ModuleType;
import kcwiki.x.kcscanner.types.FileType;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.hc.client5.http.config.RequestConfig;
import org.iharu.util.JsonUtils;
import org.iharu.web.BaseController;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author iHaru
 */
@RestController
@RequestMapping("/api")
public class Api extends BaseController {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Api.class);
    
    @Autowired
    AppConfig appConfig; 
    @Autowired
    HttpClientConfig httpClientConfig;
    @Autowired
    FileController fileController;
    @Autowired
    Start2Controller start2Controller;
    @Autowired
    KcdataController kcdataController;
    @Autowired
    Start2Analyzer start2Analyzer;
    @Autowired
    UploadStart2 uploadStart2;
    @Autowired
    private MessagePublisher messagePublisher;
    
    ScheduledExecutorService executor = null;  
    
    
    @RequestMapping("/test")
    public String apitest(@RequestParam(value="name", defaultValue="World") String name) {
        return "Hello " + name + "<br>";
    }
    
    @RequestMapping("/pushdownloadresult")
    public String pushimgs(@RequestParam(value="name", defaultValue="World") String name) {
        List<String> list = new ArrayList();
        list.add("start2file/new/kcs2/resources/map/042/01_image.png");
        list.add("start2file/new/kcs2/resources/ship/集積地棲姫 バカンスmode-壊/KanMusu1814HDIllust.png");
        list.add("start2file/new/kcs2/resources/ship/神鷹改二/KanMusu536HDDmg.png");
        list.add("start2file/new/kcs2/resources/slot/九七式艦攻(九三一空2F熟練)/Soubi302HD.png");
        DownLoadResult downLoadResult = new DownLoadResult();
        downLoadResult.setType(FileType.Mapinfo);
        downLoadResult.setFilelist(list);
        messagePublisher.publish(downLoadResult, ModuleType.DownloadResult);
        return "SUCCESS";
    }
    
    @RequestMapping("/pushinfo")
    public String pushinfo(@RequestParam(value="name", defaultValue="World") String name) {
        messagePublisher.publish("系统消息测试", ModuleType.SystemInfo);
        return "SUCCESS";
    }
    
    @RequestMapping("/startmonitor")
    public String startmonitor() {
        if(executor != null)
            executor.shutdownNow();
        executor = Executors.newScheduledThreadPool(3); 
        messagePublisher.publish("start monitoring...", ModuleType.SystemInfo);
        executor.scheduleAtFixedRate(  
               new Runnable() {
                    @Override
                    public void run() {
                        try{
                            fileController.autoScan();
                        } catch (Exception ex) {
                            LOG.error(ExceptionUtils.getStackTrace(ex));
                        }
                    }
                },  
               1,  
               3,  
               TimeUnit.MINUTES);  
        return "SUCCESS";
    }
    
    @RequestMapping("/stopmonitor")
    public String stopmonitor() {
        if(executor == null)
            return "No monitor running...";
        executor.shutdownNow();
        messagePublisher.publish("stop monitor...", ModuleType.SystemInfo);
        return "SUCCESS";
    }
    
    @RequestMapping("/diffstart2")
    public String diffstart2(@RequestParam(value="src", defaultValue="") String src, 
            @RequestParam(value="dest", defaultValue="") String dest, 
            @RequestParam(value="seasonal", defaultValue="true") String seasonal,
            @RequestParam(value="alldata", defaultValue="false") String alldata) {
        RequestConfig requestConfig;
        if(appConfig.isAllow_use_proxy()){
            requestConfig = httpClientConfig.makeProxyConfig(true);
        } else {
            requestConfig = httpClientConfig.makeProxyConfig(false);
        }
        boolean isAlldata = "true".equals(alldata.toLowerCase());
        Map<Integer, String> data = null;
        if(isAlldata){
            data = kcdataController.getShipWikiID();
        }
        String scrStr = StringUtils.isBlank(src)? 
                HttpUtils.getHttpBody("http://api.kcwiki.moe/start2/prev", requestConfig): 
                HttpUtils.getHttpBody(String.format("http://api.kcwiki.moe/start2/%s", src), requestConfig);
        String destStr = StringUtils.isBlank(dest)? 
                HttpUtils.getHttpBody("http://api.kcwiki.moe/start2", requestConfig): 
                HttpUtils.getHttpBody(String.format("http://api.kcwiki.moe/start2/%s", dest), requestConfig);
        
        if(StringUtils.isBlank(scrStr) || StringUtils.isBlank(destStr)){
            return "FAIL NULL";
        }
        if(scrStr.contains("error"))
            return scrStr;
        if(destStr.contains("error"))
            return destStr;
        boolean isSeasonal = "true".equals(seasonal.toLowerCase());
        start2Controller.downloadFile(Start2Utils.start2pojo(scrStr), Start2Utils.start2pojo(destStr), isSeasonal, data);
        return "SUCCESS" + "<br>isSeasonal:\t" + isSeasonal + "<br><br>src: <br>" + src + "<br>dest: <br>" + dest;
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
        return String.format("start2 status: <br>"
                + "PrevStart2Data: %s <br>"
                + "Start2Data: %s<br>"
                + "PrevStart2DataTimestamp: %s"
                + "diff: %s<br>",
                start2Controller.getPrevStart2Data().toString(),
                start2Controller.getStart2Data().toString(),
                start2Controller.getPrevStart2DataTimestamp().toString(),
                JsonUtils.object2json(JsonUtils.diffObject(start2Controller.getPrevStart2Data(), start2Controller.getStart2Data()))
        );
    }
    
    @RequestMapping("/getstart2")
    public String getstart2(@RequestParam(value="password", defaultValue="World") String password) {
        if(!password.equals("password"))
            return "Fail";
        start2Controller.getLatestStart2Data();
        return JsonUtils.object2json(AppDataCache.start2data);
    }
    
    @RequestMapping("/downloaddata")
    public String downloaddata(@RequestParam(value="password", defaultValue="World") String password, @RequestParam(value="seasonal", defaultValue="true") String seasonal) {
        if(!password.equals("password"))
            return "Fail";
        boolean isSeasonal = "true".equals(seasonal.toLowerCase());
        start2Controller.downloadFile(false, isSeasonal);
        return "SUCCESS" + "<br>isSeasonal:\t" + isSeasonal;
    }
    
    @RequestMapping("/downloadall")
    public String downloadall(@RequestParam(value="password", defaultValue="World") String password, 
            @RequestParam(value="seasonal", defaultValue="true") String seasonal,
            @RequestParam(value="alldata", defaultValue="false") String alldata) {
        if(!password.equals("password"))
            return "Fail";
        boolean isSeasonal = "true".equals(seasonal.toLowerCase());
        boolean isAlldata = "true".equals(alldata.toLowerCase());
        Map<Integer, String> data = null;
        if(isAlldata){
            data = kcdataController.getShipWikiID();
        }
        
        RequestConfig requestConfig;
        if(appConfig.isAllow_use_proxy()){
            requestConfig = httpClientConfig.makeProxyConfig(true);
        } else {
            requestConfig = httpClientConfig.makeProxyConfig(false);
        }
        String destStr = HttpUtils.getHttpBody("http://api.kcwiki.moe/start2", requestConfig);

        start2Controller.downloadFile(null, Start2Utils.start2pojo(destStr), isSeasonal, data);
        return "SUCCESS" + "<br>isSeasonal:\t" + isSeasonal;
    }
    
}
