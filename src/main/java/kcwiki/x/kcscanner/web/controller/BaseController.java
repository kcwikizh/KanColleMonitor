/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import kcwiki.x.kcscanner.message.mail.EmailService;
import static kcwiki.x.kcscanner.tools.ConstantValue.LINESEPARATOR;
import kcwiki.x.kcscanner.types.BaseHttpStatus;
import kcwiki.x.kcscanner.types.MessageLevel;
import kcwiki.x.kcscanner.web.entity.BaseMessageEntity;
import kcwiki.x.kcscanner.web.exception.BaseException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author iHaru
 * https://blog.csdn.net/nmgrd/article/details/52734193
 */
@Service
public class BaseController <T>{
    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);
    protected HttpServletRequest request;  
    protected HttpServletResponse response;  
    protected HttpSession session;  
    
    @Autowired
    EmailService emailService;
  
    @ModelAttribute  
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){  
        this.request = request;  
        this.response = response;  
        this.session = request.getSession();  
        response.setHeader("Access-Control-Allow-Origin", "*");
    }  

    protected BaseMessageEntity RespondBaseGen(BaseHttpStatus httpStatus, String msg) {
        BaseMessageEntity respondBase = new BaseMessageEntity();
        respondBase.setStatus(httpStatus);
        respondBase.setMsg(msg);
        return respondBase;
    }
    
    @RequestMapping("*")
    public BaseMessageEntity defaultResponse(){
        return RespondBaseGen(BaseHttpStatus.FAILURE, "请求URL有误");
    }
    
    /**  
     * 用于处理异常的  
     * @param request
     * @param response
     * @param ex
     * @return  
     */  
    @ExceptionHandler({Exception.class, BaseException.class})  
    @ResponseBody
    public BaseMessageEntity ExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        LOG.error("BaseController - ExceptionHandler left message： {}{}", LINESEPARATOR, ExceptionUtils.getStackTrace(ex));
        emailService.sendSimpleEmail(MessageLevel.ERROR, String.format("服务器内部发生错误，请查阅日志文件以了解详情。具体错误信息如下：%s%s", 
                LINESEPARATOR, 
                ExceptionUtils.getStackTrace(ex)));
        BaseMessageEntity respondBase = new BaseMessageEntity();
        respondBase.setStatus(BaseHttpStatus.ERROR);
        respondBase.setMsg("服务器内部发生错误");
        return respondBase;
    }

}
