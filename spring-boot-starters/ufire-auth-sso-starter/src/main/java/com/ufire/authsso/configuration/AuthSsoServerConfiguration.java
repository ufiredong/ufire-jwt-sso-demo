package com.ufire.authsso.configuration;

import com.ufire.authsso.server.endpoint.LoginController;
import com.ufire.authsso.server.endpoint.TokenController;
import com.ufire.authsso.server.handler.CustomlogoutSuccessHandler;
import com.ufire.authsso.server.handler.LoginAuthenticationSuccessHandler;
import com.ufire.authsso.server.properties.SsoServer;
import com.ufire.authsso.server.properties.SsoServerCookie;
import com.ufire.authsso.server.service.ClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @title: AuthSsoServerConfiguration
 * @Author ufiredong
 * @Date: 2021/9/30 10:48
 * @Des:
 * @Version 1.0
 */
@Configuration
@ConditionalOnWebApplication
@ComponentScan(value = "com.ufire.authsso.server")
@EnableConfigurationProperties
public class AuthSsoServerConfiguration {
    @Autowired
    ClientDetailsService clientDetailsService;

    @Autowired

    SsoServerCookie ssoServerCookie;

    /**
     * 登录认证凭证通过后事件处理器
     *
     * @return
     */
    @Bean
    LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler() {
        return new LoginAuthenticationSuccessHandler(clientDetailsService);
    }

    /**
     * 自定义登出成功事件处理器
     *
     * @return
     */
    @Bean
    CustomlogoutSuccessHandler customlogoutSuccessHandler() {
        return new CustomlogoutSuccessHandler(ssoServerCookie);
    }
}
