/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.web.websocket.config;

import kcwiki.x.kcscanner.web.websocket.handler.AdministratorHandler;
import kcwiki.x.kcscanner.web.websocket.handler.GuestHandler;
import kcwiki.x.kcscanner.web.websocket.interceptor.AdministratorInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    
    @Autowired
    private GuestHandler guestHandler;
    @Autowired
    private AdministratorHandler administratorHandler;
    @Autowired
    private AdministratorInterceptor administratorInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(administratorHandler, "/websocket/admin")
                .setAllowedOrigins("https://api.x.kcwiki.org", "http://localhost:48080", "http://www.blue-zero.com", "http://coolaf.com", "null")
                .addInterceptors(administratorInterceptor)
                ;
        registry.addHandler(guestHandler, "/websocket/guest")
                .setAllowedOrigins("https://api.x.kcwiki.org", "http://localhost:48080", "http://www.blue-zero.com", "http://coolaf.com", "null")
                ;
    }

}
