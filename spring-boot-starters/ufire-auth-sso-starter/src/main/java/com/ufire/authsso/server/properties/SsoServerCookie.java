package com.ufire.authsso.server.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @title: SsoServerCookie
 * @Author ufiredong
 * @Date: 2021/9/30 11:25
 * @Des:
 * @Version 1.0
 */
@ConfigurationProperties(
        prefix = "jwt.sso.server.cookie"
)
@Component
public class SsoServerCookie {

    private String domain;
    private String path;
    private Integer maxAge;
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }
}
