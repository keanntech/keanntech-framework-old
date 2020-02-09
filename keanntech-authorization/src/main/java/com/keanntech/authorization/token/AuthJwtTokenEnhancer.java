package com.keanntech.authorization.token;

import com.keanntech.common.model.po.SysUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

public class AuthJwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {

        final Map<String, Object> additionalInfo = new HashMap<>(4);
        SysUser user = (SysUser) oAuth2Authentication.getUserAuthentication().getPrincipal();
        additionalInfo.put("id", user.getId());
        additionalInfo.put("name", user.getName());
        additionalInfo.put("username", user.getUserName());
        additionalInfo.put("jobNumber", user.getJobNumber());
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);
        return oAuth2AccessToken;

    }
}
