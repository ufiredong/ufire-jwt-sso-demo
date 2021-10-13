package com.ufire.server.config;
import com.ufire.authsso.server.handler.CustomlogoutSuccessHandler;
import com.ufire.authsso.server.handler.LoginAuthenticationSuccessHandler;
import com.ufire.authsso.model.ClientDetail;
import com.ufire.authsso.server.service.ClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

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
    ClientDetailsService clientDetailsService;

    @Autowired
    LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler;

    @Autowired
    CustomlogoutSuccessHandler customlogoutSuccessHandler;


    @PostConstruct
    public void initClientDetailsService() {
        List<ClientDetail> clientDetailsList = new ArrayList<>();
        clientDetailsList.add(new ClientDetail("client1", "123456", "http://localhost:8099"));
        clientDetailsList.add(new ClientDetail("console", "123456", "http://localhost:8081"));
        clientDetailsList.add(new ClientDetail("client2", "123456", "http://localhost:8089"));
        clientDetailsService.setClientDetails(clientDetailsList);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("admin"))
                .roles("ADMIN")
                .and()
                .withUser("ufire")
                .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("ufire"))
                .roles("SUB_ADMIN")
        ;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("targetUrl");
        successHandler.setAlwaysUseDefaultTargetUrl(false);
        http.authorizeRequests().antMatchers("/login",
                        "logout", "/token/refresh_token").
                permitAll();
        http
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
