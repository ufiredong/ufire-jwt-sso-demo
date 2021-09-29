package com.ufire.authsso.annotation;

import com.ufire.authsso.endpoint.ClientDetailsService;
import com.ufire.authsso.endpoint.EndpointScan;
import com.ufire.authsso.handler.CustomlogoutSuccessHandler;
import com.ufire.authsso.handler.LoginAuthenticationSuccessHandler;
import com.ufire.authsso.properties.SsoServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @title: SSOserverSelector
 * @Author ufiredong
 * @Date: 2021/9/24 15:48
 * @Des:
 * @Version 1.0
 */
@Configuration
@EnableConfigurationProperties({SsoServer.class})
public class SSOserverSelector {
    @Autowired
    SsoServer ssoServer;

    @Bean
    public CustomlogoutSuccessHandler customlogoutSuccessHandler() {
        return new CustomlogoutSuccessHandler();
    }


    @Bean
    public LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler() {
        return new LoginAuthenticationSuccessHandler();
    }

    @Bean
    public EndpointScan endpointScan() {
        return new EndpointScan();
    }

    @Bean
    public ClientDetailsService ClientDetailsService(){
        return  new ClientDetailsService();
    }

}
