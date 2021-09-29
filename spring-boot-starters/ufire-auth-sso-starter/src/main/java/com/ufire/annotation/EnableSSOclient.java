package com.ufire.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @title: EnableSSOclient
 * @Author ufiredong
 * @Date: 2021/9/24 15:46
 * @Des:
 * @Version 1.0
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(SSOclientSelector.class)
public @interface EnableSSOclient {
}
