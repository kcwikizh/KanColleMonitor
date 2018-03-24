/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kcwiki.monitor.file;

import org.kcwiki.initializer.MainServer;

import org.kcwiki.initializer.GetModifiedDataThread;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import static java.lang.Thread.sleep;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kcwiki.exception.CodeDict;
import static org.kcwiki.initializer.MainServer.isStopScanner;
import moe.kcwiki.handler.massage.msgPublish;
import org.kcwiki.handler.thread.modifieddataPool;
import org.kcwiki.httpclient.Config;
import org.kcwiki.exception.CustomedException;
import org.kcwiki.httpclient.HttpUtils;
import org.kcwiki.tools.compressor.ZipCompressor;
import org.kcwiki.tools.constant.constant;
import static org.kcwiki.tools.constant.constant.FILESEPARATOR;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;

/**
 *
 * @author VEP
 */
public class GetLastModifiedData {
    //private final java.util.ArrayList<String>  addressList;
    //private final java.util.ArrayList<String>  dataList;
    private static JSONArray modifidedData;
    private final String rootFolder = MainServer.getTempFolder() + File.separator+"newSlotItem";
    //String[] serverlistaddress= new String[]{"203.104.209.71", "203.104.209.87", "125.6.184.16", "125.6.187.205", "125.6.187.229","125.6.187.253", "125.6.188.25", "203.104.248.135", "125.6.189.7", "125.6.189.39","125.6.189.71", "125.6.189.103", "125.6.189.135", "125.6.189.167", "125.6.189.215","125.6.189.247", "203.104.209.23", "203.104.209.39", "203.104.209.55", "203.104.209.102"};
    private static ArrayList<String> serverlistaddress = (ArrayList<String>) MainServer.getWorldList().clone();
    private static String[] serverlistname = new String[]{"横须贺镇守府","呉镇守府","佐世保镇守府","舞鹤镇守府","大凑警备府","トラック泊地","リンガ泊地","ラバウル基地","ショートランド泊地","ブイン基地","タウイタウイ泊地","パラオ泊地","ブルネイ泊地","単冠湾泊地","幌筵泊地","宿毛湾泊地","鹿屋基地","岩川基地","佐伯湾泊地","柱岛泊地"};
    private static String serveraddress  = "203.104.209.102";
    private static String servername = "203.104.209.102";
    private static int servernum=0;
    private static boolean isEvent = MainServer.isEventMode();
    private final RequestConfig hcconfig = Config.makeProxyConfig(false);
    private final DateFormat df = Config.GTMDateFormatter();   
    
    
    public boolean doMonitor() {
        GetModifiedDataThread.addJob();
        String line;
        String str = "";
        String ipFile=MainServer.getDataFolder()+File.separator+"filedata-lite.json";
        if(!new File(ipFile).exists()){
            msgPublish.msgPublisher("找不到"+serveraddress+"的扫描文件，请向技术组索取。",0,-1);
            return false;
        }
        try (BufferedReader Ibfr = new BufferedReader(new InputStreamReader(new FileInputStream(ipFile), "UTF-8"))) {
            while ((line=Ibfr.readLine())!=null) {
                str=str+line;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GetLastModifiedData.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GetLastModifiedData.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(GetLastModifiedData.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        modifidedData=JSON.parseArray(str);
        
        final int taskID = modifieddataPool.getTaskNum();
            modifieddataPool.addTask(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int countno=1;
                while(!isStopScanner()){
                    msgPublish.msgPublisher("开始第"+countno+"轮文件扫描。",0,0);
                    if(getNewData(modifidedData)) {
                        break;
                    }
                    countno++;
                    try {
                        if(isStopScanner()){
                            break;
                        }
                        msgPublish.msgPublisher("文件扫描线程进入等待阶段",0,0);
                        sleep(90*1000);
                    } catch (InterruptedException ex) {
                        //msgPublish.msgPublisher("moe.kcwiki.getmodifieddata.GetLastModifiedData-doMonitor-InterruptedException",0,0);
                        Logger.getLogger(GetLastModifiedData.class.getName()).log(Level.SEVERE, null, ex);
                        msgPublish.msgPublisher("文件扫描线程已强制退出。",0,0);
                        return taskID;
                    }
                }
                msgPublish.msgPublisher("文件扫描线程已停止。",0,0);
                long date = MainServer.getDate();
                String tempZipFolder = MainServer.getPublishFolder()+FILESEPARATOR+date;
                ZipCompressor.createZip(MainServer.getDownloadFolder()+FILESEPARATOR+"kcs", tempZipFolder, date+"-gamefiles.zip");
                if(!MainServer.isDebugMode() && !isStopScanner()){
                    File Persistence = new File(MainServer.getMuseumFolder()+FILESEPARATOR+"GameCore");
                    File tmp = new File(tempZipFolder+FILESEPARATOR+date+"-gamefiles.zip");
                    if(!Persistence.exists())
                        Persistence.mkdirs();
                    if(tmp.exists()){
                        msgPublish.msgPublisher("准备拷贝文件："+tmp.getAbsolutePath()+"\t"+Persistence,0,0);
                        FileUtils.copyFileToDirectory(tmp, Persistence);
                    }
                }
                return taskID;
            }
        },taskID,"moe.kcwiki.getmodifieddata-GetLastModifiedData-doMonitor");
        return true;
    }

    public boolean getNewData(JSONArray shipdata) {
         //msgPublish.msgPublisher("shipdata: "+JSON.toJSONString(shipdata),0,0); 
         boolean isFinish = false;
                Iterator iterator=shipdata.iterator();
                CloseableHttpClient chc = HttpClients.createDefault();
                while(iterator.hasNext()){
                    if(isStopScanner()){
                        break;
                    }
                    JSONObject object=(JSONObject)iterator.next();
                        int count=0;
                        while(count<2){
                            String url;
                            url="http://"+serveraddress;
                            //url=MainServer.getKcwikiServerAddress();
                            //url=MainServer.getKcwikiServerAddress();
                            String path = object.getString("path");
                            if (getMDD(url+path,Long.parseLong(object.getString("timestamp")),-1,serveraddress,object.getString("hash"),chc)) {
                                if(path.toLowerCase().contains("core")){
                                   isFinish = true; 
                                }
                                break;
                            }
                            count++;
                        }
                    
                    //msgPublish.msgPublisher("ID:"+shipid+"扫描完毕。",0);
                }

        return isFinish;
    }

    public boolean getMDD(String URL, Long fliestamp, int nameNo, String serveraddress, String HashHex, CloseableHttpClient chc) throws CustomedException{
        int i=URL.lastIndexOf("/"); //取得子串的初始位置
        String filename;
        if(nameNo==-1){
            filename=URL.substring(i+1,URL.length());
        }else{
            filename=org.kcwiki.tools.swfunpacker.Server.shipvoicerule.get(String.valueOf(nameNo));
        }
        String sourcepath=URL.substring(0, i);
        for(int count=0;count<3;count++){
            i=sourcepath.indexOf("/");
            sourcepath=sourcepath.substring(i+1, sourcepath.length());  
        }
        String filepath=MainServer.getDownloadFolder()+File.separator+sourcepath;
        
        String url  = URL+ "?VERSION=" + System.currentTimeMillis();
        String timestamp = df.format(fliestamp);
        if(!HttpUtils.checkModified(url, timestamp, chc, hcconfig, df))
            return false;
        if  (!(new File(filepath).exists())||!(new File(filepath).isDirectory())) {
            new File(filepath) .mkdirs();  
        }
        boolean success = false;
        try{
            success = HttpUtils.downloadFile(url, filepath, filename, hcconfig);
        } catch (CustomedException ex) {
            if (ex.getErrorCode() == CodeDict.Exception_UnknownHost) {
                msgPublish.msgPublisher("GetLastModifiedData 核心下载线程发生重大错误，已终止下载进程。",0,-1);
                throw ex;
            }
        }
        if(success){
            String hashhex = getMD5Checksum(filepath+FILESEPARATOR+filename);
            //msgPublish.msgPublisher(filename+"\t"+urlcon.getLastModified(),0,0);
            //msgPublish.msgPublisher(filename+"\t"+flie,0,0);
            //msgPublish.msgPublisher(filename+"\t"+hashhex,0,0);
            if(hashhex.equals(HashHex)){
                return false;
            }
            msgPublish.msgPublisher(filename+"\t下载完成",0,0);
            if(URL.contains(".swf")) {
                new org.kcwiki.tools.swfunpacker.Controller().Analysis(filename,filepath,sourcepath);   
            }
            return true;
        }
        return false;
    }
    
    public static String getMD5Checksum(String filename) {
        String md5 = "";
        try (FileInputStream fis = new FileInputStream(filename)) {
            md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GetLastModifiedData.class.getName()).log(Level.SEVERE, null, ex);
            msgPublish.msgPublisher("modifiedCheck-getMD5Checksum模块读写时发生FileNotFoundException错误。",0,-1); 
        } catch (IOException ex) {
            Logger.getLogger(GetLastModifiedData.class.getName()).log(Level.SEVERE, null, ex);
            msgPublish.msgPublisher("modifiedCheck-getMD5Checksum模块读写时发生IOException错误。",0,-1); 
        }
        return md5;
    }
    
    private void serverAddress(){
        if(servernum >= serverlistaddress.size()-1){
            servernum=0;
        }
        if(serverlistaddress.isEmpty()){
            serveraddress = "203.104.209.102";
        }else{
            serveraddress=serverlistaddress.get(servernum);
            servername=serverlistname[servernum]+"-"+serverlistaddress.get(servernum);
            servernum++;
        }
        if(servernum >= serverlistaddress.size()){
            servernum=0;
        }
    }
    
}
