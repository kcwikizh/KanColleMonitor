/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.httpclient.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.Thread.sleep;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.cookie.Cookie;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import kcwiki.x.kcscanner.cache.inmem.AppDataCache;
import kcwiki.x.kcscanner.exception.ExceptionBase;
import static kcwiki.x.kcscanner.tools.ConstantValue.LINESEPARATOR;
import kcwiki.x.kcscanner.types.KcServerStatus;
import kcwiki.x.kcscanner.types.PublishStatus;
import kcwiki.x.kcscanner.types.PublishTypes;
import kcwiki.x.kcscanner.types.ServiceTypes;
/*
 *  *   *   *
 *   此为KcWiki拆包工具子模块，如需单独使用请手动清除main方法的注释。
 *  *   *   *
 *   HttpUtil类说明页面
 *   http://blog.csdn.net/chaijunkun/article/details/40145685  
 *   ooi作者写的登陆类
 *   https://github.com/acgx/ooi3/blob/master/auth/kancolle.py 
 *   https://github.com/acgx/ooi2/blob/master/auth/kancolle.py
 *  *   *   *
 *   备注：
 *   HttpUtil类已经过修改，添加了setHeader，
 *   开启了get自动跳转，以及应用了代理。
 *   环境 JDK1.8 
 *   2016-12-01
 */
@Component
@Scope("prototype")
public class AutoLogin extends BaseHttpClient {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(AutoLogin.class);

    private String user_name;
    private String user_pwd;
    private String login_dmm_token;
    private String login_token;
    private String ajax_token;
    private String netgame_osapi_url;
    private String netgame_owner_id;
    private String netgame_mid;
    private String netgame_st;
    private String netgame_server;
    private Long MRR_timecode; 
    private String MRR_world;
    private String MRR_world_id;
    private String MR_token;
    private String MR_starttime;
    private StringBuilder buffer;
    private Pattern p;
    private Matcher m;
    
    private boolean isStop = false;
    
