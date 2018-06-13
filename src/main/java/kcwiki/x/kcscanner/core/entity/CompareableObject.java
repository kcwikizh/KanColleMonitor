/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core.entity;

import com.google.common.collect.Lists;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import kcwiki.x.kcscanner.core.start2.analyzer.Start2Analyzer;
import org.slf4j.LoggerFactory;

/**
 *
 * @author x5171
 */
public class CompareableObject {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(CompareableObject.class);
    
    protected void contrastObj(Object obj1, Object obj2) {  
        List<String> textList = Lists.newArrayList(); 
            try {  
                Class clazz = obj1.getClass();  
                Field[] fields = obj1.getClass().getDeclaredFields();  
                for (Field field : fields) {  
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);  
                    Method getMethod = pd.getReadMethod();  
                    Object o1 = getMethod.invoke(obj1);  
                    Object o2 = getMethod.invoke(obj2);  
                    String s1 = o1 == null ? "" : o1.toString();//避免空指针异常  
                    String s2 = o2 == null ? "" : o2.toString();//避免空指针异常  
                    //思考下面注释的这一行：会bug的，虽然被try catch了，程序没报错，但是结果不是我们想要的  
                    //if (!o1.toString().equals(o2.toString())) {  
                    if (!s1.equals(s2)) {  
                        textList.add("不一样的属性：" + field.getName() + " 属性值：[" + s1 + "," + s2 + "]");  
                    }  
                }  
            } catch (Exception e) {  
                System.out.println(e.getMessage());  
            }  
            for (String object : textList) {  
                LOG.info("{}", object);  
            }  
            if(!textList.isEmpty()){
                System.out.println();
            }
    } 
}
