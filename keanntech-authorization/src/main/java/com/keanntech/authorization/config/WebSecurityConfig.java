package com.keanntech.authorization.config;

import com.keanntech.authorization.handler.AuthenticationAccessDeniedHandler;
import com.keanntech.authorization.handler.LoginFailureHandler;
import com.keanntech.authorization.handler.LoginOutSuccessHandler;
import com.keanntech.authorization.handler.LoginSuccessHandler;
import com.keanntech.authorization.service.IOauthUserService;
import com.keanntech.authorization.utils.BCryptPwEncoder;
import com.keanntech.common.base.properties.FilterIgnoreProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${keanntech.front.loginUrl}")
    private String frontLoginUrl;

    private IOauthUserService oauthUserService;
    private FilterIgnoreProperties ignorePropertiesConfig;
    private AuthenticationAccessDeniedHandler deniedHandler;
    private LoginSuccessHandler loginSuccessHandler;
    private LoginOutSuccessHandler loginOutSuccessHandler;
    private LoginFailureHandler loginFailureHandler;

    @Autowired
    public void setOauthUserService(IOauthUserService oauthUserService) {
        this.oauthUserService = oauthUserService;
    }

    @Autowired
    public void setIgnorePropertiesConfig(FilterIgnoreProperties ignorePropertiesConfig) {
        this.ignorePropertiesConfig = ignorePropertiesConfig;
    }

    @Autowired
    public void setDeniedHandler(AuthenticationAccessDeniedHandler deniedHandler) {
        this.deniedHandler = deniedHandler;
    }

    @Autowired
    public void setLoginSuccessHandler(LoginSuccessHandler loginSuccessHandler) {
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Autowired
    public void setLoginOutSuccessHandler(LoginOutSuccessHandler loginOutSuccessHandler) {
        this.loginOutSuccessHandler = loginOutSuccessHandler;
    }

    @Autowired
    public void setLoginFailureHandler(LoginFailureHandler loginFailureHandler) {
        this.loginFailureHandler = loginFailureHandler;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(oauthUserService).passwordEncoder(new BCryptPwEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(ignorePropertiesConfig.getWebUnInterceptUris())
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage(frontLoginUrl).permitAll()
                .loginProcessingUrl("/oauth/checkLogin").permitAll()
                .failureHandler(loginFailureHandler)
                .successHandler(loginSuccessHandler)
                .and()
                .logout().logoutUrl("/oauth/loginOut").permitAll()
                .logoutSuccessUrl(frontLoginUrl)
                .logoutSuccessHandler(loginOutSuccessHandler)
                .and()
                .exceptionHandling().accessDeniedHandler(deniedHandler);

    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
