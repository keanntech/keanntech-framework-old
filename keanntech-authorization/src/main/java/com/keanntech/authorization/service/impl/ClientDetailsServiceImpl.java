package com.keanntech.authorization.service.impl;

import com.keanntech.authorization.mapper.AuthorizationMapper;
import com.keanntech.authorization.service.ClientDetailsImpl;
import com.keanntech.authorization.service.IClientDetailsService;
import com.keanntech.common.model.auth.OauthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ClientDetailsServiceImpl implements IClientDetailsService {

    @Autowired
    AuthorizationMapper authMapper;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OauthClient oauthClient = authMapper.loadClientByClientId(clientId);
        if(Objects.isNull(oauthClient)){
            throw new ClientRegistrationException("客户端不存在");
        }
        return new ClientDetailsImpl(oauthClient);
    }
}
