package com.keanntech.authorization.config;

import com.keanntech.authorization.service.IClientDetailsService;
import com.keanntech.authorization.service.IOauthUserService;
import com.keanntech.authorization.token.AuthJwtTokenEnhancer;
import com.keanntech.authorization.utils.BCryptPwEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private IClientDetailsService clientDetailsService;
    private IOauthUserService oauthUserService;
    private AuthenticationManager authenticationManager;
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    public void setClientDetailsService(IClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    @Autowired
    public void setOauthUserService(IOauthUserService oauthUserService) {
        this.oauthUserService = oauthUserService;
    }

    @Autowired
    @Qualifier("authenticationManagerBean")
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtAccessTokenConverter(JwtAccessTokenConverter jwtAccessTokenConverter) {
        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .passwordEncoder(new BCryptPwEncoder())
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter));

        endpoints.authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter)
                .tokenEnhancer(tokenEnhancerChain)
                .tokenStore(tokenStore())
                .userDetailsService(oauthUserService);

        DefaultTokenServices tokenServices = (DefaultTokenServices) endpoints.getDefaultAuthorizationServerTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1)); // 1天
        endpoints.tokenServices(tokenServices);
        super.configure(endpoints);
    }

    @Bean
    public TokenEnhancer tokenEnhancer(){
        return new AuthJwtTokenEnhancer();
    }

    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter);
    }
}
