package com.ufire.authsso.client.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.PublicKey;

/**
 * @title: RsaPubKey
 * @Author ufiredong
 * @Date: 2021/10/12 10:32
 * @Des:
 * @Version 1.0
 */

@ConfigurationProperties(
        prefix = "rsa.key.pub"
)
@Component
public class RsaPubKey {

    private String keyPath;
    private PublicKey publicKey;

    public String getKeyPath() {
        return keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
}
