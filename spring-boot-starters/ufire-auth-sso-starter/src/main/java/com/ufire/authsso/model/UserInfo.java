package com.ufire.authsso.model;

import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * @title: UserInfo
 * @Author ufiredong
 * @Date: 2021/10/11 12:36
 * @Des:
 * @Version 1.0
 */
public class UserInfo implements Serializable {
    private String id;
    private String userName;
    private Authentication authentication;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserInfo(Authentication authentication) {
        this.authentication = authentication;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }
}
