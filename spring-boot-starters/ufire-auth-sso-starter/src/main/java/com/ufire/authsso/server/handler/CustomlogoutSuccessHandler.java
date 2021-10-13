package com.ufire.authsso.server.handler;

import com.ufire.authsso.server.properties.SsoServerCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

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

public class CustomlogoutSuccessHandler implements LogoutSuccessHandler {

    SsoServerCookie ssoServerCookie;

    public  CustomlogoutSuccessHandler(SsoServerCookie ssoServerCookie) {
        this.ssoServerCookie = ssoServerCookie;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Cookie jwt = new Cookie("jwt", null);
        jwt.setDomain(ssoServerCookie.getDomain());
        jwt.setPath(ssoServerCookie.getPath());
        jwt.setMaxAge(0);
        httpServletResponse.addCookie(jwt);
        httpServletResponse.sendRedirect("/login");
    }
}
