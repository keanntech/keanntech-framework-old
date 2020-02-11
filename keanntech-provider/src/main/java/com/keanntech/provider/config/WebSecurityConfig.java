package com.keanntech.provider.config;

import com.keanntech.common.base.properties.FilterIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    FilterIgnoreProperties ignorePropertiesConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(ignorePropertiesConfig.getWebUnInterceptUris())
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf()
                .disable();
    }
}
