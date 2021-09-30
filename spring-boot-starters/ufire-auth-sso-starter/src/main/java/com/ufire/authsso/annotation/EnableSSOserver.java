package com.ufire.authsso.annotation;

import com.ufire.authsso.configuration.AuthSsoServerConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @title: EnableSSOserver
 * @Author ufiredong
 * @Date: 2021/9/24 15:44
 * @Des:
 * @Version 1.0
 */


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(AuthSsoServerConfiguration.class)
public @interface EnableSSOserver {
}
