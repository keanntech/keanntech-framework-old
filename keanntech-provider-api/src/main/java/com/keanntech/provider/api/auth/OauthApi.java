package com.keanntech.provider.api.auth;

import com.keanntech.common.base.config.FeignClientsConfig;
import com.keanntech.common.base.constants.GlobalsConstants;
import com.keanntech.common.model.auth.OauthClient;
import org.springframework.http.MediaType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.*;

@FeignClient(
        contextId = "oauth",
        name = GlobalsConstants.KEANNTECH_SERVICENAME_PROVIDER,
        url = GlobalsConstants.KEANNTECH_FEIGN_URL_PROVIDER,
        path = "/oauth",
        configuration = FeignClientsConfig.class
)
public interface OauthApi {

    @PostMapping(value = "/loadClient/{clientId}", produces = MediaType.APPLICATION_JSON_VALUE + GlobalsConstants.CHARSET, consumes = MediaType.APPLICATION_JSON_VALUE + GlobalsConstants.CHARSET)
    OauthClient loadClientByClientId(@RequestParam("clientId") String clientId);

}
