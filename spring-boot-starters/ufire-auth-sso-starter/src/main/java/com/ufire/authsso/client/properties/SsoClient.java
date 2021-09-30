package com.ufire.authsso.client.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

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
@Component
public class SsoClient {
    private String clientId;

    private String secret;

    private String homeUrl;

    private String accessTokenUrl;

    private String authorizeUrl;

    private String refreshTokenUrl;


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

    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }

    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    public String getAuthorizeUrl() {
        return authorizeUrl;
    }

    public void setAuthorizeUrl(String authorizeUrl) {
        this.authorizeUrl = authorizeUrl;
    }

    public String getRefreshTokenUrl() {
        return refreshTokenUrl;
    }

    public void setRefreshTokenUrl(String refreshTokenUrl) {
        this.refreshTokenUrl = refreshTokenUrl;
    }
}
