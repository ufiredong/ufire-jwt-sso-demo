package com.ufire.authsso.endpoint;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @title: EndpointScan
 * @Author ufiredong
 * @Date: 2021/9/24 18:05
 * @Des:
 * @Version 1.0
 */


@Configuration
@ConditionalOnProperty(
        value = {"jwt.scan", "jwt.sanee"},
        havingValue = "true",
        matchIfMissing = true
)
@ComponentScan
public class EndpointScan {
    public EndpointScan() {
    }
}
