/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.swfunpacker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import kcwiki.x.kcscanner.cache.inmem.AppDataCache;
import kcwiki.x.kcscanner.message.websocket.MessagePublisher;
import static kcwiki.x.kcscanner.tools.ConstantValue.FILE_FFDEC;
import static kcwiki.x.kcscanner.tools.ConstantValue.LINESEPARATOR;
import kcwiki.x.kcscanner.types.PublishStatus;
import kcwiki.x.kcscanner.types.PublishTypes;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author x5171
 */
@Component
public class UnpackSwf {
    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(UnpackSwf.class);
    
    @Autowired
    MessagePublisher messagePublisher;
    
    private boolean isInit = false;
    
    public synchronized boolean callShell(String shellString){
        try {
            //msgPublish.msgPublisher(shellString+"准备执行完毕",0,0);
            if(shellString == null || shellString.length() == 0) {return false;}
            
            String[] cmd = { "sh", "-c", shellString };
            try (BufferedWriter eBfw = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(
                                    new File(
                                            String.format("%s/%s", AppDataCache.appConfigs.getFolder_workspace(), "ShellLogs.txt")
                                    ), 
                                    true
                            ), 
                            "UTF-8"
                    )
            )) {
                Process pcs=Runtime.getRuntime().exec(cmd);
                String line;  
                try (BufferedReader br = new BufferedReader(new InputStreamReader(pcs.getInputStream()))) {
                    eBfw.write("<OUTPUT>"+LINESEPARATOR);
                    eBfw.write("shellString: \t "+shellString+LINESEPARATOR);
                    while ((line = br.readLine()) != null){
                        eBfw.write(line+LINESEPARATOR);
                    }
                    eBfw.write("</OUTPUT>"+LINESEPARATOR);
                    if(pcs.waitFor(180, TimeUnit.SECONDS)){
                        return true;
                    } else {
                        pcs.destroyForcibly();
                        messagePublisher.publish(String.format("UnpackSwf-callShell模块没有在规定时间内执行完毕，已强行终止。脚本为： %s", shellString), PublishTypes.Admin, PublishStatus.ERROR);
                    }
                }
            }
            //msgPublish.msgPublisher(shellString+"执行完毕",0,0);
        } catch (IOException | InterruptedException ex) {
            messagePublisher.publish("UnpackSwf-callShell模块发生IOException | InterruptedException 异常。", PublishTypes.Admin, PublishStatus.ERROR);
            Logger.getLogger(UnpackSwf.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public int createDiffFile(String folder,String file) {
        int count =0;
        if(isInit){
            return count;
        }
        if(new File(file).isDirectory()){
            File[] fileList = new File(file).listFiles();
            for(File swf:fileList){
                if(swf.isFile() && swf.getName().contains(".swf")) {
                    if(swf.getName().toLowerCase().contains("core.swf")){
                        continue;
                    }
                    count++;
                    
                    callShell(String.format("java -jar %s -export script,image,sound \"%s/%s\" \"%s\"", FILE_FFDEC, folder, swf.getName(), swf.getAbsolutePath()));
                }
            }
        } else {
            count++;
            File swf = new File(file);
            callShell(String.format("java -jar %s -export script,image,sound \"%s/%s\" \"%s\"", FILE_FFDEC, folder, swf.getName(), swf.getAbsolutePath()));
        }
        
        isInit = true;
        return count;
    }
    
    //解压各种游戏swf和批量解压文件夹
    public boolean ffdec(String folder,String file){
        return callShell(String.format("java -jar %s -export script,image,sound \"%s\" \"%s\"", FILE_FFDEC, folder, file));
    }
    
    //一次性解压所有下载完的地图
    public boolean maps(String folder,String file){
        return callShell(String.format("java -jar %s -export image,frame \"%s\" \"%s\"", FILE_FFDEC, folder, file));
    }
    
    //Start2DataThread
    public boolean unpackStart2(String folder,String file){
        messagePublisher.publish("开始进行Start2Data解包···", PublishTypes.Admin, PublishStatus.NORMAL);
//        if(!DBCenter.NewShipDB.isEmpty()){
        if(false) {
            callShell(String.format("java -jar %s -export script,image,sound \"%s/swf/ships\" \"%s/swf/ships\"", FILE_FFDEC, folder, file));
        }
//        if(!DBCenter.NewMapinfoDB.isEmpty()){
        if(false) {
            callShell(String.format("java -jar %s -export script,image,sound \"%s/swf/map\" \"%s/swf/map\"", FILE_FFDEC, folder, file));
        }
//        if(!DBCenter.NewMapBgm.isEmpty()){
        if(false) {
            callShell(String.format("java -jar %s -export script,image,sound \"%s/swf\" \"%s/swf\"", FILE_FFDEC, folder, file));
        }
        return true;
    }
}
