package com.keanntech.authorization.handler;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keanntech.authorization.service.IClientDetailsService;
import com.keanntech.authorization.token.AuthJwtTokenEnhancer;
import com.keanntech.common.base.constants.GlobalsConstants;
import com.keanntech.common.base.reponse.ResponseData;
import com.keanntech.common.base.reponse.ResponseDataUtil;
import com.keanntech.common.base.reponse.ResultEnums;
import com.keanntech.common.model.po.SysUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    IClientDetailsService clientDetailsService;

    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE + GlobalsConstants.CHARSET);
        ResponseData resData = null;
        SysUser curtUser = (SysUser)authentication.getPrincipal();
        String clientId = curtUser.getUserName();
        String clientSecret = curtUser.getPassword();
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        if(clientDetails == null){
            resData = ResponseDataUtil.buildError(ResultEnums.ERROR.getCode(),clientId + "不存在！", clientDetails);
        }else if(!StringUtils.contains(clientDetails.getClientSecret(),clientSecret)){
            resData = ResponseDataUtil.buildError(ResultEnums.ERROR.getCode(),"密码不匹配！", clientDetails);
        }else{
            TokenRequest tokenRequest = new TokenRequest(new HashMap<>(),clientId, clientDetails.getScope(),"authorization_code");
            OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request,authentication);
            OAuth2AccessToken token = defaultTokenServices().createAccessToken(oAuth2Authentication);
            resData = ResponseDataUtil.buildSuccess(ResultEnums.SUCCESS.getCode(),"登录成功！",JSON.toJSONString(token));
        }

        ObjectMapper om = new ObjectMapper();
        PrintWriter out = httpServletResponse.getWriter();
        out.write(om.writeValueAsString(resData));
        out.flush();
        out.close();
    }

    public DefaultTokenServices defaultTokenServices() {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter));

        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setTokenEnhancer(tokenEnhancerChain);
        // 这里如果设置为false则不能更新refresh_token，如果需要刷新token的功能需要设置成true
        tokenServices.setSupportRefreshToken(true);
        // 设置上次RefreshToken是否还可以使用 默认为true
        tokenServices.setReuseRefreshToken(false);
        tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1)); // 1天

        return tokenServices;
    }

    public TokenEnhancer tokenEnhancer(){
        return new AuthJwtTokenEnhancer();
    }

    public TokenStore tokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter);
    }
}
