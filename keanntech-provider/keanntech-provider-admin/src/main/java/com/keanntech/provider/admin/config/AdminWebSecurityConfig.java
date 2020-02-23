package com.keanntech.provider.admin.config;

import com.keanntech.common.base.config.WebSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class AdminWebSecurityConfig extends WebSecurityConfig {

}
