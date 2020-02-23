package com.keanntech.provider.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.keanntech.common.base.constants.SecurityConstants;
import com.keanntech.common.base.utils.RedisUtil;
import com.keanntech.common.model.auth.OauthClient;
import com.keanntech.common.model.po.SysUser;
import com.keanntech.provider.admin.mapper.AuthorizationMapper;
import com.keanntech.provider.admin.mapper.SysUserMapper;
import com.keanntech.provider.admin.service.IOauthService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
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
    public OauthClient loadClientByClientId(String clientId) {
        return authorizationMapper.loadClientByClientId(clientId);
    }

    @Override
    public SysUser loadUser(String userName) {
        SysUser sysUser = sysUserMapper.loadUser(userName);
        if(!Objects.isNull(sysUser)){
            return sysUser;
        }
        return null;
    }

    @Override
    public boolean hasPermission(HttpServletRequest authRequest) {
        //获取TOKEN，如果为空返回FALSE
        String jwt_token = authRequest.getHeader(SecurityConstants.AUTH_HEADER);
        if(StringUtils.isEmpty(jwt_token)){
            return Boolean.FALSE;
        }

        //获取用户名，根据用户名从REDIS中获取序列化后的TOKEN，如果为空返回FALSE
        String jwt_userName = authRequest.getHeader(SecurityConstants.AUTH_HEADER_USERNAME);
        String serial_token = String.valueOf(redisUtil.get(jwt_userName));
        if(StringUtils.isEmpty(serial_token)){
            return Boolean.FALSE;
        }
        //序列化的TOKEN转JSON，取出TOKEN和HEADER中携带的TOKEN比较，不一样返回FALSE
        JSONObject json_token = JSON.parseObject(serial_token);
        if(!StringUtils.equals(json_token.getString("value"),jwt_token)){
            return Boolean.FALSE;
        }

        DecodedJWT decodedJWT = JWT.decode(jwt_token);
        Map<String,Claim> claimMap = decodedJWT.getClaims();
        String userName = claimMap.get("userName").asString();

        if(StringUtils.isEmpty(userName) || !StringUtils.equals(userName,jwt_userName)){
            return Boolean.FALSE;
        }

        SysUser sysUser = sysUserMapper.loadUser(userName);
        if(Objects.isNull(sysUser)){
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
