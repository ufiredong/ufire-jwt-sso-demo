package com.ufire.config;

import com.ufire.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @title: WebConfig
 * @Author ufiredong
 * @Date: 2021/9/23 13:25
 * @Des:
 * @Version 1.0
 */
@Configuration
public class WebConfig  extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/*").excludePathPatterns("**/logout");
        super.addInterceptors(registry);
    }
}
