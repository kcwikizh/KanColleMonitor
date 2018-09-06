/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.message.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import kcwiki.x.kcscanner.initializer.AppConfigs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

/**
 *
 * @author x5171
 * http://www.baeldung.com/spring-email
 */
@Component
public class EmailConfig {
    private static final Logger LOG = LoggerFactory.getLogger(EmailConfig.class);
    
    @Autowired
    AppConfigs appConfigs;
    
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

//        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//        String appConfigFile = String.format("%sconfiguration/appconfig/appconfig.properties", rootPath);
//        Properties appProps = new Properties();
//        if(!new File(appConfigFile).exists()){
//            LOG.error("在当前位置{}找不到配置文件，程序初始化失败。", appConfigFile);
//        }
//        try {
//            appProps.load(new FileInputStream(appConfigFile));
//            mailSender.setHost(appProps.getProperty("mail.serverhost"));//指定用来发送Email的邮件服务器主机名
//            mailSender.setPort(Integer.valueOf(appProps.getProperty("mail.serverport")));//默认端口，标准的SMTP端口
//            mailSender.setUsername(appProps.getProperty("mail.username"));//用户名
//            mailSender.setPassword(appProps.getProperty("mail.password"));//密码
//        } catch (FileNotFoundException ex) {
//            LOG.error("Email系统初始化失败，在{}找不到配置文件。", appConfigFile);
//        } catch (IOException ex) {
//            LOG.error("Email系统初始化失败，解析{}配置文件时发生IOException。", appConfigFile);
//        }
        
        mailSender.setHost(appConfigs.getMail_server_host());//指定用来发送Email的邮件服务器主机名
        mailSender.setPort(appConfigs.getMail_server_port());//默认端口，标准的SMTP端口
        mailSender.setUsername(appConfigs.getMail_server_username());//用户名
        mailSender.setPassword(appConfigs.getKcserver_account_password());//密码

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtps.ssl.checkserveridentity", "true");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        props.put("mail.smtps.ssl.trust", "*");
//        props.put("mail.smtp.socketFactory", sf);
        props.put("mail.smtp.socketFactory.fallback", "false");  
        props.put("mail.smtp.timeout", "15000");
        return mailSender;
    }
}
