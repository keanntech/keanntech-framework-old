package com.keanntech.provider.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

public class ResJWTTokenStore {

    private static final String PUBLIC_KEY = "pubkey.txt";

    @Autowired
    private ResourceServerProperties resourceServerProperties;

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        String pubKey = getPubKey();
        converter.setVerifier(new RsaVerifier(pubKey));
        return converter;
    }

    /**
     * 获取非对称加密公钥 Key
     * @return 公钥 Key
     */
    private String getPubKey() {
        Resource resource = new ClassPathResource(ResJWTTokenStore.PUBLIC_KEY);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            return br.lines().collect(Collectors.joining("\n"));
        } catch (IOException ioe) {
            return getKeyFromAuthorizationServer();
        }
    }

    /**
     * 通过访问授权服务器获取非对称加密公钥 Key
     * @return 公钥 Key
     */
    private String getKeyFromAuthorizationServer() {
        ObjectMapper objectMapper = new ObjectMapper();
        String pubKey = new RestTemplate().getForObject(resourceServerProperties.getJwt().getKeyUri(), String.class);
        try {
            Map map = objectMapper.readValue(pubKey, Map.class);
            return map.get("value").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
