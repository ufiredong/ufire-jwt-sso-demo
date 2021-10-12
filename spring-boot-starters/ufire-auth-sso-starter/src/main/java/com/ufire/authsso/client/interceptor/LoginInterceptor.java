//package com.ufire.authsso.client.interceptor;
//
//import com.ufire.authsso.client.properties.RsaPubKey;
//import com.ufire.authsso.client.properties.SsoClient;
//import com.ufire.authsso.jwt.JwtUtil;
//import com.ufire.authsso.tools.HttpClientUtil;
//import com.ufire.authsso.tools.RSAUtil;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.Jwt;
//import io.jsonwebtoken.Jwts;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @title: LoginInterceptor
// * @Author ufiredong
// * @Date: 2021/9/23 13:13
// * @Des: 校验拦截 token
// * @Version 1.0
// */
//@Slf4j
////@Component
//public class LoginInterceptor implements HandlerInterceptor {
//
//    SsoClient ssoClient;
//
//    RsaPubKey rsaPubKey;
//
//    public LoginInterceptor(SsoClient ssoClient, RsaPubKey rsaPubKey) {
//        this.ssoClient = ssoClient;
//        this.rsaPubKey = rsaPubKey;
//    }
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
//        String access_token = HttpClientUtil.getInstance().sendHttpGet(ssoClient.getRefreshTokenUrl() + "?token=");
//        Map<String, Cookie> cookieMap = readCookieMap(request);
//        Cookie jwt = cookieMap.get("jwt");
//        try {
//            Jws<Claims> claimsJws = JwtUtil.parserToken(jwt.getValue(), rsaPubKey.getPublicKey());
//            System.out.println(claimsJws);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        if (cookieMap.containsKey("jwt")) {
//            log.info("jtw-token校验通过");
//            return true;
//        } else {
//            String redirectUrl = ssoClient.getRedirectUrl();
//            response.sendRedirect(redirectUrl);
//            log.info("jtw-token失效,重定向到sso认证中心" + redirectUrl);
//        }
//        return false;
//    }
//
//    private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
//        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
//        Cookie[] cookies = request.getCookies();
//        if (null != cookies) {
//            for (Cookie cookie : cookies) {
//                cookieMap.put(cookie.getName(), cookie);
//            }
//        }
//        return cookieMap;
//    }
//
//}
