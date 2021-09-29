package com.ufire.authsso.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @title: SsoClient
 * @Author ufiredong
 * @Date: 2021/9/24 15:28
 * @Des:
 * @Version 1.0
 */
@ConfigurationProperties(
        prefix = "jwt.sso.client"
)
public class SsoClient {
    private String clientId;
    private String secret;
    private String homeUrl;


    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public void setHomeUrl(String homeUrl) {
        this.homeUrl = homeUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
