package com.ufire.annotation;
import com.ufire.properties.SsoServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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


}
