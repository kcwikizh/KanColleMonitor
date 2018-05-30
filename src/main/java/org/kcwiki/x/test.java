/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kcwiki.x;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;                
import freemarker.template.TemplateException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *  https://www.jianshu.com/p/f58802a29d8a
 *  https://www.cnblogs.com/zhongshiqiang/p/5764857.html
 *  https://blog.csdn.net/pzhtpf/article/details/7600100/
 *  https://www.cnblogs.com/newwind/p/5680389.html
 *  
 * @author x5171
 */
public class test {
    
    public void testcase() throws IOException, TemplateException{
        /** 进行base64位编码 **/
        Base64.Encoder encoder = java.util.Base64.getEncoder();
        /** 初始化配置文件 **/
        Configuration configuration = new Configuration();
        /** 设置编码 **/
        configuration.setDefaultEncoding("utf-8");
        /** 我的ftl文件是放在D盘的**/
        String fileDirectory = "C:\\Users\\x5171\\Desktop\\freemarker\\";
        /** 加载文件 **/
        configuration.setDirectoryForTemplateLoading(new File(fileDirectory));
        /** 加载模板 **/
        Template template = configuration.getTemplate("美国空袭叙利亚.ftl");
        /** 准备数据 **/
        Map<String,Object> dataMap = new HashMap<>();
        
        final String ridpre = "crId";
        final int ridsn = 20;
        SimpleDateFormat sdf=new SimpleDateFormat("MM/dd");
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Integer> chartMap = objectMapper.readValue("[{value:94155, name:'网站'},{value:48408, name:'论坛'},{value:15159, name:'微博'},{value:18153, name:'博客'},{value:8929, name:'微信'},{value:74084, name:'新聞'}]",
                new TypeReference<Map<String,Integer>>(){});
        
        
        /** 表格数据初始化 **/
        List<VOXEntity> VOXEntityList = new ArrayList<>();
        VOXEntityList.add(new VOXEntity(ridpre + (ridsn + 1), "标题1","http://baidu.com", sdf.format(new Date()), df.format(new Date()),"来源1",1,"内容1"));
        VOXEntityList.add(new VOXEntity(ridpre + (ridsn + 2), "标题2","http://qq.com", sdf.format(new Date()), df.format(new Date()),"来源2",2,"内容2"));
        VOXEntityList.add(new VOXEntity(ridpre + (ridsn + 3), "标题3","http://weibo.com", sdf.format(new Date()), df.format(new Date()),"来源3",1,"内容3"));

        /** 表格数据 studentList和freemarker标签要对应**/
        dataMap.put("voxList",VOXEntityList);
        dataMap.put("image2", encoder.encodeToString(getImgBytes("C:\\Users\\x5171\\Desktop\\freemarker\\1.png")));
        dataMap.put("image3", encoder.encodeToString(getImgBytes("C:\\Users\\x5171\\Desktop\\freemarker\\2.png")));
        dataMap.put("image4", encoder.encodeToString(getImgBytes("C:\\Users\\x5171\\Desktop\\freemarker\\3.png")));
        dataMap.put("title", "本期标题");
        dataMap.put("number", 1);
        dataMap.put("company", df.format(new Date()));
        dataMap.put("briefInfo", "导读内容");
        dataMap.put("sensInfo", "检测概述内容");
        

        /** 指定输出word文件的路径 **/
        String outFilePath = "C:\\Users\\x5171\\Desktop\\freemarker\\myFreeMarker.doc";
        File docFile = new File(outFilePath);
        FileOutputStream fos = new FileOutputStream(docFile);
        Writer out = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"),10240);
        template.process(dataMap,out);

        if(out != null){
            out.close();
        }
    }
    
    private byte[] getImgBytes(String file) throws IOException{
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(file);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(in != null){
                in.close();
            }
        }
        return data;
    }
}
