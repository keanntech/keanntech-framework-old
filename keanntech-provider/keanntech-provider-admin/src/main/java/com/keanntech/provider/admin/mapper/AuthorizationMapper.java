package com.keanntech.provider.admin.mapper;

import com.keanntech.common.model.auth.OauthClient;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthorizationMapper {

    OauthClient loadClientByClientId(String clientId);

}
