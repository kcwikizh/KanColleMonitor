/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.web.security.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import kcwiki.x.kcscanner.message.mail.EmailService;
import kcwiki.x.kcscanner.types.BaseHttpStatus;
import kcwiki.x.kcscanner.web.controller.BaseController;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

/**
 *
 * @author iHaru
 */
@Component
public class BaseAuthenticateController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(BaseAuthenticateController.class);
    protected HttpServletRequest request;  
    protected HttpServletResponse response;  
    protected HttpSession session;  
    
    @Autowired
    EmailService emailService;
  
    @ModelAttribute  
    public void checkAuthorization(HttpServletRequest request, HttpServletResponse response){  
        this.request = request;  
        this.response = response;  
        this.session = request.getSession(false);  
        Assert.notNull(request, "Request must not be null");  
        boolean isLogined = (boolean) (WebUtils.getSessionAttribute(this.request, "isLogined") == null ? false:WebUtils.getSessionAttribute(request, "isLogined"));
//        this.isLogined = session.getAttribute("isLogined")== null ? false:"true".equals(session.getAttribute("isLogined"));
    }  
    
}
