package com.ufire.console;

import com.ufire.authsso.annotation.EnableSSOclient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @title: com.ufire.console.AuthConsoleApplication
 * @Author ufiredong
 * @Date: 2021/9/30 13:03
 * @Des:
 * @Version 1.0
 */
@SpringBootApplication
@EnableSSOclient
public class AuthConsoleApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthConsoleApplication.class);
    }
}
