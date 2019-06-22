/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.web.websocket.interceptor;

import java.util.Base64;
import java.util.List;
import javax.servlet.http.HttpSession;
import kcwiki.x.kcscanner.initializer.AppConfig;
import org.iharu.web.session.entity.SessionEntity;
import org.iharu.websocket.interceptor.DefaultWebsocketInterceptor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * WebSocket 拦截器，用于将用户信息从session中存入map，方便后面websocket请求时从map中找到指定的用户session信息
 * @author iHaru
 * https://blog.csdn.net/qq_28988969/article/details/78104850
 * https://dzone.com/articles/spring-boot-based-websocket
 */
@Component
public class AdministratorInterceptor extends DefaultWebsocketInterceptor {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(AdministratorInterceptor.class);
  
    @Autowired
    private AppConfig appConfig;

    @Override
    protected SessionEntity valid(ServletServerHttpRequest servletRequest, HttpSession session)
    {
        List<String> tokenlist = servletRequest.getHeaders().get("x-access-token");
        if ((tokenlist == null) || (tokenlist.isEmpty())) {
          return null;
        }
        String access_token = (String)tokenlist.get(0);
        if (StringUtils.isEmpty(access_token)) {
          return null;
        }
        LOG.debug("x-access-token: {}", access_token);
        LOG.debug("{}", servletRequest.getHeaders());
        if (!Base64.getEncoder().withoutPadding().encodeToString(appConfig.getWebsocket_token().getBytes()).equals(access_token)) {
          return null;
        }
        SessionEntity sessionEntity = new SessionEntity();
        sessionEntity.setUid(session.getId());
        sessionEntity.setToken(access_token);
        sessionEntity.setValid_timestamp(System.currentTimeMillis());
        return sessionEntity;
    }
}