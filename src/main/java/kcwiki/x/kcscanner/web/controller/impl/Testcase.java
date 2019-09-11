/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.web.controller.impl;

import java.util.Date;
import kcwiki.x.kcscanner.message.mail.EmailService;
import kcwiki.x.kcscanner.message.websocket.MessagePublisher;
import kcwiki.x.kcscanner.types.MessageLevel;
import kcwiki.x.kcscanner.message.websocket.types.ModuleType;
import kcwiki.x.kcscanner.message.websocket.types.PublishTypes;
import org.iharu.proto.web.WebResponseProto;
import org.iharu.type.BaseHttpStatus;
import org.iharu.type.ResultType;
import org.iharu.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author iHaru
 */
@RestController
@RequestMapping("/test")
public class Testcase extends BaseController {
    
    @Autowired
    MessagePublisher messagePublisher;   
    @Autowired
    EmailService emailService;
    
    @RequestMapping("/sendmail")
    public String sendMail(){
        emailService.sendSimpleEmail(MessageLevel.DEBUG, "测试邮件");
        return "SUCCESS";
    }
    
    
    @RequestMapping("/wstest")
    public String wsSendMsg(){
        messagePublisher.publish("ws test", ModuleType.ManualFileScan, ResultType.UNKNOWN);
        messagePublisher.publish("ws test", ModuleType.ManualFileScan, ResultType.FAILURE);
        return "SUCCESS";
    }
    
}
