package com.ufire.authsso.client.filter;

import com.ufire.authsso.client.properties.RefreshToken;
import com.ufire.authsso.client.properties.RsaPubKey;
import com.ufire.authsso.client.properties.SsoClient;
import com.ufire.authsso.jwt.JwtUtil;
import com.ufire.authsso.model.RestModel;
import com.ufire.authsso.tools.HttpClientUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @title: JwtTokenFilter
 * @Author ufiredong
 * @Date: 2021/10/12 12:45
 * @Des:
 * @Version 1.0
 */
public class JwtTokenFilter extends OncePerRequestFilter {

    private static Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);
    private final String tokenHeader = "Authorization";
    private SsoClient ssoClient;
    private RsaPubKey rsaPubKey;
    private RefreshToken refreshToken;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        String authHeader = httpServletRequest.getHeader(this.tokenHeader);
        String redirectUrl = ssoClient.getRedirectUrl();
        Map<String, Cookie> cookieMap = readCookieMap(httpServletRequest);
        Cookie jwt = cookieMap.get("jwt");
        if (Objects.nonNull(authHeader)) {
            token = authHeader;
        } else {
            if (Objects.nonNull(jwt)) {
                token = jwt.getValue();
            } else {
                httpServletResponse.sendRedirect(redirectUrl);
            }
        }
        RestModel restModel = JwtUtil.parserToken(token, rsaPubKey.getPublicKey());
        if (restModel.getErrorCode() == 1) {
            logger.info(restModel.getErrorMessage() + redirectUrl);
            httpServletResponse.sendRedirect(redirectUrl);
        }
        if (restModel.getErrorCode() == 2) {
            logger.info(restModel.getErrorMessage());
            String newtoken = HttpClientUtil.getInstance().sendHttpGet("http://localhost:8080/authServer/refresh_token?token=" + token + "&refresh_token="+refreshToken.getRefreshToken());
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }


    public JwtTokenFilter(SsoClient ssoClient, RsaPubKey rsaPubKey, RefreshToken refreshToken) {
        this.ssoClient = ssoClient;
        this.rsaPubKey = rsaPubKey;
        this.refreshToken=refreshToken;
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
