package com.ufire.server.config;

import com.ufire.handler.CustomlogoutSuccessHandler;
import com.ufire.handler.LoginAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * @title: WebSecurityConfigurer
 * @Author ufiredong
 * @Date: 2021/9/22 15:45
 * @Des:
 * @Version 1.0
 */
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler;

    @Autowired
    CustomlogoutSuccessHandler customlogoutSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("admin"))
                .roles("test")
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("targetUrl");
        successHandler.setAlwaysUseDefaultTargetUrl(false);
        http.authorizeRequests().antMatchers("/login",
                        "logout").
                permitAll();
        http.authorizeRequests().anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(loginAuthenticationSuccessHandler)
                .permitAll()
                .and()
                .httpBasic().disable();
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(customlogoutSuccessHandler)
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true);
    }


}
