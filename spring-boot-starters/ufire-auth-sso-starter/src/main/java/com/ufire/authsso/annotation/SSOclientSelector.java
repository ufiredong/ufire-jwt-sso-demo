package com.ufire.authsso.annotation;
import com.ufire.authsso.client.properties.SsoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @title: SSOclientSelector
 * @Author ufiredong
 * @Date: 2021/9/24 15:47
 * @Des:
 * @Version 1.0
 */
@Configuration
@EnableConfigurationProperties({SsoClient.class})
@ComponentScan(value = "com.ufire.authsso.client")
public class SSOclientSelector {
    @Autowired
    SsoClient ssoClient;
}
