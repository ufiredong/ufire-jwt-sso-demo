package com.ufire.authsso.server.endpoint;

import com.alibaba.fastjson.JSONObject;
import com.ufire.authsso.client.properties.RsaPubKey;
import com.ufire.authsso.jwt.JwtUtil;
import com.ufire.authsso.model.ClientDetail;
import com.ufire.authsso.model.RestModel;
import com.ufire.authsso.model.UserInfo;
import com.ufire.authsso.server.properties.RsaPriKey;
import com.ufire.authsso.server.properties.SsoServerCookie;
import com.ufire.authsso.server.service.AuthCodeService;
import com.ufire.authsso.server.service.ClientDetailsService;
import com.ufire.authsso.tools.AesUtil;
import com.ufire.authsso.tools.RSAUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * @title: TokenController
 * @Author ufiredong
 * @Date: 2021/9/26 9:37
 * @Des:
 * @Version 1.0
 */
@Controller
@RequestMapping("/authServer")
@Slf4j
public class TokenController {
    @Autowired

    private RsaPubKey rsaPubKey;

    @Autowired

    private RsaPriKey rsaPriKey;
    @Autowired

    public AuthCodeService authCodeService;

    @Autowired

    public ClientDetailsService clientDetailsService;

    @Autowired

    public SsoServerCookie ssoServerCookie;


    /**
     * 获取 auth_code 授权码
     *
     * @return
     */
    @GetMapping("/authorize")
    public ModelAndView auth_code(Map<String, Object> model, @RequestParam Map<String, String> parameters, Principal principal) {
        ModelAndView modelAndView = new ModelAndView(new RedirectView("/authServer/access_token"));
        String clientId = parameters.get("clientId");
        String sercet = parameters.get("sercet");
        String targetUrl = parameters.get("targetUrl");
        // 判断 是否认证通过  生成 Authentication对象
        if (principal instanceof Authentication && ((Authentication) principal).isAuthenticated()) {
            Authentication authentication = (Authentication) principal;
            User user = (User) authentication.getPrincipal();
            log.info("用户:{}已经登录获得Authentication凭证，权限:{}", user.getUsername(), user.getAuthorities());
            // client 是否在auth服务端有备案， 判断client的合法性。 颁发auth_code 授权码
            if (clientDetailsService.authorize(new ClientDetail(clientId, sercet, targetUrl))) {
                // 生成随机授权码  缓存到 ConcurrentHashMap 中
                String auth_code = authCodeService.createAuthorizationCode();
                authCodeService.store(auth_code, authentication);
                parameters.put("auth_code", auth_code);
                log.info("子系统:{}在服务端有备案可以颁发授权码:{}", clientId, auth_code);
                modelAndView.addAllObjects(parameters);
                log.info("成功获得auth_code:{}重定向到/authServer/access_token获取access-token", auth_code);
                return modelAndView;
            }
        }
        return null;
    }


    /**
     * 获取 access_token
     *
     * @return
     */
    @GetMapping("/access_token")
    public ModelAndView access_token(HttpServletResponse response, @RequestParam Map<String, String> parameters) throws Exception {
        String auth_code = parameters.get("auth_code");
        Authentication authentication = authCodeService.authorizationCodeStore.get(parameters.get("auth_code"));
        if (Objects.nonNull(authentication)) {
            authCodeService.authorizationCodeStore.remove(auth_code);
            log.info("授权码auth_code:{}从内存移除，保证只能使用一次", auth_code);
            ModelAndView modelAndView = new ModelAndView("redirect:" + parameters.get("targetUrl"));
            String token = JwtUtil.generateToken(JSONObject.toJSONString(authentication), rsaPriKey.getPrivateKey(), 30);
            Cookie jwt = new Cookie("jwt", token);
            jwt.setDomain(ssoServerCookie.getDomain());
            jwt.setPath(ssoServerCookie.getPath());
            log.info("成功获得access-token:{}", token);
            log.info("设置cookie,domain:{},path:{},携带cookie重定向回:{}", jwt.getDomain(), jwt.getPath(), parameters.get("targetUrl"));
            response.addCookie(jwt);
            return modelAndView;
        } else {
            return null;
        }

    }

    @RequestMapping("/token")
    @RestController
    class RefreshTokenController{
        @GetMapping("refresh_token")
        public String refresh_token(String token, String refreshToken) throws Exception {
            String newtoken = null;
            RestModel res = JwtUtil.parserToken(refreshToken, rsaPubKey.getPublicKey());
            // 永久token有效
            if (res.getErrorCode() == 0) {
                RestModel token_res = JwtUtil.parserToken(token, rsaPubKey.getPublicKey());
//                if (token_res.getErrorCode() == 2) {
//                    Object data = token_res.getData();
//                    newtoken = JwtUtil.generateToken(JSONObject.toJSONString(data), rsaPriKey.getPrivateKey(), 30);
//                }
                Object data = token_res.getData();
                newtoken = JwtUtil.generateToken(JSONObject.toJSONString(data), rsaPriKey.getPrivateKey(), 30);
            } else {
                return null;
            }
            return newtoken;
        }
    }
}
