package com.keanntech.authorization.service;

import com.keanntech.common.model.auth.OauthClient;
import org.springframework.security.oauth2.provider.ClientDetailsService;

public interface IClientDetailsService extends ClientDetailsService {

    int insertClient(OauthClient oauthClient);

}
