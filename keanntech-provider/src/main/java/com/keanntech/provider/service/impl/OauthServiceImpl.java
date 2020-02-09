package com.keanntech.provider.service.impl;

import com.keanntech.common.model.auth.OauthClient;
import com.keanntech.provider.mapper.AuthorizationMapper;
import com.keanntech.provider.service.IOauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OauthServiceImpl implements IOauthService {

    @Autowired
    AuthorizationMapper authorizationMapper;

    @Override
    public OauthClient loadClientByClientId(String clientId) {
        return authorizationMapper.loadClientByClientId(clientId);
    }
}
