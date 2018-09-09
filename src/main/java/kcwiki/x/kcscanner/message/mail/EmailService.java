/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.message.mail;

import java.io.File;
import java.util.logging.Level;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang3.exception.ExceptionUtils;
import kcwiki.x.kcscanner.database.service.LogService;
import kcwiki.x.kcscanner.initializer.AppConfigs;
import static kcwiki.x.kcscanner.tools.ConstantValue.LINESEPARATOR;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import kcwiki.x.kcscanner.types.MessageLevel;

/**
 *
 * @author x5171
 * http://www.baeldung.com/spring-email
 */
@Component
public class EmailService {
    private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);
    
    @Autowired
    public JavaMailSender emailSender;
    @Autowired
    private AppConfigs appConfigs;
    @Autowired
    private LogService logService;
   
    public void sendSimpleEmail(MessageLevel msgTypes, String text){
        SimpleMailMessage message = new SimpleMailMessage();//消息构造器
        message.setFrom(appConfigs.getMail_sender());//发件人
        message.setTo(appConfigs.getMail_recipient());//收件人
        String title = titleGenerator(msgTypes);
        message.setSubject(title);//主题
        message.setText(text);//正文
        try{
            emailSender.send(message);
        } catch (MailException ex) {
            String log = String.format("邮件发送失败。标题为：%s，内容为：%s。", title, text);
            LOG.error(ExceptionUtils.getStackTrace(ex));
            logService.addLog(MessageLevel.ERROR, log);
            return;
        }
        String log = String.format("发送邮件标题为：%s，内容为：%s。", title, text);
        LOG.info(log);
//        logService.addLog(msgTypes, log);
    }
    
    public void sendSimpleEmail(MessageLevel msgTypes, Throwable ex){
        SimpleMailMessage message = new SimpleMailMessage();//消息构造器
        message.setFrom(appConfigs.getMail_sender());//发件人
        message.setTo(appConfigs.getMail_recipient());//收件人
        String title = titleGenerator(msgTypes);
        message.setSubject(title);//主题
        String text = String.format(
                "%s发生错误，错误信息为：%s%s%s%s", 
                ex.getLocalizedMessage(),
                LINESEPARATOR, 
                ex.getMessage(),
                LINESEPARATOR, 
                ExceptionUtils.getStackTrace(ex)
        );
        message.setText(text);//正文
        try{
            emailSender.send(message);
        } catch (MailException ex1) {
            String log = String.format("邮件发送失败。标题为：%s，内容为：%s。", title, text);
            LOG.error(ExceptionUtils.getStackTrace(ex1));
            logService.addLog(MessageLevel.ERROR, log);
            return;
        }
        String log = String.format("发送邮件标题为：%s，内容为：%s。", title, text);
        LOG.info(log);
//        logService.addLog(msgTypes, log);
    }
    
    public void sendMessageWithAttachment(
        String to, String subject, String text, String pathToAttachment) {
        try {
            // ...
            
            MimeMessage message = emailSender.createMimeMessage();
            
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            
            FileSystemResource file
                    = new FileSystemResource(new File(pathToAttachment));
            helper.addAttachment("Invoice", file);
            
            emailSender.send(message);
            // ...
        } catch (MessagingException ex) {
            java.util.logging.Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String titleGenerator(MessageLevel msgTypes) {
        return String.format("%s，类型为：%s", appConfigs.getMail_title(), msgTypes.getName());
    }
}
