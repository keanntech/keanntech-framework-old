package com.keanntech.common.base.oauth;

import cn.hutool.core.codec.Base64;
import com.keanntech.common.base.constants.SecurityConstants;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class JWTHelper {

    private JWTHelper(){}

    public static Claims parseJWT(HttpServletRequest httpServletRequest, String publicKey){
        String jwt_token = httpServletRequest.getHeader(SecurityConstants.AUTH_HEADER);
        if(StringUtils.isEmpty(jwt_token)){
            return null;
        }
        return parseJWT(jwt_token, publicKey);
    }

    public static Claims parseJWT(String token, String publicKey){
        if(StringUtils.isEmpty(token) || StringUtils.isEmpty(publicKey)){
            return null;
        }

        //解析TOKEN
        Claims claims = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            String strPubKey = publicKey.replace("-----BEGIN PUBLIC KEY-----","").replace("-----END PUBLIC KEY-----","").replace("\n","");
            byte[] encodedKey = Base64.decode(strPubKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            claims = Jwts.parser().setSigningKey(pubKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
//            return null;
        }

        return claims;
    }

}
