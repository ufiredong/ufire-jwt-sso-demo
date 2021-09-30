package com.ufire.authsso.server.handler;

import com.ufire.authsso.model.ClientDetail;
import com.ufire.authsso.server.service.ClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @title: LoginAuthenticationSuccessHandler
 * @Author ufiredong
 * @Date: 2021/9/28 14:42
 * @Des: 登录成功 后续处理器 ，主要负责判断参数中的 client sercet 是否合法，生成一次性auth_code 授权码，同时 根据授权码 跳转到 access-token
 * 接口获取token
 * @Version 1.0
 */

public class LoginAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    ClientDetailsService clientDetailsService;

    public LoginAuthenticationSuccessHandler(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String referer = request.getHeader("Referer");
        // 如果没有referer 属性
        if (referer.indexOf("targetUrl") == -1) {
            ClientDetail console = clientDetailsService.getConsole();
            String consoleUrl = "clientId=" + console.getClientId() + "&sercet=" + console.getSecret() + "&targetUrl=" + console.getTargetUrl();
            response.sendRedirect("/authServer/authorize?" + consoleUrl);
        } else {
            String targetUrl = referer.substring(referer.indexOf("?") + 1, referer.length());
            response.sendRedirect("/authServer/authorize?" + targetUrl);
        }
    }
}
