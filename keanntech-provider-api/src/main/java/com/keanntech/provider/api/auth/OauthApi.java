package com.keanntech.provider.api.auth;

import com.keanntech.common.base.constants.GlobalsConstants;
import com.keanntech.common.base.constants.SecurityConstants;
import com.keanntech.common.model.auth.OauthClient;
import com.keanntech.common.model.po.SysUser;
import org.springframework.http.MediaType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        contextId = "oauth",
        name = "oauthApi",
        url = "${keanntech.feignUrl.provider.admin}",
        path = "/oauth"
)
public interface OauthApi {

    @PostMapping(value = "/loadClient")
    OauthClient loadClientByClientId(@RequestParam("clientId") String clientId);

    @PostMapping(value = "/hasPermission")
    boolean hasPermission(@RequestHeader(SecurityConstants.AUTH_HEADER) String authentication, @RequestHeader(SecurityConstants.AUTH_HEADER_USERNAME) String userName, @RequestParam("url") String url, @RequestParam("method") String method);

    @PostMapping(value = "/loadUser")
    SysUser loadUser(@RequestParam("userName") String userName);



}
