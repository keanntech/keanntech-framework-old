package com.keanntech.provider.admin.service.impl;

import com.keanntech.common.base.constants.GlobalsConstants;
import com.keanntech.common.base.constants.SecurityConstants;
import com.keanntech.common.base.oauth.JWTHelper;
import com.keanntech.common.base.utils.RedisUtil;
import com.keanntech.provider.admin.mapper.AuthorizationMapper;
import com.keanntech.provider.admin.mapper.SysUserMapper;
import com.keanntech.provider.admin.service.IOauthService;
import com.keanntech.common.base.utils.OauthUtil;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.Objects;

@Service("oauthService")
public class OauthServiceImpl implements IOauthService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    AuthorizationMapper authorizationMapper;

    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public boolean hasPermission(HttpServletRequest authRequest) {
        //获取TOKEN，如果为空返回FALSE
        String jwt_token = authRequest.getHeader(SecurityConstants.AUTH_HEADER);
        if(StringUtils.isEmpty(jwt_token)){
            return Boolean.FALSE;
        }
        if(StringUtils.startsWith(jwt_token, SecurityConstants.AUTH_TYPE)){
            jwt_token = jwt_token.substring(SecurityConstants.AUTH_TYPE.length());
        }

        //解析TOKEN
        Claims claims = JWTHelper.parseJWT(jwt_token, OauthUtil.getPubKey());

        //已过期
        if(Objects.isNull(claims) || claims.getExpiration().before(new Date())){
            return Boolean.FALSE;
        }

        String jwt_userName = String.valueOf(claims.get("userName"));
        //获取Redis中的TOKEN
        String serial_token = String.valueOf(redisUtil.get(GlobalsConstants.CACHE_NAME_TOKEN_SERIALIZER + "::" + jwt_userName)) + "}";
        if(StringUtils.isEmpty(serial_token)){
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }
}
