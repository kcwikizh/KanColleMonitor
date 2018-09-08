/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.tools.zip.ZipEntry;  
import org.apache.tools.zip.ZipOutputStream;  
import org.slf4j.LoggerFactory;

/**
 *
 * @author iTeam_VEP
 */
public class ZipCompressorUtils {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ZipCompressorUtils.class);
  
    private ZipCompressorUtils(){};  
   /** 
     * 创建ZIP文件 
     * @param sourcePath 文件或文件夹路径 
     * @param zipPath 生成的zip文件存在路径（包括文件名） 
     */  
    public static void createZip(String sourcePath, String destFolder,String zipName) {  
        FileOutputStream fos = null;  
        ZipOutputStream zos = null;  
        if(!new File(destFolder).exists()){
            new File(destFolder).mkdirs();
        }
        try {  
            fos = new FileOutputStream(destFolder+File.separator+zipName);  
            zos = new ZipOutputStream(fos);  
            zos.setEncoding("utf-8");//此处修改字节码方式。  
            //createXmlFile(sourcePath,"293.xml");  
            writeZip(new File(sourcePath), "", zos);  
        } catch (FileNotFoundException e) { 
            
            //log.error("创建ZIP文件失败",e);  
        } finally {  
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException ex) {
                    LOG.error("创建ZIP文件失败");
                    LOG.error(ExceptionUtils.getStackTrace(ex));
                }
            } //log.error("创建ZIP文件失败",e);
  
        }  
    }  
  
    private static void writeZip(File file, String parentPath, ZipOutputStream zos) {  
        if(file.exists()){  
            if(file.isDirectory()){//处理文件夹  
                parentPath+=file.getName()+File.separator;  
                File [] files=file.listFiles();  
                if(files.length != 0)  
                {  
                    for(File f:files){  
                        writeZip(f, parentPath, zos);  
                    }  
                }  
                else  
                {       try {
                    //空目录则创建当前目录
                    zos.putNextEntry(new ZipEntry(parentPath)); // TODO Auto-generated catch block
                    } catch (IOException ex) {
                        LOG.error("创建ZIP文件失败");
                        LOG.error(ExceptionUtils.getStackTrace(ex));
                    }
                }  
            }else{  
                FileInputStream fis=null;  
                try {  
                    fis=new FileInputStream(file);  
                    ZipEntry ze = new ZipEntry(parentPath + file.getName());  
                    zos.putNextEntry(ze);  
                    byte [] content=new byte[1024];  
                    int len;  
                    while((len=fis.read(content))!=-1){  
                        zos.write(content,0,len);  
                        zos.flush();  
                    }  
  
                } catch (FileNotFoundException e) {  
                    //log.error("创建ZIP文件失败",e);  
                } catch (IOException e) {  
                    //log.error("创建ZIP文件失败",e);  
                }finally{  
                    try {  
                        if(fis!=null){  
                            fis.close();  
                        }  
                    }catch(IOException e){  
                        //log.error("创建ZIP文件失败",e);  
                    }  
                }  
            }  
        }  
    }   
}
