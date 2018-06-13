/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.swfunpacker;

import kcwiki.x.kcscanner.core.files.analyzer.src.VerifyScr;
import java.io.File;
import java.io.IOException;
import kcwiki.x.kcscanner.message.websocket.MessagePublisher;
import static kcwiki.x.kcscanner.tools.ConstantValue.PUBLISH_FOLDER;
import static kcwiki.x.kcscanner.tools.ConstantValue.TEMPLATE_FOLDER;
import static kcwiki.x.kcscanner.tools.ConstantValue.TEMP_FOLDER;
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
public class Controller {
    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Controller.class);
    
    @Autowired
    MessagePublisher messagePublisher;
    
    private String previousfolder;
    private String currentfolder;    
    private final UnpackSwf ffdec = new UnpackSwf();
    private final kcwiki.x.kcscanner.core.files.analyzer.image.Controller verify = new kcwiki.x.kcscanner.core.files.analyzer.image.Controller();
    

    public void Analysis(String filename,String filepath,String sourcepath) throws InterruptedException, Exception{
        previousfolder = TEMPLATE_FOLDER;
        currentfolder = String.format("%s/%s", PUBLISH_FOLDER, "currentswf");
        if(!new File(currentfolder).exists()){new File(currentfolder).mkdirs();}
        String outputpath=currentfolder+File.separator+sourcepath+File.separator+(filename.substring(0, filename.length()-4));
        if(filename.contains("Core")){
            if(!new kcwiki.x.kcscanner.swfunpacker.coredecryptor.CoreRecover().unlockCore(String.format("%s/%s", filepath, filename), TEMP_FOLDER))
                messagePublisher.publish("Core.swf 解码失败。", PublishTypes.Admin, PublishStatus.ERROR);
            ffdec.ffdec(outputpath,TEMP_FOLDER+File.separator+"Core_hack.swf");
            new kcwiki.x.kcscanner.swfunpacker.coredecryptor.CoreDecrypt().getData(outputpath+File.separator+Server.getCoremap(), outputpath+File.separator+Server.getCoresound());
            filename = "Core_hack.swf";
        }
        //filename = (filename.substring(0, filename.length()-4));
        if(new File(previousfolder+File.separator+filename).exists()){
            if(!filename.contains("Core_hack")){
                //msgPublish.msgPublisher(filename+"\t开始解压",0,0);
                ffdec.ffdec(outputpath,filepath+File.separator+filename);
            }
            new VerifyScr().verifyscr(outputpath+File.separator+"scripts", previousfolder+File.separator+filename+File.separator+"scripts");
            verify.verifyimg(outputpath+File.separator+"images", previousfolder+File.separator+filename+File.separator+"images");
        } 
    }
}
