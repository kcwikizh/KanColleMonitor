/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

/**
 *
 * @author x5171
 * https://www.jianshu.com/p/d05303d34222
 * http://www.ruanyifeng.com/blog/2016/04/cors.html
 * https://www.jianshu.com/p/af8360b83a9f
 */
@Configuration
//@EnableWebMvc
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
//           .allowedOrigins("http://localhost:48080", "http://localhost:58080", "null")
           .allowedOrigins("https://baidu.com")
           .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
           .maxAge(3600)
           .allowCredentials(true);
    
    }

}

