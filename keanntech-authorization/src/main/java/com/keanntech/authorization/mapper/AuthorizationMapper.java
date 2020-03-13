package com.keanntech.authorization.mapper;

import com.keanntech.common.model.auth.OauthClient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthorizationMapper {

    OauthClient loadClientByClientId(String clientId);

    int insert(OauthClient oauthClient);

    int resetClientSecret(@Param("secret") String secret, @Param("clientId") String clientId);

}
