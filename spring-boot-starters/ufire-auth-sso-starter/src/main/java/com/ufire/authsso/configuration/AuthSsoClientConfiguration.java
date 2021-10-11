package com.ufire.authsso.configuration;

import com.ufire.authsso.client.interceptor.LoginInterceptor;
import com.ufire.authsso.client.properties.SsoClient;
import com.ufire.authsso.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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


    @Configuration
    class Webconfig extends WebMvcConfigurerAdapter {
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            //注册自定义拦截器，添加拦截路径和排除拦截路径
            registry.addInterceptor(new LoginInterceptor(ssoClient)).addPathPatterns("/*").excludePathPatterns("**/logout");
            super.addInterceptors(registry);
        }
    }

}
