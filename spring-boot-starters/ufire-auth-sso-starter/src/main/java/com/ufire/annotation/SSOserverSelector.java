package com.ufire.annotation;
import com.ufire.endpoint.EndpointScan;
import com.ufire.properties.SsoServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
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
@ConditionalOnWebApplication
@EnableConfigurationProperties({SsoServer.class})
public class SSOserverSelector {
    @Autowired
    SsoServer ssoServer;
    @Bean
    @ConditionalOnProperty(prefix="jwt.auth.server",name = "enabled")
    public EndpointScan endpointScan(){
        return  new EndpointScan();
    }
}
