package com.ufire.authsso.server.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;

/**
 * @title: RsaPriKey
 * @Author ufiredong
 * @Date: 2021/10/12 10:24
 * @Des:
 * @Version 1.0
 */
@ConfigurationProperties(
        prefix = "rsa.key.pri"
)
@Component
public class RsaPriKey {

    private  String keyPath;

    private PrivateKey privateKey;

    public String getKeyPath() {
        return keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

}
