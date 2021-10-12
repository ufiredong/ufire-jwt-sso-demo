package com.ufire.authsso.configuration;

import com.ufire.authsso.client.interceptor.LoginInterceptor;
import com.ufire.authsso.client.properties.RsaPubKey;
import com.ufire.authsso.client.properties.SsoClient;
import com.ufire.authsso.jwt.JwtUtil;
import com.ufire.authsso.tools.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;

/**
 * @title: AuthSsoClientConfiguration
 * @Author ufiredong
 * @Date: 2021/9/30 10:49
 * @Des:
 * @Version 1.0
 */
@Configuration
@ComponentScan(value = "com.ufire.authsso.client")
public class AuthSsoClientConfiguration {
    @Autowired
    SsoClient ssoClient;

    @Autowired
    RsaPubKey rsaPubKey;

    @Configuration
    class Webconfig extends WebMvcConfigurerAdapter {
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            //注册自定义拦截器，添加拦截路径和排除拦截路径
            registry.addInterceptor(new LoginInterceptor(ssoClient, rsaPubKey)).addPathPatterns("/*").excludePathPatterns("**/logout");
            super.addInterceptors(registry);
        }
    }

    @PostConstruct
    public void initPublicKey() throws Exception {
        rsaPubKey.setPublicKey(RSAUtil.getPublicKey(rsaPubKey.getKeyPath()));
    }

}
