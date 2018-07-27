/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.files.processor.src;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import kcwiki.x.kcscanner.cache.inmem.RuntimeValue;
import kcwiki.x.kcscanner.message.websocket.MessagePublisher;

import kcwiki.x.kcscanner.tools.CommontUtils;
import kcwiki.x.kcscanner.types.PublishStatus;
import kcwiki.x.kcscanner.types.PublishTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author iTeam_VEP
 */
@Component
public class VerifyScr {
    static final Logger LOG = LoggerFactory.getLogger(VerifyScr.class);
    
    @Autowired
    RuntimeValue runtimeValue;
    @Autowired
    MessagePublisher messagePublisher;
    private String rootFolder;
    private HashMap<String,Object> fileData = new LinkedHashMap<>();
    private String newFile;
    private String oldFile;
    
    public boolean verifyscr(String newFileFolder,String oldFileFolder) {
        new Thread() {  //创建新线程用于下载
            @Override
            public void run() {
                rootFolder = runtimeValue.TEMPLATE_FOLDER+File.separator+"currentswf";
                HashMap<String, String> scrDelList = new LinkedHashMap<>();
                newFile = newFileFolder;
                oldFile = oldFileFolder;
                int newScr=0;
                int oldScr;
                scrDelList.clear();
                
                if(new File(oldFileFolder).exists()){
                    scrDelList=readOldScr(oldFileFolder,scrDelList);
                    oldScr=scrDelList.size();
                    String filename=newFileFolder.substring(0, newFileFolder.lastIndexOf(File.separator));
                    newScr=readNewScr(newFileFolder,scrDelList,newScr);
                    
                    //差分结果缓存
//                    swfSrcPatch.put(newFileFolder.substring(rootFolder.length()+1, newFileFolder.length()), fileData.clone());
                    fileData.clear();
                    int lastest = oldScr-newScr;
                    if(lastest != 0){
                        //MD5互查对比完成
                        
                        messagePublisher.publish(
                                String.format("%s\t scr 剩余文件：%s", filename.substring(filename.lastIndexOf(File.separator)+1, filename.length()), lastest),
                                PublishTypes.Admin, 
                                PublishStatus.NORMAL
                        );
                    }
                    scrDelList.clear();
                    //String data = JSON.toJSONString(swfSrcPatch,SerializerFeature.BrowserCompatible);
                }
                //new srcdiff().getData();
            }
        }.start();
        return true;
    }
    
    public HashMap readOldScr(String filepath,HashMap scrDelList)  {
        File file = new File(filepath);
        if (!file.isDirectory()) {
            scrDelList.put(CommontUtils.getFileHex(file.getAbsolutePath()),file.getAbsolutePath());
        } else if (file.isDirectory()) {
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++) {
                File readfile = new File(filepath + File.separator + filelist[i]);
                if (!readfile.isDirectory()) {
                    scrDelList.put(CommontUtils.getFileHex(readfile.getAbsolutePath()),readfile.getAbsolutePath());
                } else if (readfile.isDirectory()) {
                    scrDelList.putAll(readOldScr(filepath + File.separator + filelist[i],scrDelList));
                }
            }
        }
        //System.out.println(scrDelList);
        return scrDelList;
    }
    
    public int readNewScr(String filepath,HashMap<String,String> scrDelList,int count) {
        scrdiff srcdiffer = new scrdiff();
        File file = new File(filepath);
        String newFilePath = file.getAbsolutePath();
        if (!file.isDirectory()) {
            if(scrDelList.get(CommontUtils.getFileHex(newFilePath)) != null){
                new File(newFilePath).delete();
                count++;
            }else{
                String oldFilePath = oldFile + File.separator + newFilePath.substring(newFile.length()+1, newFilePath.length());
                ArrayList tmp = srcdiffer.differ(oldFilePath,newFilePath);
                if(!tmp.isEmpty())
                    fileData.put(newFilePath.substring(rootFolder.length()+1, newFilePath.length()), tmp.clone());
            }
        } else if (file.isDirectory()) {
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++) {
                File readfile = new File(filepath + File.separator + filelist[i]);
                if (!readfile.isDirectory()) {
                    newFilePath = readfile.getAbsolutePath();
                    if(scrDelList.get(CommontUtils.getFileHex(newFilePath)) != null){
                        new File(newFilePath).delete();
                        count++;
                    }else{
                        String oldFilePath = oldFile + File.separator + newFilePath.substring(newFile.length()+1, newFilePath.length());
                        ArrayList<String> tmp = srcdiffer.differ(oldFilePath,newFilePath);
                        if(!tmp.isEmpty())
                            fileData.put(newFilePath.substring(rootFolder.length()+1, newFilePath.length()), tmp.clone());
                    }
                    //String tempfolder=readfile.getAbsolutePath().substring(0,readfile.getAbsolutePath().lastIndexOf(File.separator) );
                    //count=count+delFiles(tempfolder,scrDelList);
                } else if (readfile.isDirectory()) {
                    count=readNewScr(filepath + File.separator + filelist[i],scrDelList,count);
                }
            }
            if(file.exists()){
                File[] fileList = file.listFiles();
                if(fileList.length==0){
                    file.delete();
                }
            }
        }
        return count;
    }
    
    public static int delFiles(String folder,HashMap scrDelList){
        int count=0;
        if(!new File(folder).exists()){return 0;}
        File[] fileList = new File(folder).listFiles();
        for (File fileList1 : fileList) {
            if(scrDelList.get(CommontUtils.getFileHex(folder+File.separator + fileList1.getName()))!=null){
                fileList1.delete();
                count++;
            }
        }
        fileList = new File(folder).listFiles();
        if(fileList.length==0){
            new File(folder).delete();
        }
        return count;
    }
    
    public static void main(String[] args) {
//        MainServer.setTempFolder("C:\\Users\\iTeam_VEP\\Desktop\\tset\\test");
//        rootFolder = MainServer.getTempFolder()+File.separator+"currentswf";
        try {
            //new Controller().Analysis("C:\\Users\\VEP\\Desktop\\test\\test\\Core.swf", "C:\\Users\\VEP\\Desktop\\test\\test", "null");
            new VerifyScr().verifyscr("C:\\Users\\iTeam_VEP\\Desktop\\tset\\test\\currentswf\\new\\scripts", "C:\\Users\\iTeam_VEP\\Desktop\\tset\\test\\currentswf\\old\\scripts");
        } catch (Exception ex) {
            
        }
    }
    
}
