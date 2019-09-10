/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 *
 * @author iHaru
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().and() //开启cors配置过滤器注入
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
            .authorizeRequests().anyRequest().permitAll().and()
//                .csrf().csrfTokenRepository(new CookieCsrfTokenRepository()).and()   //API服务器开启csrf验证会导致接口403错误
            .csrf().ignoringAntMatchers("/actuator/**", "/debug/**", "/api/**")
//            .csrf().disable()
            ;
    }
}
