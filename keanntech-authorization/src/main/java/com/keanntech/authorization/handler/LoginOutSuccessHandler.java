package com.keanntech.authorization.handler;

import cn.hutool.core.util.StrUtil;
import com.keanntech.common.base.constants.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginOutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

//    @Autowired
//    @Qualifier("redisTokenStore")
//    TokenStore redisTokenStore;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//        String token = httpServletRequest.getHeader(SecurityConstants.AUTH_HEADER);
//        String tokenValue = token.replace(SecurityConstants.BEARER_TYPE, StrUtil.EMPTY).trim();
//        OAuth2AccessToken accessToken = redisTokenStore.readAccessToken(tokenValue);
//        if (accessToken != null || !StrUtil.isBlank(accessToken.getValue())) {
//            redisTokenStore.removeAccessToken(accessToken);
//            OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
//            redisTokenStore.removeRefreshToken(refreshToken);
//        }
    }
}
