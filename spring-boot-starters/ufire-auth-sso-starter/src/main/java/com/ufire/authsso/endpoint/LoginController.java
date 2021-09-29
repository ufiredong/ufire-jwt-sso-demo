package com.ufire.authsso.endpoint;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @title: LoginController
 * @Author ufiredong
 * @Date: 2021/9/24 18:04
 * @Des:
 * @Version 1.0
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/logout")
    public String logout() {
        return "login";
    }
    @GetMapping("/index")
    public String index(){
        return "index";
    }

}
