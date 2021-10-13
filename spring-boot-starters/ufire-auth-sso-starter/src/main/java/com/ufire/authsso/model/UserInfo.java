package com.ufire.authsso.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * @title: UserInfo
 * @Author ufiredong
 * @Date: 2021/10/11 12:36
 * @Des:
 * @Version 1.0
 */
@Data
public class UserInfo implements Serializable {
    private String userName;
    private String authentication;
    private JwtToken token;

    public UserInfo(Authentication authentication) {
        this.authentication = JSONObject.toJSONString(authentication);
        this.userName = authentication.getName();
    }

    UserInfo() {
    }
}
