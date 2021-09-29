package com.ufire.authsso.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @title: HomeController
 * @Author ufiredong
 * @Date: 2021/9/28 15:05
 * @Des:
 * @Version 1.0
 */
@RestController

public class HomeController {
    @GetMapping("/info")
    public String getInfo() {
        return "hello sso";

    }
}
