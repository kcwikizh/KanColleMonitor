/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kcwiki.monitor.file;

import java.io.File;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kcwiki.exception.CodeDict;
import org.kcwiki.initializer.MainServer;
import static org.kcwiki.initializer.MainServer.isStopScanner;
import moe.kcwiki.handler.massage.msgPublish;
import org.kcwiki.handler.thread.getUnkownSlotitemPool;
import org.kcwiki.httpclient.Config;
import org.kcwiki.exception.CustomedException;
import org.kcwiki.httpclient.HttpUtils;
import org.apache.hc.client5.http.config.RequestConfig;

/**
 *
 * @author iTeam_VEP
 */
public class GetUnknowSlotitem {
    private final int slotitemNo;
    private final int shinkaiSlotitemNo;
    private final int maxSlotitemNo;
    private final int maxSelfSlotitemNo;
    private final String proxyhost;
    private final int proxyport;
    private static String serveraddress;
    private static String servername;
    private static int servernum=0;
    public static LinkedHashMap<String, String> unknowShipList = new LinkedHashMap<>(); 
    private final java.util.ArrayList<String>  dataList;
    private final String rootFolder = MainServer.getPublishFolder()+ File.separator+"newSlotItem";
    //String[] serverlistaddress= new String[]{"203.104.209.71", "203.104.209.87", "125.6.184.16", "125.6.187.205", "125.6.187.229","125.6.187.253", "125.6.188.25", "203.104.248.135", "125.6.189.7", "125.6.189.39","125.6.189.71", "125.6.189.103", "125.6.189.135", "125.6.189.167", "125.6.189.215","125.6.189.247", "203.104.209.23", "203.104.209.39", "203.104.209.55", "203.104.209.102"};
    private static ArrayList<String> serverlistaddress = (ArrayList<String>) MainServer.getWorldList().clone();
    private static String[] serverlistname = new String[]{"横须贺镇守府","呉镇守府","佐世保镇守府","舞鹤镇守府","大凑警备府","トラック泊地","リンガ泊地","ラバウル基地","ショートランド泊地","ブイン基地","タウイタウイ泊地","パラオ泊地","ブルネイ泊地","単冠湾泊地","幌筵泊地","宿毛湾泊地","鹿屋基地","岩川基地","佐伯湾泊地","柱岛泊地"};
    private RequestConfig hcconfig = Config.makeProxyConfig(false);
    
    public GetUnknowSlotitem(){
        this.proxyhost = MainServer.getProxyhost();
        this.proxyport = MainServer.getProxyport();
        slotitemNo = Integer.valueOf(MainServer.getSlotitemno());
        shinkaiSlotitemNo = Integer.valueOf(MainServer.getShinkaislotitemno());
        maxSlotitemNo = 600;
        maxSelfSlotitemNo = 300;
        dataList = new  java.util.ArrayList<>();
        serverAddress();
    }
    
    public void getUnknowData(){
        
        final int taskID = getUnkownSlotitemPool.getTaskNum();
            getUnkownSlotitemPool.addTask(() -> {
                boolean hasDownload = false;
                int no=slotitemNo;
                msgPublish.msgPublisher("开始装备扫描。",0,0);
                    while(!isStopScanner()){  
                        if (getMDD("http://"+serveraddress+"/kcs/resources/image/slotitem/card/"+String.valueOf(no)+".png")) {
                            getMDD("http://"+serveraddress+"/kcs/resources/image/slotitem/item_character/"+String.valueOf(no)+".png");
                            getMDD("http://"+serveraddress+"/kcs/resources/image/slotitem/item_on/"+String.valueOf(no)+".png");
                            getMDD("http://"+serveraddress+"/kcs/resources/image/slotitem/item_up/"+String.valueOf(no)+".png");
                            hasDownload = true;
                            //msgPublish.msgPublisher("目前下载到的装备ID：\t"+no,0,0);
                            no++;
                        }else{
                            //msgPublish.msgPublisher("目前扫描到的装备ID：\t"+no,0,0);
                            serverAddress();
                            no++;
                            if(no > maxSlotitemNo){
                                if(hasDownload){
                                    msgPublish.msgPublisher("扫描到新装备，已下载完毕",0,1);
                                    msgPublish.urlListPublisher(rootFolder+File.separator+"card","newSlotitem");
                                    return taskID;
                                }else {
                                    no = slotitemNo;
                                    try{
                                        sleep(60*1000);
                                    } catch (InterruptedException ex){
                                        Logger.getLogger(GetUnknowSlotitem.class.getName()).log(Level.SEVERE, null, ex);
                                        msgPublish.msgPublisher("新装备扫描线程已强制退出。",0,0);
                                        return taskID;
                                    }
                                    if(isStopScanner()){
                                        return taskID;
                                    }
                                    msgPublish.msgPublisher("开始下一轮新装备扫描",0,0);
                                }
                            }
                            if(no < shinkaiSlotitemNo && no > maxSelfSlotitemNo) {
                                no = shinkaiSlotitemNo;
                            }
                        } 
                        if(isStopScanner()) {
                            return taskID;
                        }
                    }
                    msgPublish.msgPublisher("新装备扫描线程已停止",0,0);
                
                return taskID;
        },taskID,"GetUnknowSlotitem-getUnknowData"); 
    }
    
    public synchronized boolean getMDD(String URL) throws CustomedException {
        int i=URL.lastIndexOf("/"); //取得子串的初始位置
        String filename=URL.substring(i+1,URL.length());
        String filepath = null;
        
        String url  = URL+ "?VERSION=" + System.currentTimeMillis();
        if  (!(new File(filepath).exists()) || !(new File(filepath).isDirectory())) {
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
        return success; 
    }
    
    private void serverAddress(){
        serveraddress=serverlistaddress.get(servernum);
        servername=serverlistname[servernum]+"-"+serverlistaddress.get(servernum);
        servernum++;
        if(servernum==serverlistaddress.size()){
            servernum=0;
        }
    }
    
    public static void main(String[] args){
        //new GetUnknowSlotitem().getUnknowData();
    }
    
}
