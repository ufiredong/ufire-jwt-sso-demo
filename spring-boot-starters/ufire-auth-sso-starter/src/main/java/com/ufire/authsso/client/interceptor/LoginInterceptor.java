package com.ufire.authsso.client.interceptor;

import com.ufire.authsso.client.properties.SsoClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @title: LoginInterceptor
 * @Author ufiredong
 * @Date: 2021/9/23 13:13
 * @Des: 校验拦截 token
 * @Version 1.0
 */
@Slf4j
//@Component
public class LoginInterceptor implements HandlerInterceptor {

    SsoClient ssoClient;

    public LoginInterceptor(SsoClient ssoClient) {
        this.ssoClient = ssoClient;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        Map<String, Cookie> cookieMap = readCookieMap(request);
        if (cookieMap.containsKey("jwt")) {
            log.info("jtw-token校验通过");
            return true;
        } else {
            String redirectUrl = ssoClient.getRedirectUrl();
            response.sendRedirect(redirectUrl);
            log.info("jtw-token失效,重定向到sso认证中心"+redirectUrl);
        }
        return false;
    }

    private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

}
