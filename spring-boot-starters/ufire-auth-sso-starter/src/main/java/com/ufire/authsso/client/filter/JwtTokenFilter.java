package com.ufire.authsso.client.filter;

import com.ufire.authsso.client.properties.RefreshToken;
import com.ufire.authsso.client.properties.RsaPubKey;
import com.ufire.authsso.client.properties.SsoClient;
import com.ufire.authsso.jwt.JwtUtil;
import com.ufire.authsso.model.RestModel;
import com.ufire.authsso.server.properties.SsoServerCookie;
import lombok.SneakyThrows;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public SsoServerCookie ssoServerCookie;

    @SneakyThrows
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
            String refresh = refresh("http://localhost:8080/token/refresh_token?token=" + token + "&refreshToken=" + refreshToken.getRefreshToken());
            Cookie newtoken = new Cookie("jwt", refresh);
            jwt.setDomain(ssoServerCookie.getDomain());
            jwt.setPath(ssoServerCookie.getPath());
            httpServletResponse.addCookie(jwt);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }


    public JwtTokenFilter(SsoClient ssoClient, RsaPubKey rsaPubKey, RefreshToken refreshToken,SsoServerCookie ssoServerCookie) {
        this.ssoClient = ssoClient;
        this.rsaPubKey = rsaPubKey;
        this.refreshToken = refreshToken;
        this.ssoServerCookie=ssoServerCookie;
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

    private String refresh(String path) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建一个 GET 请求
        HttpGet httpGet = new HttpGet(path);
        // 执行请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
        //取响应的结果
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        String content = EntityUtils.toString(response.getEntity(), "UTF-8");
        System.out.println(content);
        //关闭httpclient
        response.close();
        httpClient.close();
        return content;

    }
}
