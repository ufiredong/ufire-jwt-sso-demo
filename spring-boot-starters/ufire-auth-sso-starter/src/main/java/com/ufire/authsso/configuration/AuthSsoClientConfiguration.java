package com.ufire.authsso.configuration;

import com.ufire.authsso.client.properties.RsaPubKey;
import com.ufire.authsso.server.properties.SsoServerCookie;
import com.ufire.authsso.tools.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @title: AuthSsoClientConfiguration
 * @Author ufiredong
 * @Date: 2021/9/30 10:49
 * @Des:
 * @Version 1.0
 */
@Configuration
@Slf4j
@ComponentScan(value = "com.ufire.authsso.client")
public class AuthSsoClientConfiguration {
    @Autowired
    RsaPubKey rsaPubKey;

    @PostConstruct
    public void initPublicKey() throws Exception {
        log.info("-----------加载公钥钥文件--------------------");
        ClassPathResource classPathResource = new ClassPathResource("key/rsa-jwt.pub");
        boolean exists = classPathResource.exists();
        if (!exists) {
            log.info("key/rsa-jwt.pub公钥文件找不到！");
        } else {
            rsaPubKey.setPublicKey(RSAUtil.getPublicKey(classPathResource.getInputStream()));
        }
    }


    @Bean
    @ConditionalOnClass(SsoServerCookie.class)
    public SsoServerCookie ssoServerCookie() {
        return new SsoServerCookie();
    }

}
