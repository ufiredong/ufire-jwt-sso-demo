package com.ufire.authsso.client.endpoint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ufire.authsso.client.properties.RsaPubKey;
import com.ufire.authsso.jwt.JwtUtil;
import com.ufire.authsso.model.RestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * @title: IndexController
 * @Author ufiredong
 * @Date: 2021/10/13 9:12
 * @Des:
 * @Version 1.0
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    RsaPubKey rsaPubKey;

    @GetMapping("/info")
    public RestModel info() {

        return new RestModel();
    }

    @GetMapping("/checkToken")
    public RestModel checkToken(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = readCookieMap(request);
        Cookie jwt = cookieMap.get("jwt");
        RestModel restModel = JwtUtil.parserToken(jwt.getValue(), rsaPubKey.getPublicKey());
        return restModel;
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
