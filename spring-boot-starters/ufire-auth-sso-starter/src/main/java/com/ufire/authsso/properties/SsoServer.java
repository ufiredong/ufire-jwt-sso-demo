package com.ufire.authsso.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @title: SsoServer
 * @Author ufiredong
 * @Date: 2021/9/24 15:27
 * @Des:
 * @Version 1.0
 */
@ConfigurationProperties(
        prefix = "jwt.sso.server.allow"
)
public class SsoServer {
    private List<String> clientIds;


    public List<String> getClientIds() {
        return clientIds;
    }

    public void setClientIds(List<String> clientIds) {
        this.clientIds = clientIds;
    }
}
