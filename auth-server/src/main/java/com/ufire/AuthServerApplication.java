package com.ufire;

import com.ufire.annotation.EnableSSOserver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @title: AuthServerApplication
 * @Author ufiredong
 * @Date: 2021/9/22 15:42
 * @Des:
 * @Version 1.0
 */
@SpringBootApplication
@EnableSSOserver
public class AuthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class);
    }
}
