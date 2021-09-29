package com.ufire.authsso.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @title: CustomlogoutSuccessHandler
 * @Author ufiredong
 * @Date: 2021/9/29 14:46
 * @Des:
 * @Version 1.0
 */
@Component

public class CustomlogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Cookie jwt = new Cookie("jwt", null);
        jwt.setDomain("localhost");
        jwt.setPath("/");
        jwt.setMaxAge(0);
        httpServletResponse.addCookie(jwt);
        httpServletResponse.sendRedirect("/login");
    }
}
