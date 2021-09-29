package com.ufire.client.endpoint;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @title: AuthCodeService
 * @Author ufiredong
 * @Date: 2021/9/26 14:23
 * @Des:   基于内存的 Auth授权码
 * @Version 1.0
 */
@Component
public class AuthCodeService {
    protected final ConcurrentHashMap<String, Authentication> authorizationCodeStore = new ConcurrentHashMap();
    private RandomValueStringGenerator generator = new RandomValueStringGenerator();
    public String createAuthorizationCode() {
        String code = this.generator.generate();
        return code;
    }
    public void store(String code, Authentication authentication) {
        this.authorizationCodeStore.put(code, authentication);
    }
}
