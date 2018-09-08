/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.web.websocket.interceptor;

import java.util.Map;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * WebSocket 拦截器，用于将用户信息从session中存入map，方便后面websocket请求时从map中找到指定的用户session信息
 * @author x5171
 * https://blog.csdn.net/qq_28988969/article/details/78104850
 * https://dzone.com/articles/spring-boot-based-websocket
 */
@Component
public class AdministratorInterceptor implements HandshakeInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(AdministratorInterceptor.class);
    
    private static final String SESSION_USER = "user";

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, 
            WebSocketHandler wsHandler, Map<String, Object> attributes) throws HandshakeFailureException {
        return true;
//        if (request instanceof ServletServerHttpRequest) {
//            ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
//            HttpSession session = serverHttpRequest.getServletRequest().getSession();
//            LOG.info("用户建立连接。。。");
//            LOG.error("{}", attributes);
//            if(attributes == null){
//                return false;
//            }
//                
//            if (request instanceof ServletServerHttpRequest) {
//                String userId = ((ServletServerHttpRequest) request).getServletRequest().getParameter("userId");
//                attributes.put("userId", userId);
//                LOG.info("用户唯一标识:" + userId);
//            }
//            if (session != null) {
//                LOG.info("{} - {}", session.getId(), session.getAttribute(SESSION_USER));
//                attributes.put(SESSION_USER, session.getAttribute(SESSION_USER));
//                return true;
//            }
//        }
//        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
            ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
