package com.ufire.authsso.configuration;
import com.ufire.authsso.client.properties.RsaPubKey;
import com.ufire.authsso.server.handler.CustomlogoutSuccessHandler;
import com.ufire.authsso.server.handler.LoginAuthenticationSuccessHandler;
import com.ufire.authsso.server.properties.RsaPriKey;
import com.ufire.authsso.server.properties.SsoServerCookie;
import com.ufire.authsso.server.service.ClientDetailsService;
import com.ufire.authsso.tools.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
public class AuthSsoServerConfiguration {

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
    RsaPubKey rsaPubKey(){
        return new RsaPubKey();
    }

    /**
     * 加载私钥
     * @return
     */
    @PostConstruct
    public void initPrivateKey() throws Exception {
        rsaPriKey.setPrivateKey(RSAUtil.getPrivateKey(rsaPriKey.getKeyPath()));
        rsaPubKey.setPublicKey(RSAUtil.getPublicKey(rsaPubKey.getKeyPath()));
    }
}
