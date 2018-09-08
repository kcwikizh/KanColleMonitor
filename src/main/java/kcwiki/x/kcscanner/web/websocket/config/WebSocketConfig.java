/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.web.websocket.config;

import kcwiki.x.kcscanner.web.websocket.handler.AdministratorHandler;
import kcwiki.x.kcscanner.web.websocket.handler.GuestHandler;
import kcwiki.x.kcscanner.web.websocket.interceptor.AdministratorInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new AdministratorHandler(), "/websocket/admin")
                .setAllowedOrigins("https://api.x.kcwiki.org", "http://localhost:48080", "http://www.blue-zero.com", "http://coolaf.com", "null")
//                .addInterceptors(createHhandshakeInterceptor())
                ;
        registry.addHandler(new GuestHandler(), "/websocket/guest")
                .setAllowedOrigins("https://api.x.kcwiki.org", "http://localhost:48080", "http://www.blue-zero.com", "http://coolaf.com", "null")
                ;
    }

}
