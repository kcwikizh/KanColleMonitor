
package org.kcwiki.httpclient;

import org.kcwiki.exception.CustomedException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kcwiki.exception.CodeDict;
import moe.kcwiki.handler.massage.msgPublish;
import static org.kcwiki.tools.constant.constant.FILESEPARATOR;
import static org.kcwiki.tools.constant.constant.LINESEPARATOR;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpHead;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
        
public class HttpUtils {
    
    public static boolean checkModified(String url, String gtmdate,CloseableHttpClient httpclient, RequestConfig config, DateFormat df) throws CustomedException {

        HttpHead httpHead = DefaultMethod.getDefaultHeadMethod(url, config);
        try{  
            httpHead.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");  
//            httpHead.setHeader("If-Modified-Since", "Sun, 19 Nov 2017 14:04:58 GMT");
            httpHead.setHeader("If-Modified-Since", gtmdate);
            try (CloseableHttpResponse response = httpclient.execute(httpHead)) {
                int rspCode = response.getCode();
                checkResponseCode(rspCode);
                
                long lastmodified = 0L;
                if (!response.containsHeader("Last-Modified") || (lastmodified = df.parse(response.getFirstHeader("Last-Modified").getValue()).getTime()) == 0)
                    throw new CustomedException(CodeDict.HTTP_NonLastModified, "尝试获取以下url时发生错误：" + LINESEPARATOR + url ,(new Throwable()).getStackTrace());
                
                long diffTime = df.parse(gtmdate).getTime() - lastmodified;
                return rspCode == 200 && diffTime < 0;
            } catch (ParseException ex) {
                Logger.getLogger(HttpUtils.class.getName()).log(Level.SEVERE, null, ex);
                throw new CustomedException(CodeDict.Exception_Runtime, "null" ,(new Throwable()).getStackTrace());
            }
            
        } catch (CustomedException ex) {
            throw ex;
        } catch (UnknownHostException e) {
            msgPublish.msgPublisher("HttpUtils_AHC"+"checkModified时端口出错,请检测网络连接。",0,-1);
        } catch (ConnectException e){
            msgPublish.msgPublisher("HttpUtils_AHC"+"checkModified时文件连接超时。",0,-1);
        } catch (IOException e){
            msgPublish.msgPublisher("HttpUtils_AHC"+"checkModified时文件时发生IOException错误。",0,-1);  
        }  
        throw new CustomedException(CodeDict.Exception_Runtime, "尝试获取以下url时发生Non UnknownHostException错误：" + LINESEPARATOR + url ,(new Throwable()).getStackTrace());
    }
    
    public static boolean checkModified(String url, long timestamp) throws CustomedException {
        HttpURLConnection conn = null;
        try {
            URL _url = new URL(url);
            conn = (HttpURLConnection)_url.openConnection();
            conn.setIfModifiedSince(timestamp);
            conn.connect();
            int rspCode = conn.getResponseCode();
            checkResponseCode(rspCode);
            
            conn.disconnect();
            long lastmodified = 0L;
            if (conn.getHeaderField("Last-Modified") == null || (lastmodified = conn.getLastModified()) == 0)
                throw new CustomedException(CodeDict.HTTP_NonLastModified, "尝试获取以下url时发生错误：" + LINESEPARATOR + url ,(new Throwable()).getStackTrace());
                
            long diffTime = timestamp - lastmodified;
            return rspCode == 200 && diffTime < 0;
        } catch (CustomedException ex) {
            throw ex;
        } catch (MalformedURLException ex) {
            msgPublish.msgPublisher("HttpUtils_SHC"+"checkModified时端口出错,请检测网络连接。",0,-1);
        } catch (IOException ex) {
            msgPublish.msgPublisher("HttpUtils_SHC"+"checkModified时端口出错,请检测网络连接。",0,-1);
        } finally {
            if (conn != null)
                conn.disconnect();
        }
        throw new CustomedException(CodeDict.Exception_Runtime, "尝试获取以下url时发生Non UnknownHostException错误：" + LINESEPARATOR + url ,(new Throwable()).getStackTrace());
    }

    
    public static boolean downloadFile(String url, String filefolder, String filename, RequestConfig config) throws CustomedException {
        
        HttpGet httpGet = DefaultMethod.getDefaultGetMethod(url, config);
        try{
            httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");  
            if(!new File(filefolder).exists())
                new File(filefolder).mkdirs();
            final RandomAccessFile file = new RandomAccessFile(filefolder + FILESEPARATOR + filename, "rw");
            
            try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
                try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                    if (300 >= response.getCode()) {
                        HttpEntity en = response.getEntity();
                        try (InputStream in = en.getContent()) {
                            byte[] buf = new byte[1024];
                            int len = -1;

                            while (-1 != (len = in.read(buf))) {
                                file.write(buf, 0, len);
                            }
                            file.close();
                        }
                    }
                }
            }
            
            return true;
        }catch(UnknownHostException e){
            msgPublish.msgPublisher("HttpUtils"+"下载端口出错,请检测网络连接。",0,-1);
            throw new CustomedException(CodeDict.Exception_UnknownHost, "尝试获取以下url时发生UnknownHostException错误：" + LINESEPARATOR + url ,(new Throwable()).getStackTrace());
        }catch(ConnectException e){
            msgPublish.msgPublisher("HttpUtils"+"下载文件连接超时。",0,-1);
        }catch(IOException e){
            msgPublish.msgPublisher("HttpUtils"+"下载文件时发生IOException错误。",0,-1);  
        }  
        throw new CustomedException(CodeDict.Exception_Runtime, "尝试获取以下url时发生Non UnknownHostException错误：" + LINESEPARATOR + url ,(new Throwable()).getStackTrace());
    }
    
    public static void checkResponseCode(int code) throws CustomedException {
        switch (code){
            case 403:
                throw new CustomedException(CodeDict.HTTP_Forbidden, "null" ,(new Throwable()).getStackTrace());
//            case 404:
//                throw new CustomedException(CodeDict.HTTP_Not_Found, "null" ,(new Throwable()).getStackTrace());
            case 500:
                throw new CustomedException(CodeDict.HTTP_Internal_Server_Error, "null" ,(new Throwable()).getStackTrace());
            case 503:
                throw new CustomedException(CodeDict.HTTP_Service_Unavailable, "null" ,(new Throwable()).getStackTrace());
        }
    }
    
    public static void main(String[] args) {
        long ts = new Date().getTime();
//        ts = 1511100298000L;
        DateFormat sdf  = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = sdf.format(ts);
//        System.out.println(date);
        checkModified("http://ooi.moe/kcs/sound/kc9999/429.mp3", date, HttpClients.createDefault(), Config.makeProxyConfig(false), sdf);
        checkModified("http://ooi.moe/kcs/sound/kc9999/429.mp3", ts);
    }
}
