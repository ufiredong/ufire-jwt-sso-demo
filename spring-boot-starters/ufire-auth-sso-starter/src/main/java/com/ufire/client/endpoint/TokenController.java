package com.ufire.client.endpoint;

import com.ufire.model.ClientDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.Objects;

/**
 * @title: TokenController
 * @Author ufiredong
 * @Date: 2021/9/26 9:37
 * @Des:
 * @Version 1.0
 */
@Controller
@RequestMapping("/authServer")
public class TokenController {
    @Autowired

    AuthCodeService authCodeService;


    @Autowired

    ClientDetailsService clientDetailsService;


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
            // client 是否在auth服务端有备案， 判断client的合法性。 颁发auth_code 授权码
            if (clientDetailsService.authorize(new ClientDetail(clientId, sercet, targetUrl))) {
                // 生成随机授权码  缓存到 ConcurrentHashMap 中
                String auth_code = authCodeService.createAuthorizationCode();
                authCodeService.store(auth_code, authentication);
                parameters.put("auth_code", auth_code);
                modelAndView.addAllObjects(parameters);
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
    public ModelAndView access_token(HttpServletResponse response, @RequestParam Map<String, String> parameters) throws IOException {
        String auth_code = parameters.get("auth_code");
        Authentication authentication = authCodeService.authorizationCodeStore.get(parameters.get("auth_code"));
        if (Objects.nonNull(authentication)) {
            authCodeService.authorizationCodeStore.remove(auth_code);
            ModelAndView modelAndView = new ModelAndView("redirect:" + parameters.get("targetUrl"));
            Cookie jwt = new Cookie("jwt", "123456");
            jwt.setDomain("localhost");
            jwt.setPath("/");
            response.addCookie(jwt);
//                    String new_url = parameters.get("targetUrl");
//        String html = "<script type='text/javascript'>location.href='"+parameters.get("targetUrl")+"';</script>";
//        response.getWriter().print(html);
            return modelAndView;
        } else {
            return null;
        }

    }


    /**
     * 刷新 access_token
     *
     * @return
     */
    @GetMapping("refresh_token")
    public String refresh_token() {
        return null;
    }
}
