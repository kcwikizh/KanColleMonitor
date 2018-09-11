/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kcwiki.monitor.start2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.Thread.sleep;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import org.kcwiki.initializer.MainServer;
import moe.kcwiki.handler.massage.msgPublish;
import org.kcwiki.tools.constant.constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.kcwiki.tools.RWFile;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.cookie.BasicCookieStore;
import org.apache.hc.client5.http.cookie.Cookie;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.http.message.BasicNameValuePair;

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

public class GetStart2 {
    
    //private static Map<String, org.apache.http.cookie.Cookie> cookies = new HashMap<String, org.apache.http.cookie.Cookie>();
    private static final String user_name = MainServer.getKcUser();   //dmm账号
    private static final String user_pwd = MainServer.getKcPassword();    //dmm密码
//    private static final String user_name = "517138883@qq.com";   //dmm账号
//    private static final String user_pwd = "Xuenxiang520";    //dmm密码
    private static String login_dmm_token;
    private static String login_token;
    private static String ajax_token;
    private static String ajax_login_id;
    private static String ajax_password;
    private static String netgame_osapi_url;
    private static String netgame_owner_id;
    private static String netgame_mid;
    private static String netgame_st;
    private static String netgame_server;
    private static Long MRR_timecode; 
    private static String MRR_world;
    private static String MRR_world_id;
    private static String[] worldlist;
    private static String MR_token;
    private static String MR_starttime;
    private static StringBuilder buffer;
    private static Pattern p;
    private static Matcher m;
    private static List<JSONObject> userList= new ArrayList<>();
    
    private static boolean isStop = false;
    //设置连接管理器
    private static PoolingHttpClientConnectionManager connManager ;
    private static final HttpHost proxy = new HttpHost(MainServer.getProxyhost(), MainServer.getProxyport());   //代理设置
//    private static final HttpHost proxy = new HttpHost("127.0.0.1", 8123);
    private static final RequestConfig config = RequestConfig.custom().setProxy(proxy).setRedirectsEnabled(true).build();
//    private static final RequestConfig config = RequestConfig.custom().setRedirectsEnabled(true).build();
    private static final BasicCookieStore cookieStore = new BasicCookieStore();
    private static List<Cookie> cookies = cookieStore.getCookies();
    private static CloseableHttpClient httpclient ;
    
