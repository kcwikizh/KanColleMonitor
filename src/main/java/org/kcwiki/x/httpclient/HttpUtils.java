
package org.kcwiki.x.httpclient;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import com.google.common.io.CharStreams;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpHead;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.kcwiki.x.exception.ExceptionBase;
import static org.kcwiki.x.tools.ConstantValue.FILESEPARATOR;
import org.kcwiki.x.types.MsgTypes;
import org.slf4j.LoggerFactory;
import org.kcwiki.x.types.ServiceTypes;
        
public class HttpUtils {
    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(HttpUtils.class);

    public boolean checkModified(String url, String gtmdate,CloseableHttpClient httpclient, RequestConfig config, DateFormat df) throws ExceptionBase {

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
                    throw new ExceptionBase(ServiceTypes.HttpClient, String.format("尝试获取%s时发生错误。", url));
                
                long diffTime = df.parse(gtmdate).getTime() - lastmodified;
                return rspCode == 200 && diffTime < 0;
            } catch (ParseException ex) {
                LOG.error("");;
            }
        } catch (UnknownHostException e) {
            LOG.error("HttpUtils_AHC"+"checkModified时端口出错,请检测网络连接。");
        } catch (ConnectException e){
            LOG.error("HttpUtils_AHC"+"checkModified时文件连接超时。");
        } catch (IOException e){
            LOG.error("HttpUtils_AHC"+"checkModified时文件时发生IOException错误。");  
        }
        throw new ExceptionBase(ServiceTypes.HttpClient, String.format("尝试获取%s时发生错误。", url));
    }
    
    public boolean checkModified(String url, long timestamp) throws ExceptionBase {
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
                throw new ExceptionBase(ServiceTypes.HttpClient, String.format("尝试获取%s时发生错误。", url));
                
            long diffTime = timestamp - lastmodified;
            return rspCode == 200 && diffTime < 0;
        } catch (MalformedURLException ex) {
            LOG.error("HttpUtils_SHC"+"checkModified时端口出错,请检测网络连接。");
        } catch (IOException ex) {
            LOG.error("HttpUtils_SHC"+"checkModified时端口出错,请检测网络连接。");
        } finally {
            if (conn != null)
                conn.disconnect();
        }
        throw new ExceptionBase(ServiceTypes.HttpClient, String.format("尝试获取%s时发生错误。", url));
    }

    public boolean downloadFile(String url, String filefolder, String filename, RequestConfig config) throws ExceptionBase {

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
            LOG.error("HttpUtils"+"下载端口出错,请检测网络连接。");
            throw new ExceptionBase(ServiceTypes.HttpClient, String.format("尝试获取%s时发生错误。", url));
        }catch(ConnectException e){
            LOG.error("HttpUtils"+"下载文件连接超时。");
        }catch(IOException e){
            LOG.error("HttpUtils"+"下载文件时发生IOException错误。");  
        }  
        throw new ExceptionBase(ServiceTypes.HttpClient, String.format("尝试获取%s时发生错误。", url));
    }
    
    public String getHttpBody (String url, RequestConfig config) throws ExceptionBase {
        HttpGet httpGet = DefaultMethod.getDefaultGetMethod(url, config);
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                if (300 >= response.getCode()) {
                    HttpEntity entity = response.getEntity();
                    String text ;
                    try (final Reader reader = new InputStreamReader(entity.getContent())) {
                        text = CharStreams.toString(reader);
                    }
                    return text;
                }
            } catch (IOException ex) {
                LOG.error("HttpUtils访问{}时发生IOException错误。", url);  
            }
        }catch(UnknownHostException e){
            LOG.error("HttpUtils"+"下载端口出错,请检测网络连接。");
            throw new ExceptionBase(ServiceTypes.HttpClient, String.format("尝试获取%s时发生错误。", url));
        }catch(ConnectException e){
            LOG.error("HttpUtils"+"下载文件连接超时。");
        }catch(IOException e){
            LOG.error("HttpUtils"+"下载文件时发生IOException错误。");  
        }  
        throw new ExceptionBase(ServiceTypes.HttpClient, String.format("尝试获取%s时发生错误。", url));
    }
    
    public void checkResponseCode(int code) throws ExceptionBase {
        switch (code){
            case 403:
                throw new ExceptionBase(ServiceTypes.KanColleServer, MsgTypes.ERROR, "403");
            case 500:
                throw new ExceptionBase(ServiceTypes.KanColleServer, MsgTypes.ERROR, "500");
            case 503:
                throw new ExceptionBase(ServiceTypes.KanColleServer, MsgTypes.ERROR, "503");
        }
    }

}
