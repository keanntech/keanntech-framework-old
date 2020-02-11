package com.keanntech.provider.api.auth;

import com.keanntech.common.base.config.FeignClientsConfig;
import com.keanntech.common.base.constants.GlobalsConstants;
import com.keanntech.common.base.constants.SecurityConstants;
import com.keanntech.common.base.reponse.ResponseData;
import com.keanntech.common.model.auth.OauthClient;
import com.keanntech.common.model.po.SysUser;
import org.springframework.http.MediaType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        contextId = "oauth",
        name = GlobalsConstants.KEANNTECH_SERVICENAME_PROVIDER,
        url = GlobalsConstants.KEANNTECH_FEIGN_URL_PROVIDER,
        path = "/oauth"
)
public interface OauthApi {

    @PostMapping(value = "/loadClient", produces = MediaType.APPLICATION_JSON_VALUE + GlobalsConstants.CHARSET, consumes = MediaType.APPLICATION_JSON_VALUE + GlobalsConstants.CHARSET)
    OauthClient loadClientByClientId(@RequestParam("clientId") String clientId);

    @PostMapping(value = "/hasPermission", produces = MediaType.APPLICATION_JSON_VALUE + GlobalsConstants.CHARSET, consumes = MediaType.APPLICATION_JSON_VALUE + GlobalsConstants.CHARSET)
    boolean hasPermission(@RequestHeader(SecurityConstants.AUTH_HEADER) String authentication, @RequestParam("url") String url, @RequestParam("method") String method);

    @PostMapping(value = "loadUser", produces = MediaType.APPLICATION_JSON_VALUE + GlobalsConstants.CHARSET, consumes = MediaType.APPLICATION_JSON_VALUE + GlobalsConstants.CHARSET)
    SysUser loadUser(@RequestParam("userName") String userName);



}
