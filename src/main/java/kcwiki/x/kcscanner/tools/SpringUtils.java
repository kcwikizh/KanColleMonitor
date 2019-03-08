/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.tools;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 *
 * @author iHaru
 */
@Component  
public class SpringUtils implements ApplicationContextAware {  
  
    private static ApplicationContext applicationContext = null;  
// 非@import显式注入，@Component是必须的，且该类必须与main同包或子包  
    // 若非同包或子包，则需手动import 注入，有没有@Component都一样  
    // 可复制到Test同包测试  
  
    @Override  
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {  
        if(SpringUtils.applicationContext == null){  
            SpringUtils.applicationContext  = applicationContext;  
        }  
        System.out.println("\r\n---------------kcwiki.x.kcscanner.tools.SpringUtil---------------\r\n");  
    }  
  
    //获取applicationContext  
    public static ApplicationContext getApplicationContext() {  
        return applicationContext;  
    }  
  
    //通过name获取 Bean.  
    public static Object getBean(String name){  
        return getApplicationContext().getBean(name);  
  
    }  
  
    //通过class获取Bean.  
    public static <T> T getBean(Class<T> clazz){  
        return getApplicationContext().getBean(clazz);  
    }  
  
    //通过name,以及Clazz返回指定的Bean  
    public static <T> T getBean(String name,Class<T> clazz){  
        return getApplicationContext().getBean(name, clazz);  
    }  
  
}  
