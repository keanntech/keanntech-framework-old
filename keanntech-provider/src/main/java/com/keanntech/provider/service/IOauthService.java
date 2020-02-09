package com.keanntech.provider.service;

import com.keanntech.common.model.auth.OauthClient;

public interface IOauthService {

    OauthClient loadClientByClientId(String clientId);

}
