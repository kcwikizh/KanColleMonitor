/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kcwiki.x.web.websocket.interceptor;

import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * WebSocket 拦截器，用于将用户信息从session中存入map，方便后面websocket请求时从map中找到指定的用户session信息
 * @author x5171
 *
 */
public class AdministratorInterceptor implements HandshakeInterceptor {
    private static final String SESSION_USER = "user";

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, 
            WebSocketHandler wsHandler, Map<String, Object> map) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
            HttpSession session = serverHttpRequest.getServletRequest().getSession();
            if (session != null) {
                map.put(SESSION_USER, session.getAttribute(SESSION_USER));
            }

        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
            ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