    public String readStream(InputStream in){
        StringBuilder data = new StringBuilder();
        try {
            InputStreamReader isr = new InputStreamReader(in, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                data.append(line).append(constant.LINESEPARATOR);
            }
            data = data.delete(data.length()-1, data.length());
        } catch (IOException ex) {
            Logger.getLogger(GetStart2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data.toString();
    }
    
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
            msgPublish.msgPublisher("IP: " + MainServer.getProxyhost() + "\t PORT: " + MainServer.getProxyport(),0,0);
            msgPublish.msgPublisher("测试： " + rspcode + " " + response.getReasonPhrase(),0,0);
            if (rspcode == 200) {
                
                return true;
            }
        } catch (IOException ex) {    
            try {
                httpclient.close();
            } catch (IOException ex1) {
                Logger.getLogger(GetStart2.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(GetStart2.class.getName()).log(Level.SEVERE, null, ex);
            msgPublish.msgPublisher("测试： 失败，发生IOException错误。代理可能失效。" ,0,-1);
        }
        return false;
    }
    
    public boolean Login() throws IOException{
        //try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
        
        //HttpClientBuilder.create().setDefaultCookieStore(cookieStore).setConnectionManager(connManager).build()
            HttpGet httpGet = new HttpGet("https://www.dmm.com/my/-/login/=/path=Sg9VTQFXDFcXFl5bWlcKGExKUVdUXgFNEU0KSVMVR28MBQ0BUwJZBwxK/");
            httpGet.setConfig(config);
            httpGet.setHeader("Upgrade-Insecure-Requests", "1"); 
            httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.62 Safari/537.36");  
            httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"); 
            //httpPost.setHeader("DNT", "1");       
            httpGet.setHeader("Accept-Encoding", "gzip, deflate, br");  
            httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
            cookies = cookieStore.getCookies();
            // The underlying HTTP connection is still held by the response object
            // to allow the response content to be streamed directly from the network socket.
            // In order to ensure correct deallocation of system resources
            // the user MUST call CloseableHttpResponse#close() from a finally clause.
            // Please note that if response content is not fully consumed the underlying
            // connection cannot be safely re-used and will be shut down and discarded
            // by the connection manager.
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                System.out.println(response.getCode() + " " + response.getReasonPhrase());
                //final HttpEntity entity = response.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                //EntityUtils.consume(entity);
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
                //msgPublish.msgPublisher("Login成功",0,1);
            }else{
                return false;
            }
        
        
        return true;
    }
    
    public boolean Ajax() {
        
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
                System.out.println(response.getCode() + " " + response.getReasonPhrase());
                //final HttpEntity entity = response.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                InputStream in=response.getEntity().getContent();
                String retVal = readStream(in);
                p=Pattern.compile("token\":\"([\\d|\\w]+)");
                m=p.matcher(retVal);
                if(m.find()){
                    ajax_token=m.group(1);
                }
                p=Pattern.compile("login_id\":\"([\\d|\\w]+)");
                m=p.matcher(retVal);
                if(m.find()){
                    ajax_login_id=m.group(1);
                }
                p=Pattern.compile("password\":\"([\\d|\\w]+)");
                m=p.matcher(retVal);
                if (m.find()) {
                    ajax_password=m.group(1);
                }
                cookies = cookieStore.getCookies();
            } catch (IOException ex) {
            Logger.getLogger(GetStart2.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
            if(ajax_token != null){
                //msgPublish.msgPublisher("成功交换数据",0,1);
            }else{
                return false;
            }
        return true;
    }
    
    public boolean Auth(){
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
                System.out.println(response.getCode() + " " + response.getReasonPhrase());
                //final HttpEntity entity = response.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                InputStream in=response.getEntity().getContent();
                String retVal = readStream(in);
                        p=Pattern.compile("地域からご利用");
                        m=p.matcher(retVal);
                        if(m.find()){
                            //JOptionPane.showMessageDialog(null, "非日本IP登陆");
                            msgPublish.msgPublisher("非日本IP登陆",0,-1);
                            return false;
                        }
                        p=Pattern.compile("認証エラー");
                        m=p.matcher(retVal);
                        if(m.find()){
                            //JOptionPane.showMessageDialog(null, "DMM强制要求修改密码");
                            msgPublish.msgPublisher("DMM强制要求修改密码",0,-1);
                            return false;
                        }
                        cookies = cookieStore.getCookies();
                /*if(response.getCode() == 302 || response.getCode() == 301) {
                    String redirectURL = response.getFirstHeader("Location").getValue();
                    if(redirectURL == null){
                        System.err.println(response.getCode()+"no Location");
                        return false;
                    }
                    httpGet = new HttpGet(redirectURL);
                    httpGet.setConfig(config);
                    try (CloseableHttpResponse redirectResponse = httpclient.execute(httpGet)) {
                        System.out.println(redirectResponse.getCode() + " " + redirectResponse.getReasonPhrase());
                        
                        InputStream in=redirectResponse.getEntity().getContent();
                        String retVal = readStream(in);
                        p=Pattern.compile("地域からご利用");
                        m=p.matcher(retVal);
                        if(m.find()){
                            //JOptionPane.showMessageDialog(null, "非日本IP登陆");
                            msgPublish.msgPublisher("非日本IP登陆",0,-1);
                        }
                        p=Pattern.compile("認証エラー");
                        m=p.matcher(retVal);
                        if(m.find()){
                            //JOptionPane.showMessageDialog(null, "DMM强制要求修改密码");
                            msgPublish.msgPublisher("DMM强制要求修改密码",0,-1);
                        }
                    }
                    response.getHeaders("Set-Cookie");
                } else {
                    System.err.println(response.getCode()+"no redirection");
                    return false;
                }*/
                //msgPublish.msgPublisher("接口认证通过",0,1);
            } catch (IOException ex) {
            Logger.getLogger(GetStart2.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public boolean NetGame() {
        
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
                msgPublish.msgPublisher("Cookie获取有误",0,-1);
                return false;
            }
            // The underlying HTTP connection is still held by the response object
            // to allow the response content to be streamed directly from the network socket.
            // In order to ensure correct deallocation of system resources
            // the user MUST call CloseableHttpResponse#close() from a finally clause.
            // Please note that if response content is not fully consumed the underlying
            // connection cannot be safely re-used and will be shut down and discarded
            // by the connection manager.
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                System.out.println(response.getCode() + " " + response.getReasonPhrase());
                //final HttpEntity entity = response.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                //EntityUtils.consume(entity);
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
                    //JOptionPane.showMessageDialog(null, "输入的用户密码有误");
                    msgPublish.msgPublisher("输入的用户密码有误",0,-1);
                    return false;
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
            } catch (IOException ex) {
            Logger.getLogger(GetStart2.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
            if(netgame_osapi_url != null){
                //msgPublish.msgPublisher("跳转成功",0,1);
            }else{
                return false;
            }
        return true;
    }
    
    public boolean MakeRequestRefresh() {
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
            //msgPublish.msgPublisher("setcookie",0,0);
            for(Cookie c:cookies){
                cookieStore.addCookie(c);
            }
            //msgPublish.msgPublisher(url,0,0);
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                System.out.println(response.getCode() + " " + response.getReasonPhrase());
                //final HttpEntity entity = response.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                InputStream in=response.getEntity().getContent();
                String retVal = readStream(in);
                //msgPublish.msgPublisher(retVal,0,0);
                retVal = retVal.replace("throw 1; < don't be evil' >", "");
                JSONObject jobj = JSON.parseObject(retVal);
                Set<String> ks = jobj.keySet();
                for(String i:ks){
                   jobj = jobj.getJSONObject(i);
                }
                //MRR_world = worldlist[jobj.getInteger("")-1];
                if(!retVal.contains("成功") && !retVal.contains("\\u6210\\u529f")){
                    msgPublish.msgPublisher("服务器仍在维护中",0,0);
                    sleep(90*1000);
                    return false;
                }
                p=Pattern.compile("api_world_id\\W+(\\d*)");
                m=p.matcher(retVal);
                if(m.find()){
                    MRR_world_id = m.group(1);
                    MRR_world=worldlist[Integer.parseInt(MRR_world_id)-1];
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(GetStart2.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
            Logger.getLogger(GetStart2.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
            //msgPublish.msgPublisher(MRR_world,0,0);
            if(MRR_world != null){
                //msgPublish.msgPublisher("游戏Token获取成功！",0,1); 
            }else{
                return false;
            }
        return true;
    }
    
    public boolean MakeRequest() {
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
                System.out.println(response.getCode() + " " + response.getReasonPhrase());
                //final HttpEntity entity = response.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
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
            } catch (IOException ex) {
            Logger.getLogger(GetStart2.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public boolean Start2() {
        String referer = "http://"+MRR_world+"/kcs/mainD2.swf?api_token="+MR_token+"&api_starttime="+MR_starttime+"/[[DYNAMIC]]/1"; 
        String url="http://"+MRR_world+"/kcsapi/api_start2";
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);
        httpPost.setHeader("Host", netgame_server);  
        httpPost.setHeader("connection", "keep-alive");  
        httpPost.setHeader("Origin", MRR_world); 
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
                System.out.println(response.getCode() + " " + response.getReasonPhrase());
                //final HttpEntity entity = response.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                buffer=new StringBuilder();
                String line;
                InputStream in=response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                while ((line = reader.readLine()) != null) {      
                    buffer.append(line);      
                }  
                System.out.println(buffer.toString());
                //cookies = cookieStore.getCookies();
            } catch (IOException ex) {
            Logger.getLogger(GetStart2.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public boolean getexp(){
        String referer = "http://"+MRR_world+"/kcs/mainD2.swf?api_token="+MR_token+"&api_starttime="+MR_starttime+"/[[DYNAMIC]]/1"; 
        String url="http://"+MRR_world+"/kcsapi/api_req_member/get_practice_enemyinfo";
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);
        httpPost.setHeader("Host", netgame_server);  
        httpPost.setHeader("connection", "keep-alive");  
        httpPost.setHeader("Origin", MRR_world); 
        httpPost.setHeader("X-Requested-With", "ShockwaveFlash/22.0.0.209"); 
        httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) poi/8.1.0-beta.3 Chrome/58.0.3029.110 Electron/1.7.9 Safari/537.36");  
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");  
        httpPost.setHeader("Accept", "*/*"); 
        httpPost.setHeader("DNT", "1");
        httpPost.setHeader("Referer", referer);  
        httpPost.setHeader("Accept-Encoding", "gzip, deflate");  
        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        
        int failure = 0;
        int startno = 10000;
        for(int i = startno; i <999999; i++) {
//        String uid = MRR_world_id + String.format("%06d", i);   //6位数新账户
        String uid = (Integer.valueOf(MRR_world_id)-1) + String.format("%05d", i);   //5位数老账户
        final List<NameValuePair> nvps = new ArrayList<>();
        nvps.add(new BasicNameValuePair("api_token", MR_token));
        nvps.add(new BasicNameValuePair("api_verno", "1"));
        nvps.add(new BasicNameValuePair("api_member_id",  uid));
//        nvps.add(new BasicNameValuePair("api_member_id", "6000000"));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            for(Cookie c:cookies){
                cookieStore.addCookie(c);
            }

            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                //System.out.println(response.getCode() + " " + response.getReasonPhrase());
                System.out.println(uid);
                //final HttpEntity entity = response.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
                buffer=new StringBuilder();
                String line;
                InputStream in=response.getEntity().getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                while ((line = reader.readLine()) != null) {      
                    buffer.append(line);      
                }  
                String jstr = buffer.toString().replace("svdata=", "");
                JSONObject jobj = JSON.parseObject(jstr);
                if(jobj.getInteger("api_result") == 1 ) {
                    userList.add((JSONObject) jobj.clone());
                    failure = 0;
                } else {
                    failure += 1;
                    System.out.println("failure id-" + uid);
                }
                
                if(failure == 5000){
                    System.out.println("failure reached 10000");
                    break;
                }
                if(i != startno && i % 10000 == 0){  //
                    String file = "G:\\KcWiki\\服务器人数统计\\6-トラック泊地\\5位\\"+ i +".json";
                    System.out.print("success reached "+i);
                    RWFile.writeLog(JSON.toJSONString(userList), file);
//                    break;
                }
                
                /*try {
                    sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GetStart2.class.getName()).log(Level.SEVERE, null, ex);
                }*/
                //System.out.println();
                //cookies = cookieStore.getCookies();
            } catch (IOException ex) {
                Logger.getLogger(GetStart2.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        String result = JSON.toJSONString(userList);
        String file = "G:\\KcWiki\\服务器人数统计\\6-トラック泊地\\5位\\"+ "all.json";
        RWFile.writeLog(JSON.toJSONString(userList), file);
        return true;
    
    }
    
    
    public boolean upload() {
        String startdata = buffer.toString();
        if(startdata.contains("svdata")){
            startdata = startdata.substring(startdata.indexOf("svdata=")+66, startdata.length()-1);
        }
        
        try {
                
                try (CloseableHttpClient httpclient = HttpClients.custom().build()) {
                    final List<NameValuePair> nvps = new ArrayList<>();
                    String url="https://acc.kcwiki.org/start2/upload";
                    HttpPost httpPost = new HttpPost(url);
                    httpPost.setHeader("Host", "acc.kcwiki.org");
                    httpPost.setHeader("connection", "keep-alive");
                    httpPost.setHeader("Cache-Control", "max-age=0");
                    httpPost.setHeader("Upgrade-Insecure-Requests", "1");
                    httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.98 Safari/537.36");
                    httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                    httpPost.setHeader("DNT", "1");
                    httpPost.setHeader("Accept-Encoding", "gzip, deflate");
                    httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
                    nvps.add(new BasicNameValuePair("password", MainServer.getAuthorization_uploadstart2()));
                    nvps.add(new BasicNameValuePair("data", startdata));
                    httpPost.setEntity(new UrlEncodedFormEntity(nvps));
                    
                    try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                        InputStream in=response.getEntity().getContent();
                        String retVal = readStream(in);
                        if(retVal.contains("data invalid")){
                            msgPublish.msgPublisher("上传的start2数据不合法！",0,-1);
                        }
                        if(retVal.contains("duplicate start2 data")){
                            msgPublish.msgPublisher("上传的start2数据已经存在。",0,0);
                        }
                        if(retVal.contains("success")){
                            msgPublish.msgPublisher("start2上传成功！",0,1);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(GetStart2.class.getName()).log(Level.SEVERE, null, ex);
                        return false;
                    }
                }
                
                return true;
        } catch (IOException ex) {
                Logger.getLogger(GetStart2.class.getName()).log(Level.SEVERE, null, ex);
                return false;
        }
    }
    
    public static boolean netStart(){
        try {
            GetStart2 getStart2 =new GetStart2();
            worldlist= new String[]{"203.104.209.71","203.104.209.87","125.6.184.16","125.6.187.205","125.6.187.229","203.104.209.134","203.104.209.167","203.104.248.135","125.6.189.7","125.6.189.39","125.6.189.71","125.6.189.103","125.6.189.135","125.6.189.167","125.6.189.215","125.6.189.247","203.104.209.23","203.104.209.39","203.104.209.55","203.104.209.102"};
            int len = worldlist.length;
            /*worldlist = new String[20];
            int i = 0;
            for(String world:MainServer.getWorldList()){
                worldlist[i] = world;
                i++;
            }*/
            if(getStart2.initClient()){
                msgPublish.msgPublisher("初始化http客户端成功",0,0);
            } else {
                msgPublish.msgPublisher("初始化http客户端失败",0,-1);
                return false;
            }
            
            while(true){
                if(getStart2.Login()){
                    msgPublish.msgPublisher("获取dmm_token成功",0,1);
                    break;
                }
            }
            
            while(true){
                if(getStart2.Ajax()){
                    msgPublish.msgPublisher("成功交换数据",0,1);
                    break;
                }
            }
            while(true){
                if(getStart2.Auth()){
                    msgPublish.msgPublisher("接口认证通过",0,1);
                    break;
                }
            }
            while(true){
                if(getStart2.NetGame()){
                    msgPublish.msgPublisher("跳转成功",0,1);
                    break;
                }
            }
            while(true){
                if(getStart2.MakeRequestRefresh()){
                    msgPublish.msgPublisher("游戏Token获取成功！",0,1); 
                    break;
                }
            }
            while(true){
                if(getStart2.MakeRequest()){
                    break;
                }
            }
            while(true){
                if(getStart2.Start2()){
                    break;
                }
            }
            
            new Thread() {  //创建新线程用于下载
                @Override
                public void run() {
                    while(true){
                        if(getStart2.upload()){
                            break;
                        }
                    }
                }
            }.start();
            
            new Start2Analyzer().ReadNewFile(buffer);   //开始进行对比分析
        } catch (IOException ex) {
            Logger.getLogger(GetStart2.class.getName()).log(Level.SEVERE, null, ex);
            msgPublish.msgPublisher("GetStart2发生IOException错误！",0,-1); 
            return false;
        } finally {
            try {
                httpclient.close();
            } catch (IOException ex1) {
                Logger.getLogger(GetStart2.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return true;  
    }
    
    
    public static void main(String args[]){
        //System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.client.protocol.ResponseProcessCookies", "fatal");
        GetStart2 getStart2 = new GetStart2();
        try {
            worldlist= new String[]{"203.104.209.71", "125.6.184.15", "125.6.184.16", "125.6.187.205", "125.6.187.229","125.6.187.253", "125.6.188.25", "203.104.248.135", "125.6.189.7", "125.6.189.39","125.6.189.71", "125.6.189.103", "125.6.189.135", "125.6.189.167", "125.6.189.215","125.6.189.247", "203.104.209.23", "203.104.209.39", "203.104.209.55", "203.104.209.102"};
            netStart();
            /*util = HttpUtil.getInstance();
            worldlist= new String[]{"203.104.209.71", "125.6.184.15", "125.6.184.16", "125.6.187.205", "125.6.187.229","125.6.187.253", "125.6.188.25", "203.104.248.135", "125.6.189.7", "125.6.189.39","125.6.189.71", "125.6.189.103", "125.6.189.135", "125.6.189.167", "125.6.189.215","125.6.189.247", "203.104.209.23", "203.104.209.39", "203.104.209.55", "203.104.209.102"};
            getStart2.Login("https://www.dmm.com/my/-/login/=/path=Sg9VTQFXDFcXFl5bWlcKGExKUVdUXgFNEU0KSVMVR28MBQ0BUwJZBwxK/");
            //getStart2.Login("https://accounts.dmm.com/service/login/password/=/path=Sg9VTQFXDFcXFl5bWlcKGExKUVdUXgFNEU0KSVMVR28MBQ0BUwJZBwxK");
            //getStart2.Ajax("https://accounts.dmm.com/service/login/password/authenticate");
            getStart2.getToken();
            getStart2.Auth("https://accounts.dmm.com/service/login/password/authenticate");
            getStart2.Netgame("http://www.dmm.com/netgame/social/-/gadgets/=/app_id=854854/");
            getStart2.MakeRequestRefresh();
            getStart2.MakeRequest();
            getStart2.Start2();*/
            //getStart2.Login();
            //getStart2.upload();
        } catch (Exception ex) {
            Logger.getLogger(GetStart2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the isStop
     */
    public static boolean isIsStop() {
        return isStop;
    }

    /**
     * @param aIsStop the isStop to set
     */
    public static void setIsStop(boolean aIsStop) {
        isStop = aIsStop;
    }
    
    
}
