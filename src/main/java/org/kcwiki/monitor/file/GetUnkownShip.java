/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kcwiki.monitor.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import static java.lang.Thread.sleep;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.kcwiki.initializer.MainServer;
import static org.kcwiki.initializer.MainServer.isStopScanner;
import moe.kcwiki.handler.massage.msgPublish;
import org.kcwiki.handler.thread.getUnkownShipPool;
import org.kcwiki.tools.GetHash;
import org.kcwiki.tools.constant.constant;
import org.kcwiki.tools.Encoder;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.kcwiki.exception.CodeDict;
import org.kcwiki.exception.CustomedException;
import org.kcwiki.httpclient.Config;
import org.kcwiki.httpclient.HttpUtils;
import static org.kcwiki.tools.constant.constant.FILESEPARATOR;

/**
 *
 * @author Administrator
 */
public class GetUnkownShip {
    private final String unknowShipFile;
    private static String intputFile;
    private final String localoldstart2;
    private final String proxyhost;
    private final int proxyport;
    private static String serveraddress = "203.104.209.102";
    private static String servername;
    private static int servernum=0;
    private static boolean isEvent = MainServer.isEventMode();
    public static LinkedHashMap<String, String> unknowShipList = new LinkedHashMap<>(); 
    private final static Set<String> md5DataSet = new HashSet<>();
    private final static List<JSONObject> md5DataList = new ArrayList<>();
    private static int blockCount = 30;
    //private final static LinkedHashMap<String,JSONObject> md5DataMap = new LinkedHashMap<>();
    private static ArrayList<String> serverlistaddress = MainServer.getWorldList();
    private static String[] serverlistname = new String[]{"横须贺镇守府","呉镇守府","佐世保镇守府","舞鹤镇守府","大凑警备府","トラック泊地","リンガ泊地","ラバウル基地","ショートランド泊地","ブイン基地","タウイタウイ泊地","パラオ泊地","ブルネイ泊地","単冠湾泊地","幌筵泊地","宿毛湾泊地","鹿屋基地","岩川基地","佐伯湾泊地","柱岛泊地"};
//    private GetHash Hash = new GetHash();
    private final String rootFolder = MainServer.getTempFolder()+File.separator+"newShip";
    private final Random random = new Random();
    private final DateFormat df = Config.GTMDateFormatter();   
    //private final java.util.ArrayList<String>  dataList;
    
    
    public GetUnkownShip(){
        //this.localpath = "L:\\NetBeans\\NetBeansProjects\\moe kcwiki stdunpacktools\\build";
        this.localoldstart2 = org.kcwiki.initializer.MainServer.getLocaloldstart2data();
        //this.localoldstart2 = MainServer.getDataPath()+File.separator+"oldstart2.json";
        this.proxyhost = org.kcwiki.initializer.MainServer.getProxyhost();
        this.proxyport = org.kcwiki.initializer.MainServer.getProxyport();
        unknowShipFile = MainServer.getDataFolder()+File.separator+"unknowShip";
        
//        unknowShipFile = "L:\\NetBeans\\NetBeansProjects\\KcWikiOnline\\data\\unknowShip";
        //dataList = new  java.util.ArrayList<>();
        //serverAddress();
    }
        
