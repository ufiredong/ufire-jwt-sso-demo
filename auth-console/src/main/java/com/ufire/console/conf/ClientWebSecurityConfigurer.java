package com.ufire.console.conf;
import com.ufire.authsso.client.filter.JwtTokenFilter;
import com.ufire.authsso.client.properties.RsaPubKey;
import com.ufire.authsso.client.properties.SsoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * @description:
 * @author: fengandong
 * @time: 2021/9/29 22:59
 */
@Configuration
public class ClientWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    SsoClient ssoClient;

    @Autowired
    RsaPubKey rsaPubKey;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new JwtTokenFilter(ssoClient, rsaPubKey), UsernamePasswordAuthenticationFilter.class);
        http.csrf().disable();
    }
}
