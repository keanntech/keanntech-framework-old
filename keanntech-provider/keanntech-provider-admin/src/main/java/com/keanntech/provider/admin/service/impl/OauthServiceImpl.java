package com.keanntech.provider.admin.service.impl;

import cn.hutool.core.codec.Base64;
import com.keanntech.common.base.constants.GlobalsConstants;
import com.keanntech.common.base.constants.SecurityConstants;
import com.keanntech.common.base.utils.RedisUtil;
import com.keanntech.provider.admin.mapper.AuthorizationMapper;
import com.keanntech.provider.admin.mapper.SysUserMapper;
import com.keanntech.provider.admin.service.IOauthService;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("oauthService")
public class OauthServiceImpl implements IOauthService {

    private static final String PUBLIC_KEY = "pubkey.txt";

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
        Claims claims = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            String strPubKey = this.getPubKey().replace("-----BEGIN PUBLIC KEY-----","").replace("-----END PUBLIC KEY-----","");
            byte[] encodedKey = Base64.decode(strPubKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            claims = Jwts.parser().setSigningKey(pubKey).parseClaimsJws(jwt_token).getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

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



    /**
     * 获取非对称加密公钥 Key
     * @return 公钥 Key
     */
    private String getPubKey() {
        Resource resource = new ClassPathResource(OauthServiceImpl.PUBLIC_KEY);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            return br.lines().collect(Collectors.joining("\n"));
        } catch (IOException ioe) {
            return null;
        }
    }
}
