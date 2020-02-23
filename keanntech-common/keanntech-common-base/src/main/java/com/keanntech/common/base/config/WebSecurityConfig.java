package com.keanntech.common.base.config;

import com.keanntech.common.base.properties.FilterIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