    public boolean loadKnewFile(){
        String line;
        String str = "";
        String ipFile=MainServer.getDataFolder()+File.separator+"shipgraphdata.json";
        if(!new File(ipFile).exists()){
            msgPublish.msgPublisher("找不到Md5的扫描文件，请向技术组索取。",0,-1);
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
        JSONArray md5Data=JSON.parseArray(str);
        Iterator iterator=md5Data.iterator();
        md5DataSet.add(null);
                while(iterator.hasNext()){
                    JSONObject object=(JSONObject)iterator.next();
                    md5DataList.add(object);
                    md5DataSet.add(object.getString("hash"));
                }
        return true;
    }
    
    public boolean getAllData(){
        StringBuilder buffer = null;
//        File file=new File("L:\\NetBeans\\NetBeansProjects\\KcWikiOnline\\data\\"+".txt");
        File file=new File(unknowShipFile+".txt");
        if(file.exists()){
            try (BufferedReader nBfr = new BufferedReader(new InputStreamReader(new FileInputStream(unknowShipFile+".txt"), Encoder.codeString(unknowShipFile+".txt")))) {
                String line;
                while ((line=nBfr.readLine())!=null) {
                    if(line.contains("\t")){
                        String[] data=line.split("\t");
                        unknowShipList.put(data[0], data[1]);
                    }
                }
            } catch (FileNotFoundException ex) {
                msgPublish.msgPublisher("moe.kcwiki.getmodifieddata-GetUnkownShip-getAllData:FileNotFoundException",0,-1);
                Logger.getLogger(GetUnkownShip.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } catch (IOException ex) {
                msgPublish.msgPublisher("moe.kcwiki.getmodifieddata-GetUnkownShip-getAllData:IOException",0,-1);
                Logger.getLogger(GetUnkownShip.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } catch (Exception ex) {
                msgPublish.msgPublisher("moe.kcwiki.getmodifieddata-GetUnkownShip-getAllData:Exception",0,-1);
                Logger.getLogger(GetUnkownShip.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
            //file.renameTo(new File(unknowShipFile+" backup.txt"));
        }
        
        try (BufferedReader nBfr = new BufferedReader(new InputStreamReader(new FileInputStream(intputFile), Encoder.codeString(intputFile)))) {
            String line;
            buffer = new StringBuilder();
            while ((line=nBfr.readLine())!=null) {
                buffer.append(line);
            }
        }catch (FileNotFoundException ex) {
                msgPublish.msgPublisher("moe.kcwiki.getmodifieddata-GetUnkownShip-getAllData:FileNotFoundException",0,-1);
                Logger.getLogger(GetUnkownShip.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } catch (IOException ex) {
                msgPublish.msgPublisher("moe.kcwiki.getmodifieddata-GetUnkownShip-getAllData:IOException",0,-1);
                Logger.getLogger(GetUnkownShip.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } catch (Exception ex) {
                msgPublish.msgPublisher("moe.kcwiki.getmodifieddata-GetUnkownShip-getAllData:Exception",0,-1);
                Logger.getLogger(GetUnkownShip.class.getName()).log(Level.SEVERE, null, ex);
                return false;
        }
        
        /*
        JSONObject Data=JSON.parseObject(buffer.toString());
        JSONArray newData=Data.getJSONArray("api_mst_ship");
        Iterator iterator=newData.iterator();
        while(iterator.hasNext()){
            JSONObject newObject=(JSONObject)iterator.next();
            if(unknowShipList.containsKey(newObject.getString("api_id"))){
                if(!unknowShipList.get(newObject.getString("api_id")).equals(newObject.getString("api_name"))){
                    int num=1;
                    String newkey=newObject.getString("api_id")+"-"+String.valueOf(num);
                    while(true){
                        if(!unknowShipList.containsKey(newkey)){
                            unknowShipList.put(newkey, newObject.getString("api_name"));
                            break;
                        }
                        num++;
                        newkey=newObject.getString("api_id")+"-"+String.valueOf(num);
                    }
                }
            }else{
                unknowShipList.put(newObject.getString("api_id"), newObject.getString("api_name"));
            }
            
        }
        */
        
        JSONObject Data=JSON.parseObject(buffer.toString());
        JSONArray newData=Data.getJSONArray("api_mst_shipgraph");
        Iterator iterator=newData.iterator();
        while(iterator.hasNext()){
            JSONObject newObject=(JSONObject)iterator.next();
            if(unknowShipList.containsKey(newObject.getString("api_id"))){
                if(!unknowShipList.get(newObject.getString("api_id")).equals(newObject.getString("api_filename"))){
                    int num=1;
                    String newkey=newObject.getString("api_id")+"-"+String.valueOf(num);
                    while(true){
                        if(!unknowShipList.containsKey(newkey)){
                            unknowShipList.put(newkey, newObject.getString("api_filename"));
                            break;
                        }
                        num++;
                        newkey=newObject.getString("api_id")+"-"+String.valueOf(num);
                    }
                }
            }else{
                unknowShipList.put(newObject.getString("api_id"), newObject.getString("api_filename"));
            }
            
        }
        
        try (BufferedWriter eBfw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(unknowShipFile+".txt")), "UTF-8"))) {
            for (Map.Entry<String,String> ship : unknowShipList.entrySet()) {
                eBfw.write(ship.getKey()+"\t"+ship.getValue()+constant.LINESEPARATOR);
            }
        } catch (FileNotFoundException ex) {
                msgPublish.msgPublisher("moe.kcwiki.getmodifieddata-GetUnkownShip-getAllData:FileNotFoundException",0,-1);
                Logger.getLogger(GetUnkownShip.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } catch (IOException ex) {
                msgPublish.msgPublisher("moe.kcwiki.getmodifieddata-GetUnkownShip-getAllData:IOException",0,-1);
                Logger.getLogger(GetUnkownShip.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            } catch (Exception ex) {
                msgPublish.msgPublisher("moe.kcwiki.getmodifieddata-GetUnkownShip-getAllData:Exception",0,-1);
                Logger.getLogger(GetUnkownShip.class.getName()).log(Level.SEVERE, null, ex);
                return false;
        }
        return true;
    }

    public void getUnknowData(List<JSONObject> md5DataList,int blockno){
        /*if(MainServer.isDebugMode()){
            return;
        }*/
        
        final int taskID = getUnkownShipPool.getTaskNum();
        getUnkownShipPool.addTask(new Callable<Integer>() {
                @Override
                public Integer call() {
                        GetUnkownShip rename=new GetUnkownShip();
                        RequestConfig hcconfig = Config.makeProxyConfig(false);
                        CloseableHttpClient chc = HttpClients.createDefault();
                        while(md5DataList!=null){
                            Iterator<JSONObject> it = md5DataList.iterator();   
                            String url;
                            while(it.hasNext()) {  
                                if(isStopScanner() && taskID == blockCount - 2){
                                    msgPublish.msgPublisher("新立绘扫描线程已停止",0,0);
                                    return taskID;
                                } else if (isStopScanner()){
                                    return taskID;
                                }
                                JSONObject data = it.next(); 
                                if(Integer.valueOf(data.getString("id")) >= 1700){
                                    continue;
                                }
                                if(!MainServer.isDebugMode() && isEvent){
                                    url="http://"+serveraddress+"/kcs/";
                                }else{
                                    url=MainServer.getKcwikiServerAddress();
                                }
//                                url="http://203.104.209.102/kcs/";
                                try {
//                                    if (getMDD("http://"+serveraddress+"/kcs/"+data.getString("path"),data.getLong("timestamp"),data.getString("hash"))) {
                                    if (getMDD(url+data.getString("path"), data.getLong("timestamp"), data.getString("hash"), chc, hcconfig)) {
                                        it.remove();
                                        String filename=data.getString("filename");
                                        if(!new File(rootFolder+File.separator+filename+".swf").exists()){
                                            continue;
                                        }
                                        String newHash = new GetHash().getNewHash(rootFolder+File.separator+filename+".swf");
                                        if(md5DataSet.contains(newHash)){
                                            continue;
                                        }
                                        MainServer.getNewHashMap().put(data.getString("id"), newHash);
                                        //msgPublish.msgPublisher("立绘文件： "+data.getString("id")+"\t"+"http://"+serveraddress+"/kcs/"+data.getString("path"),0,0);
                                        //msgPublish.msgPublisher("立绘HASH： "+Hash.getNewHash(rootFolder+File.separator+filename+".swf"),0,0);
                                        rename.renameShipSwf(filename);
                                    } else {
                                        serverAddress();
                                    }
                                } catch (Exception ex) {
                                    Logger.getLogger(GetUnkownShip.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } 
                            if(md5DataList.isEmpty()){
                                msgPublish.msgPublisher("新立绘扫描线程:Thread-"+taskID+"结束",0,0);
                                return taskID;
                            }
                            try{
                                sleep(90*1000);
                            } catch (InterruptedException ex){
                                Logger.getLogger(GetUnknowSlotitem.class.getName()).log(Level.SEVERE, null, ex);
                                msgPublish.msgPublisher("新立绘扫描线程:Thread-"+taskID+"已强制退出。",0,0);
                                return taskID;
                            }
                            if(isStopScanner() && taskID == blockCount - 2){
                                msgPublish.msgPublisher("新立绘扫描线程已停止",0,0);
                                return taskID;
                            }
                            if(taskID == blockCount - 2){
                                msgPublish.msgPublisher("开始下一轮新立绘扫描",0,0);
                            }
                            
                        }
                    
                    return taskID;
                }
            },taskID,"GetUnkownShip-getUnknowData Thread-"+taskID);
    }
    
        
    public boolean setterSplitter(){
        unknowShipList.clear();
        
        if(!loadKnewFile()){
            return false;
        }
            ArrayList<JSONObject> tempList = new ArrayList();
            
            int blockSize= (int) Math.ceil(md5DataList.size()/(double) blockCount);
            int countno=0;
            int sum=0;
            int blockno=1;
            for(JSONObject ship:md5DataList){
                tempList.add(ship);
                countno++;
                sum++;
                if(countno==blockSize){
                    getUnknowData((List<JSONObject>) tempList.clone(),blockno);
                    tempList.clear();
                    countno=0;
                    blockno++;
                    continue;
                }
                if(sum==md5DataList.size()){
                    getUnknowData((List<JSONObject>) tempList.clone(),blockno);
                    tempList.clear();
                    break;
                }
            }
            msgPublish.msgPublisher("开始新舰娘扫描。",0,0);
            return true;
    }
    
    //synchronized
    public boolean getMDD(String URL,Long timestamp,String HashHex, CloseableHttpClient chc, RequestConfig hcconfig) throws CustomedException{
        int i=URL.lastIndexOf("/"); //取得子串的初始位置
        String filename = URL.substring(i+1,URL.length());
        String filepath = rootFolder+File.separator+filename;
 
        String url  = URL + "?VERSION=" + System.currentTimeMillis();
        if(!HttpUtils.checkModified(url, filepath, chc, hcconfig, df))
            return false;
        
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
            String hashhex = getMD5Checksum(filepath);
            /*String hashhex = getMD5Checksum(rootFolder+File.separator+filename);
                    msgPublish.msgPublisher(filename+"\t"+urlcon.getLastModified(),0,0);
                    msgPublish.msgPublisher(filename+"\t"+timestamp,0,0);
                    msgPublish.msgPublisher(filename+"\t"+hashhex,0,0);
                    if(hashhex.equals(HashHex)){
                        return false;
            }*/
            return true;
        }    
        //serverAddress();
        return false;
    }
    
    private static void serverAddress(){
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
    
    public static String getMD5Checksum(String filename) {
        String md5 = "";
        try (FileInputStream fis = new FileInputStream(filename)) {
            md5 = DigestUtils.md5Hex(IOUtils.toByteArray(fis));
            IOUtils.closeQuietly(fis);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GetLastModifiedData.class.getName()).log(Level.SEVERE, null, ex);
            msgPublish.msgPublisher("getUnkownShipPool-getMD5Checksum模块读写时发生FileNotFoundException错误。",0,-1); 
        } catch (IOException ex) {
            Logger.getLogger(GetLastModifiedData.class.getName()).log(Level.SEVERE, null, ex);
            msgPublish.msgPublisher("getUnkownShipPool-getMD5Checksum模块读写时发生IOException错误。",0,-1); 
        }
        return md5;
    }
    
    public boolean renameShipSwf(String shipname){
        
        String publishFolder=MainServer.getPublishFolder()+File.separator+"newShip"+File.separator+shipname;
        String shipFile=rootFolder+File.separator+shipname+".swf";
        
        new org.kcwiki.tools.swfunpacker.UnpackSwf().ffdec(publishFolder, shipFile);
        
        File[] fileList = new File(publishFolder+File.separator+"images").listFiles();
        if(fileList!=null){
            /*for (File file : fileList) {
                if(fileList.length==15){
                        
                }
            }*/
                
            msgPublish.unkonwedShipListPublisher(publishFolder+File.separator+"images");
            return true;
        }
        return false;
    }
    
    
    public static void main(String[] args){
        intputFile="G:\\KcWiki\\Start2 历代文件\\start2\\2018020520.json";
        String[] filelist = new File("G:\\KcWiki\\Start2 历代文件\\start2").list();
        for(String file:filelist){
            if(!file.contains("json")){
                continue;
            }
            intputFile = "G:\\KcWiki\\Start2 历代文件\\start2\\" + file;
            new GetUnkownShip().getAllData();
        }
        //new GetUnkownShip().getUnknowData();
        JOptionPane.showMessageDialog(null, "文件生成完毕。");
    }
    
    
}
