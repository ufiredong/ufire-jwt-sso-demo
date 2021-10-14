package com.ufire.authsso.configuration;

import com.ufire.authsso.client.properties.RsaPubKey;
import com.ufire.authsso.server.handler.CustomlogoutSuccessHandler;
import com.ufire.authsso.server.handler.LoginAuthenticationSuccessHandler;
import com.ufire.authsso.server.properties.RsaPriKey;
import com.ufire.authsso.server.properties.SsoServerCookie;
import com.ufire.authsso.server.service.ClientDetailsService;
import com.ufire.authsso.tools.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;

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
@Slf4j
public class AuthSsoServerConfiguration {

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    RsaPubKey rsaPubKey;

    @Autowired
    ClientDetailsService clientDetailsService;

    @Autowired
    RsaPriKey rsaPriKey;

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

    @Bean
    @ConditionalOnClass(RsaPubKey.class)
    RsaPubKey rsaPubKey() {
        return new RsaPubKey();
    }


    /**
     * 加载私钥
     *
     * @return
     */
    @PostConstruct
    public void initRsaKey() throws Exception {
        log.info("-----------加载公钥/私钥文件--------------------");
        ClassPathResource pub = new ClassPathResource("key/rsa-jwt.pub");
        ClassPathResource pri = new ClassPathResource("key/rsa-jwt.pri");
        if (!pub.exists()) {
            log.info("key/rsa-jwt.pub公钥文件找不到！");
        } else {
            rsaPubKey.setPublicKey(RSAUtil.getPublicKey(pub.getInputStream()));
        }
        if (!pri.exists()) {
            log.info("key/rsa-jwt.pub私钥钥文件找不到！");
        } else {
            rsaPubKey.setPublicKey(RSAUtil.getPublicKey(pri.getInputStream()));
        }
    }
}
