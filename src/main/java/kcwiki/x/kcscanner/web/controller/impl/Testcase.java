/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.web.controller.impl;

import java.util.Date;
import kcwiki.x.kcscanner.message.mail.EmailService;
import kcwiki.x.kcscanner.message.websocket.MessagePublisher;
import kcwiki.x.kcscanner.types.BaseHttpStatus;
import kcwiki.x.kcscanner.types.MessageLevel;
import kcwiki.x.kcscanner.message.websocket.types.WebsocketMessageType;
import kcwiki.x.kcscanner.message.websocket.types.PublishTypes;
import kcwiki.x.kcscanner.web.controller.BaseController;
import kcwiki.x.kcscanner.web.entity.BaseMessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author x5171
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
        BaseMessageEntity baseMessageEntity = new BaseMessageEntity();
        baseMessageEntity.setData(new Date());
        baseMessageEntity.setMsg("Full Msg Test");
        messagePublisher.publish(baseMessageEntity, PublishTypes.All, WebsocketMessageType.KanColleScanner_Manual_FileScan, MessageLevel.ERROR);
        baseMessageEntity = new BaseMessageEntity();
        baseMessageEntity.setData(new Date());
        baseMessageEntity.setMsg("Full Msg Test");
        baseMessageEntity.setStatus(BaseHttpStatus.Bad_Request);
        messagePublisher.publish(baseMessageEntity);
        return "SUCCESS";
    }
    
}
