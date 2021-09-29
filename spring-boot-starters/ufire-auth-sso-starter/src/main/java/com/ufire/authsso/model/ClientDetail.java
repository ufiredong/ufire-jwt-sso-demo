package com.ufire.authsso.model;
import java.io.Serializable;
/**
 * @title: ClientDetail
 * @Author ufiredong
 * @Date: 2021/9/28 17:28
 * @Des:
 * @Version 1.0
 */
public class ClientDetail implements Serializable {
    private String clientId;
    private String secret;
    private String targetUrl;

    public ClientDetail(String clientId, String secret, String targetUrl){
        this.clientId=clientId;
        this.secret=secret;
        this.targetUrl=targetUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}
