/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.files.processor.image;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException; 
import static java.lang.Thread.sleep;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.Future;
import kcwiki.x.kcscanner.message.websocket.MessagePublisher;
import kcwiki.x.kcscanner.message.websocket.types.PublishTypes;
import kcwiki.x.kcscanner.types.MessageLevel;
import kcwiki.x.kcscanner.message.websocket.types.WebsocketMessageType;
import static org.iharu.constant.ConstantValue.LINESEPARATOR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
 
/**
 *
 * @author VEP
 */
public class PhashController implements Runnable{
    static final Logger LOG = LoggerFactory.getLogger(PhashController.class);
    
    @Autowired
    MessagePublisher messagePublisher;
    
    @Async
    public Future verifyimg(String newFileFolder,String oldFileFolder) throws FileNotFoundException, IOException, Exception{
        
                HashMap<String, String> NewHashList = new LinkedHashMap<>();
                HashMap<String, String> OldHashList = new LinkedHashMap<>();
                HashMap<String, String> delList = new LinkedHashMap<>();
                try {
                    NewHashList.clear();
                    OldHashList.clear();
                    delList.clear();
                        
                    if(new File(oldFileFolder).exists()){
                        File[] fileList = new File(oldFileFolder).listFiles();       
                        for (File fileList1 : fileList) {
                            delList.put(new GetHash().getNewHash(oldFileFolder + File.separator + fileList1.getName()),fileList1.getName());
                        }

                        //new ScannerGui().setStatement(2);
                        fileList = new File(newFileFolder).listFiles();
                        for (File fileList1 : fileList) {
                            if(delList.get(new GetHash().getNewHash(newFileFolder+File.separator + fileList1.getName()))!=null){
                                fileList1.delete();
                            }
                        }
                        String filename=oldFileFolder.substring(0, oldFileFolder.lastIndexOf(File.separator));
                        //msgPublish.msgPublisher(filename.substring(filename.lastIndexOf(File.separator)+1, filename.length())+"\t img MD5互查对比完成",1);
                        sleep(1*1000);

                        //new ScannerGui().setStatement(3);
                        NewHashList.clear();
                        File[] newfileList = new File(newFileFolder).listFiles();
                        File[] oldfileList = new File(oldFileFolder).listFiles();
                        Scanner p = new Scanner();
                        int count=0;
                        for (File newfile : newfileList) {
                            NewHashList.put(Scanner.getFeatureValue(newFileFolder+File.separator + newfile.getName()), newFileFolder+File.separator + newfile.getName());
                            count++;
                        }
                        LOG.info(count+LINESEPARATOR);
                        count=0;
                        for (File oldfile : oldfileList) {
                            OldHashList.put(Scanner.getFeatureValue(oldFileFolder+File.separator + oldfile.getName()), oldFileFolder+File.separator + oldfile.getName());
                            count++;
                        }
                        LOG.info(count+LINESEPARATOR);
                        for(String newfile : NewHashList.keySet()){
                            for(String oldfile : OldHashList.keySet()){
                                double score=Scanner.calculateSimilarity(newfile, oldfile);
                                //if(delList.get(OldHashList.get(oldfile))!=null){continue;}
                                if(score > 0.90){
                                    new File(NewHashList.get(newfile)).delete();
                                    //delList.put(OldHashList.get(oldfile),oldfile);
                                    //System.out.print(score+LINESEPARATOR);
                                    break;
                                }
                            }
                        }
                        int lastest = new File(newFileFolder).listFiles().length;
                        if(lastest != 0){
                            //pHash互查对比完成
                            messagePublisher.publish(
                                    String.format(
                                            "%s\t img 剩余文件：%d", 
                                            filename.substring(
                                                    filename.lastIndexOf(File.separator)+1, 
                                                    filename.length()
                                            ), 
                                            lastest
                                    ), 
                                    PublishTypes.Admin, 
                                    WebsocketMessageType.KanColleScanner_UploadStart2, MessageLevel.INFO
                            );
//                            DBCenter.imgdiff.add(newFileFolder);
                        }
                        sleep(1*1000);
                    }
                    
                    NewHashList.clear();
                    OldHashList.clear();
                    delList.clear();
                    return new AsyncResult(1);
                } catch (InterruptedException ex) {
                    
                }
        return null;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
