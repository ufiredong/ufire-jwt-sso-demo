package com.ufire.client;
import com.ufire.authsso.annotation.EnableSSOclient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @title: AuthClientApplication
 * @Author ufiredong
 * @Date: 2021/9/22 15:53
 * @Des:
 * @Version 1.0
 */
@SpringBootApplication
@EnableSSOclient
public class AuthClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthClientApplication.class);
    }
}
