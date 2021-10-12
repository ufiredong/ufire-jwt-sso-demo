package com.ufire.authsso.configuration;
import com.ufire.authsso.client.properties.RsaPubKey;
import com.ufire.authsso.tools.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
    RsaPubKey rsaPubKey;

    @PostConstruct
    public void initPublicKey() throws Exception {
        rsaPubKey.setPublicKey(RSAUtil.getPublicKey(rsaPubKey.getKeyPath()));
    }

}
