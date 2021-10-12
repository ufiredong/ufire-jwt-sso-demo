package com.ufire.authsso.model;

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
    private String id;
    private String userName;
    private Authentication authentication;

    UserInfo(){
    }
}
