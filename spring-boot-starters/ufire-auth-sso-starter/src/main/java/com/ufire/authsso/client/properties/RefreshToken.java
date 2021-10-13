package com.ufire.authsso.client.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @title: RefreshToken  内置服务端  永久 refresh-token  用于 刷新即将过期的 access-token
 * @Author ufiredong
 * @Date: 2021/10/9 15:37
 * @Des:
 * @Version 1.0
 */
@ConfigurationProperties(
        prefix = "jwt.sso.refresh-token"
)
@Component
public class RefreshToken {


    private String refreshToken;

    private String refreshTokenUrl;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshTokenUrl() {
        return refreshTokenUrl;
    }

    public void setRefreshTokenUrl(String refreshTokenUrl) {
        this.refreshTokenUrl = refreshTokenUrl;
    }
}
