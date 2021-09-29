package com.ufire.authsso.endpoint;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @title: IndexController
 * @Author ufiredong
 * @Date: 2021/9/23 11:48
 * @Des:
 * @Version 1.0
 */
@Controller
public class IndexController {
    @GetMapping("/home")
    public  String login(){
        return "index";
    }

}
