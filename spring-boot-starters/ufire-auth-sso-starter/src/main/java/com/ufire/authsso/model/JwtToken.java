package com.ufire.authsso.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @title: JwtToken
 * @Author ufiredong
 * @Date: 2021/10/13 14:29
 * @Des:
 * @Version 1.0
 */
@Data
public class JwtToken implements Serializable {

    private String jit;
    private Long exp;
    private String expireDes;
    private String token;

    public JwtToken(String jit, Long exp, String expireDes, String token) {
        this.jit = jit;
        this.exp = exp;
        this.expireDes = expireDes;
        this.token = token;
    }
}
