package com.keanntech.authorization.token;

import com.keanntech.common.model.po.SysRole;
import com.keanntech.common.model.po.SysUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class AuthJwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {

        final Map<String, Object> additionalInfo = new HashMap<>(9);
        SysUser user = (SysUser) oAuth2Authentication.getUserAuthentication().getPrincipal();
        List<SysRole> roles = user.getUserRoles();
        List<Long> roleIds = null;
        if(!Objects.isNull(roles) && roles.size() > 0) {
            roleIds = roles.stream().map(SysRole::getId).collect(Collectors.toList());
        }
        additionalInfo.put("id", user.getId());
        additionalInfo.put("name", user.getName());
        additionalInfo.put("userName", user.getUserName());
        additionalInfo.put("jobNumber", user.getJobNumber());
        additionalInfo.put("photo", user.getPhoto());
        additionalInfo.put("roleIds", roleIds);
        additionalInfo.put("superAdmin", user.getSuperAdmin());
        additionalInfo.put("admin", user.getAdmin());
        additionalInfo.put("sysCompanyId", user.getSysCompanyId());
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);
        return oAuth2AccessToken;

    }
}