    private boolean initClient() {
        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();
	ConnectionSocketFactory	plainSF = new PlainConnectionSocketFactory();
		registryBuilder.register("http", plainSF);
                TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        @Override
                        public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                        }
                        @Override
                        public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                        }
                    }
                };
                
		//指定信任密钥存储对象和连接套接字工厂
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			SSLContext sslContext = SSLContext.getInstance("SSL");
                        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
			SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(sslContext,new NoopHostnameVerifier()); 
			registryBuilder.register("https", ssf);
		} catch (KeyStoreException e) {
			throw new RuntimeException(e);
		} catch (KeyManagementException e) {
			throw new RuntimeException(e);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		Registry<ConnectionSocketFactory> registry = registryBuilder.build();
		connManager = new PoolingHttpClientConnectionManager(registry);
                httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).setConnectionManager(connManager).build();
        
        HttpGet httpGet = new HttpGet("https://www.dmm.com");
        httpGet.setConfig(config);
        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            int rspcode = response.getCode();
            messagePublisher.publish(
                    String.format("测试： %d %s" , 
                        rspcode, 
                        response.getReasonPhrase()
                    ), 
                    PublishTypes.Admin, PublishStatus.NORMAL
            );
            if (rspcode == 200) {
                return true;
            }
        } catch (IOException ex) {  
            LOG.error("测试： 失败，发生IOException错误。");
            try {
                httpclient.close();
            } catch (IOException ex1) {
            }
            messagePublisher.publish(
                    String.format("测试： 失败，发生IOException错误。"), 
                    PublishTypes.Admin, 
                    PublishStatus.NORMAL
            );
        }
        return false;
    }
    
    private boolean Login() throws IOException{
            HttpGet httpGet = new HttpGet("https://www.dmm.com/my/-/login/=/path=Sg9VTQFXDFcXFl5bWlcKGExKUVdUXgFNEU0KSVMVR28MBQ0BUwJZBwxK/");
            httpGet.setConfig(config);
            httpGet.setHeader("Upgrade-Insecure-Requests", "1"); 
            httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36");  
            httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"); 
            //httpPost.setHeader("DNT", "1");       
            httpGet.setHeader("Accept-Encoding", "gzip, deflate, br");  
            httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
            cookies = cookieStore.getCookies();
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                LOG.debug("Code:{}, Phrase:{}", response.getCode(), response.getReasonPhrase());
                InputStream in=response.getEntity().getContent();
                String retVal = readStream(in);
                p=Pattern.compile("http-dmm-token\" content=\"([\\d|\\w]+)");
                m=p.matcher(retVal);
                if(m.find()){
                    login_dmm_token=m.group(1);
                }
                p=Pattern.compile("token\" content=\"([\\d|\\w]+)");
                m=p.matcher(retVal);
                if(m.find()){
                    login_token=m.group(1);
                }
            }
            if(login_token != null){
                //messagePublisher.publish("Login成功", PublishTypes.Admin, PublishStatus.SUCCESS);
            }else{
                return false;
            }
        
        
        return true;
    }
    
    private boolean Ajax() throws IOException {
        
            final List<NameValuePair> nvps = new ArrayList<>();
            HttpPost httpPost = new HttpPost("https://accounts.dmm.com/service/api/get-token");
            httpPost.setConfig(config);
            httpPost.setHeader("Accept", "application/json, text/plain, */*");
            httpPost.setHeader("Origin", "https://accounts.dmm.com"); 
            httpPost.setHeader("X-Requested-With", "XMLHttpRequest"); 
            httpPost.setHeader("content-type", "application/json;charset=UTF-8"); 
            httpPost.setHeader("http-dmm-token", login_dmm_token); 
            httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36");  
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"); 
            //httpPost.setHeader("Referer", "https://accounts.dmm.com/service/login/password/=/path=Sg9VTQFXDFcXFl5bWlcKGExKUVdUXgFNEU0KSVMVR28MBQ0BUwJZBwxK");    
            httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");  
            httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
            nvps.add(new BasicNameValuePair("token", login_token));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            for(Cookie c:cookies){
                cookieStore.addCookie(c);
            }

            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                LOG.debug("Code:{}, Phrase:{}", response.getCode(), response.getReasonPhrase());
                InputStream in=response.getEntity().getContent();
                String retVal = readStream(in);
                p=Pattern.compile("token\":\"([\\d|\\w]+)");
                m=p.matcher(retVal);
                if(m.find()){
                    ajax_token=m.group(1);
                }
                cookies = cookieStore.getCookies();
            }
            
            if(ajax_token != null){
                //messagePublisher.publish("成功交换数据", PublishTypes.Admin, PublishStatus.SUCCESS);
            }else{
                return false;
            }
        return true;
    }
    
    private boolean Auth() throws ExceptionBase, IOException {
        final List<NameValuePair> nvps = new ArrayList<>();
        HttpPost httpPost = new HttpPost("https://accounts.dmm.com/service/login/password/authenticate");
            httpPost.setConfig(config);
            httpPost.setHeader("Cache-Control", "max-age=0"); 
            httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            httpPost.setHeader("Origin", "https://accounts.dmm.com"); 
            httpPost.setHeader("Upgrade-Insecure-Requests", "1"); 
            httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36");  
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded"); 
            //httpPost.setHeader("Referer", "https://accounts.dmm.com/service/login/password/=/path=Sg9VTQFXDFcXFl5bWlcKGExKUVdUXgFNEU0KSVMVR28MBQ0BUwJZBwxK");    
            httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");  
            httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8");

            nvps.add(new BasicNameValuePair("token", ajax_token));
            nvps.add(new BasicNameValuePair("login_id", user_name));
            nvps.add(new BasicNameValuePair("password", user_pwd));
            nvps.add(new BasicNameValuePair("idKey", user_name));
            nvps.add(new BasicNameValuePair("pwKey", user_pwd));
            nvps.add(new BasicNameValuePair("path", "Sg9VTQFXDFcXFl5bWlcKGExKUVdUXgFNEU0KSVMVR28MBQ0BUwJZBwxK"));
            nvps.add(new BasicNameValuePair("prompt", ""));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            for(Cookie c:cookies){
                cookieStore.addCookie(c);
            }
            
            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                LOG.debug("Code:{}, Phrase:{}", response.getCode(), response.getReasonPhrase());
                InputStream in=response.getEntity().getContent();
                String retVal = readStream(in);
                        p=Pattern.compile("地域からご利用");
                        m=p.matcher(retVal);
                        if(m.find()){
                            messagePublisher.publish("非日本IP登陆", PublishTypes.Admin, PublishStatus.ERROR);
                            throw new ExceptionBase(ServiceTypes.KanColleServer, KcServerStatus.ERROR, "非日本IP登陆");
                        }
                        p=Pattern.compile("認証エラー");
                        m=p.matcher(retVal);
                        if(m.find()){
                            messagePublisher.publish("DMM强制要求修改密码", PublishTypes.Admin, PublishStatus.ERROR);
                            throw new ExceptionBase(ServiceTypes.KanColleServer, KcServerStatus.ERROR, "DMM强制要求修改密码");
                        }
                        cookies = cookieStore.getCookies();
            }
        return true;
    }
    
    private boolean NetGame() throws ExceptionBase, IOException {
        
            HttpGet httpGet = new HttpGet("http://www.dmm.com/netgame/social/-/gadgets/=/app_id=854854/");
            httpGet.setConfig(config);
            httpGet.setHeader("Host", "www.dmm.com");  
            httpGet.setHeader("connection", "keep-alive");  
            httpGet.setHeader("Upgrade-Insecure-Requests", "1"); 
            httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36");  
            httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"); 
            httpGet.setHeader("DNT", "1");       
            httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");  
            httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8"); 
            
            String cookieStr = "";
            for(Cookie c:cookies){
                cookieStr += c.getName()+"="+c.getValue()+"; ";
            }
            cookieStr = cookieStr.substring(0, cookieStr.length()-1);
            httpGet.setHeader("Cookie", cookieStr); 
            if(!cookieStr.contains("login_secure_id")){
                messagePublisher.publish("Cookie获取有误", PublishTypes.Admin, PublishStatus.ERROR);
                throw new ExceptionBase(ServiceTypes.KanColleServer, KcServerStatus.ERROR, "Cookie获取有误");
            }
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                LOG.debug("Code:{}, Phrase:{}", response.getCode(), response.getReasonPhrase());
                InputStream in=response.getEntity().getContent();
                String retVal = readStream(in);
                p=Pattern.compile("OWNER_ID\\W+(\\d+),");
                m=p.matcher(retVal);
                if(m.find()){
                    netgame_owner_id=m.group(1);
                }
                p=Pattern.compile("URL\\W+:\\W+\"(.*)\",");
                m=p.matcher(retVal);
                if(m.find()){
                    netgame_osapi_url=m.group(1);
                }else{
                    messagePublisher.publish("输入的用户密码有误", PublishTypes.Admin, PublishStatus.ERROR);
                    throw new ExceptionBase(ServiceTypes.KanColleServer, KcServerStatus.ERROR, "输入的用户密码有误");
                }
                p=Pattern.compile("&mid=(\\d+)");
                m=p.matcher(netgame_osapi_url);
                if(m.find()){
                    netgame_mid=m.group(1);
                }
                p=Pattern.compile("url=http%3A%2F%2F(.*)%2Fgadget.xml");
                m=p.matcher(netgame_osapi_url);
                if(m.find()){
                    netgame_server=m.group(1);
                }
                p=Pattern.compile("ST\\W+\"(.*)\",");
                m=p.matcher(retVal);
                if(m.find()){
                    netgame_st=m.group(1);
                }
                cookies = cookieStore.getCookies();
            }
            
            if(netgame_osapi_url != null){
                //messagePublisher.publish("跳转成功", PublishTypes.Admin, PublishStatus.SUCCESS);
            }else{
                return false;
            }
        return true;
    }
    
    private boolean MakeRequestRefresh() throws IOException {
        MRR_timecode=System.currentTimeMillis();
            String url="http://osapi.dmm.com/gadgets/makeRequest?refresh=3600&url=http%3A%2F%2F"+netgame_server+"%2Fkcsapi%2Fapi_world%2Fget_id%2F"+netgame_owner_id+"%2F1%2F"+MRR_timecode+"&httpMethod=GET&headers=&postData=&authz=&st=&contentType=JSON&numEntries=3&getSummaries=false&signOwner=true&signViewer=true&gadget=http%3A%2F%2F"+netgame_server+"%2Fgadget.xml&container=dmm&bypassSpecCache=&getFullHeaders=false";
            HttpGet httpGet = new HttpGet(url);
            httpGet.setConfig(config);
            httpGet.setHeader("Accept", "*/*");  
            httpGet.setHeader("DNT", "1");  
            httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");  
            httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");  
            httpGet.setHeader("connection", "keep-alive");  
            httpGet.setHeader("Host", "osapi.dmm.com");       
            httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36");    
            for(Cookie c:cookies){
                cookieStore.addCookie(c);
            }
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                LOG.debug("Code:{}, Phrase:{}", response.getCode(), response.getReasonPhrase());
                InputStream in=response.getEntity().getContent();
                String retVal = readStream(in);
                retVal = retVal.replace("throw 1; < don't be evil' >", "");
                JsonObject jobj = (JsonObject) new JsonParser().parse(retVal);
                Set<String> ks = jobj.keySet();
                for(String i:ks){
                   jobj = jobj.getAsJsonObject(i);
                }
                if(!retVal.contains("成功") && !retVal.contains("\\u6210\\u529f")){
                    messagePublisher.publish("服务器仍在维护中", PublishTypes.Admin, PublishStatus.NORMAL);
                    sleep(90*1000);
                    return false;
                }
                p=Pattern.compile("api_world_id\\W+(\\d*)");
                m=p.matcher(retVal);
                if(m.find()){
                    MRR_world_id = m.group(1);
                    MRR_world=AppDataCache.gameWorlds.get(Integer.parseInt(MRR_world_id));
                }
            } catch (InterruptedException ex) {
                LOG.error("AutoLogin - MakeRequestRefresh - InterruptedException - {}{}", LINESEPARATOR, ExceptionUtils.getStackTrace(ex));
            }
            
            if(MRR_world != null){
                //messagePublisher.publish("游戏Token获取成功！", PublishTypes.Admin, PublishStatus.SUCCESS); 
            }else{
                return false;
            }
        return true;
    }
    
    private boolean MakeRequest() throws IOException {
        String referer = "http://osapi.dmm.com/gadgets/ifr?synd=dmm&container=dmm&owner="+netgame_owner_id+"&viewer="+netgame_owner_id+"&aid=854854&mid="+netgame_mid+"&country=jp&lang=ja&view=canvas&parent=http%3A%2F%2Fwww.dmm.com%2Fnetgame%2Fsocial%2F&url=http%3A%2F%2F"+netgame_server+"%2Fgadget.xml&st="+netgame_st;   
        String url="http://osapi.dmm.com/gadgets/makeRequest";
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);
        httpPost.setHeader("Host", "osapi.dmm.com");  
        httpPost.setHeader("connection", "keep-alive");  
        //httpPost.setHeader("Content-length", String.valueOf(mydata.length));    
        httpPost.setHeader("Origin", "http://osapi.dmm.com"); 
        httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36");  
        httpPost.setHeader("Accept", "*/*"); 
        httpPost.setHeader("DNT", "1");
        httpPost.setHeader("Referer", referer);  
        httpPost.setHeader("Accept-Encoding", "gzip, deflate");  
        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8");  
        
        final List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("url", "http://"+MRR_world+"/kcsapi/api_auth_member/dmmlogin/"+netgame_owner_id+"/1/"+MRR_timecode));
        nvps.add(new BasicNameValuePair("httpMethod", "GET"));
        nvps.add(new BasicNameValuePair("headers", ""));
        nvps.add(new BasicNameValuePair("postData", ""));
        nvps.add(new BasicNameValuePair("authz", "signed"));
        nvps.add(new BasicNameValuePair("st", netgame_st));
        nvps.add(new BasicNameValuePair("contentType", "JSON"));
        nvps.add(new BasicNameValuePair("numEntries", "3"));
        nvps.add(new BasicNameValuePair("getSummaries", "false"));
        nvps.add(new BasicNameValuePair("signOwner", "true"));
        nvps.add(new BasicNameValuePair("signViewer", "true"));
        nvps.add(new BasicNameValuePair("gadget", "http://"+netgame_server+"/gadget.xml"));
        nvps.add(new BasicNameValuePair("container", "dmm"));
        nvps.add(new BasicNameValuePair("bypassSpecCache", ""));
        nvps.add(new BasicNameValuePair("getFullHeaders", "false"));
        nvps.add(new BasicNameValuePair("oauthState", ""));
        nvps.add(new BasicNameValuePair("OAUTH_SIGNATURE_PUBLICKEY", "key_2018"));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            for(Cookie c:cookies){
                cookieStore.addCookie(c);
            }

        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                LOG.debug("Code:{}, Phrase:{}", response.getCode(), response.getReasonPhrase());
                InputStream in=response.getEntity().getContent();
                String retVal = readStream(in);
                p=Pattern.compile("api_token\\W+([\\d|\\w]+)");
                m=p.matcher(retVal);
                if(m.find()){
                    MR_token=m.group(1);
                }
                p=Pattern.compile("api_starttime\\W+([\\d]+)");
                m=p.matcher(retVal);
                if (m.find()) {
                    MR_starttime=m.group(1);
                }
                cookies = cookieStore.getCookies();
        }
        return true;
    }
    
    private boolean Start2() throws IOException {
        String referer = "http://"+MRR_world+"/kcs/mainD2.swf?api_token="+MR_token+"&api_starttime="+MR_starttime+"/[[DYNAMIC]]/1"; 
        String url="http://"+MRR_world+"/kcsapi/api_start2";
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);
        httpPost.setHeader("Host", MRR_world);  
        httpPost.setHeader("Connection", "keep-alive");  
        httpPost.setHeader("Origin", String.format("http://%s", MRR_world)); 
        httpPost.setHeader("X-Requested-With", "ShockwaveFlash/22.0.0.209"); 
        httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36");  
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");  
        httpPost.setHeader("Accept", "*/*"); 
        httpPost.setHeader("DNT", "1");
        httpPost.setHeader("Referer", referer);  
        httpPost.setHeader("Accept-Encoding", "gzip, deflate");  
        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        
        final List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("api_token", MR_token));
        nvps.add(new BasicNameValuePair("api_verno", "1"));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            for(Cookie c:cookies){
                cookieStore.addCookie(c);
            }

            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                LOG.debug("Code:{}, Phrase:{}", response.getCode(), response.getReasonPhrase());
                buffer=new StringBuilder();
                String line;
                InputStream in=response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                while ((line = reader.readLine()) != null) {      
                    buffer.append(line);      
                }  
                LOG.debug("Start2 RawData: {}", buffer);
            }
        return true;
    }
    
    public boolean netStart() throws ExceptionBase, IOException {
        if(!initClient()){
            return false;
        }
        try {
            while(true){
                if(Login()){
                    messagePublisher.publish("获取dmm_token成功", PublishTypes.Admin, PublishStatus.SUCCESS);
                    break;
                }
            }
            
            while(true){
                if(Ajax()){
                    messagePublisher.publish("成功交换数据", PublishTypes.Admin, PublishStatus.SUCCESS);
                    break;
                }
            }
            while(true){
                if(Auth()){
                    messagePublisher.publish("接口认证通过", PublishTypes.Admin, PublishStatus.SUCCESS);
                    break;
                }
            }
            while(true){
                if(NetGame()){
                    messagePublisher.publish("跳转成功", PublishTypes.Admin, PublishStatus.SUCCESS);
                    break;
                }
            }
            while(true){
                if(MakeRequestRefresh()){
                    messagePublisher.publish("游戏Token获取成功！", PublishTypes.Admin, PublishStatus.SUCCESS); 
                    break;
                }
            }
            while(true){
                if(MakeRequest()){
                    break;
                }
            }
            while(true){
                if(Start2()){
                    break;
                }
            }
            this.httpclient.close();
        } catch (IOException | ExceptionBase ex) {
            LOG.error("AutoLogin - netStart - {}{}", LINESEPARATOR, ExceptionUtils.getStackTrace(ex));
            messagePublisher.publish(
                    String.format("GetStart2发生IOException错误！错误信息概述为：{}", ex.getMessage()), 
                    PublishTypes.Admin, PublishStatus.ERROR); 
            if(ex instanceof ExceptionBase) {
                throw ex;
            }
            return false;
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex1) {
                LOG.error("AutoLogin - netStart - httpclient.close() - {}{}", LINESEPARATOR, ExceptionUtils.getStackTrace(ex1));
            }
        }
        return true;  
    }

    /**
     * @return the isStop
     */
    public boolean isIsStop() {
        return isStop;
    }

    /**
     * @param aIsStop the isStop to set
     */
    public void setIsStop(boolean aIsStop) {
        isStop = aIsStop;
    }
    
    /**
     * @param user_name the user_name to set
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /**
     * @param user_pwd the user_pwd to set
     */
    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    /**
     * @return the buffer
     */
    public StringBuilder getBuffer() {
        return buffer;
    }
    
}
