package com.keanntech.provider.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.keanntech.common.base.constants.SecurityConstants;
import com.keanntech.common.model.auth.OauthClient;
import com.keanntech.common.model.po.SysUser;
import com.keanntech.provider.mapper.AuthorizationMapper;
import com.keanntech.provider.service.IOauthService;
import com.keanntech.provider.service.ISysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

@Service
public class OauthServiceImpl implements IOauthService {

    @Autowired
    ISysUserService sysUserService;

    @Autowired
    AuthorizationMapper authorizationMapper;

    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;

    @Override
    public OauthClient loadClientByClientId(String clientId) {
        return authorizationMapper.loadClientByClientId(clientId);
    }

    @Override
    public SysUser loadUser(String userName) {
        return sysUserService.loadUser(userName);
    }

    @Override
    public boolean hasPermission(HttpServletRequest authRequest) {
        String jwt_token = authRequest.getHeader(SecurityConstants.AUTH_HEADER);
        if(StringUtils.isEmpty(jwt_token)){
            return Boolean.FALSE;
        }

        DecodedJWT decodedJWT = JWT.decode(jwt_token);
        Map<String,Claim> claimMap = decodedJWT.getClaims();
        String userName = claimMap.get("userName").asString();

        if(StringUtils.isEmpty(userName)){
            return Boolean.FALSE;
        }
        SysUser sysUser = sysUserService.loadUser(userName);
        if(Objects.isNull(sysUser)){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
