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
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * WebSocket 拦截器，用于将用户信息从session中存入map，方便后面websocket请求时从map中找到指定的用户session信息
 * @author iHaru
 * https://blog.csdn.net/qq_28988969/article/details/78104850
 * https://dzone.com/articles/spring-boot-based-websocket
 */
@Component
public class AdministratorInterceptor implements HandshakeInterceptor {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(AdministratorInterceptor.class);

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map attributes) throws Exception {
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			HttpSession session = servletRequest.getServletRequest().getSession();
			attributes.put("sessionId", session.getId());
                        LOG.debug("{}", ((ServletServerHttpRequest) request).getHeaders());
//                        response.setStatusCode(HttpStatus.FORBIDDEN);
		}
		return true;
	}

        @Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
	}
}