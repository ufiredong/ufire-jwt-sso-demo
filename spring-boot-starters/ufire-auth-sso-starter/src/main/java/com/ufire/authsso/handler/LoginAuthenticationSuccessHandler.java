package com.ufire.authsso.handler;
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

/**
 * @title: LoginAuthenticationSuccessHandler
 * @Author ufiredong
 * @Date: 2021/9/28 14:42
 * @Des: 登录成功 后续处理器 ，主要负责判断参数中的 client sercet 是否合法，生成一次性auth_code 授权码，同时 根据授权码 跳转到 access-token
 * 接口获取token
 * @Version 1.0
 */

public class LoginAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    ClientDetailsService clientDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
//        System.out.println(request.getQueryString());
//        String referer = request.getHeader("Referer");
//        System.out.println(referer);
//        String targetUrl = referer.substring(referer.indexOf("=") + 1, referer.length());
//        response.sendRedirect(targetUrl);
        ClientDetail clientDetail = new ClientDetail("1","2","www");
        response.sendRedirect("/authServer/authorize?clientId=client1&sercet=123456&targetUrl=http://localhost:8099");
//        if (clientDetailsService.authorize(clientDetail)) {
//
//        } else {
//            response.sendRedirect("/login");
////            response.sendError(403,"client不存在");
////            response.setCharacterEncoding("utf-8");
////            response.setContentType("application/json; charset=utf-8");
////            PrintWriter writer = response.getWriter();
////            Map<String, String> map = new HashMap<>();
////            map.put("-1", "client,sercet 不合法或者不存在");
////            writer.write(map.toString());
//        }


    }
}
