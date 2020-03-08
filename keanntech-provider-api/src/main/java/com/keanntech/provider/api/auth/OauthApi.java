package com.keanntech.provider.api.auth;

import com.keanntech.common.base.constants.SecurityConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        contextId = "oauthApi",
        name = "oauthApi",
        url = "${keanntech.feignUrl.provider.admin}",
        path = "/api/oauth"
)
public interface OauthApi {

    @GetMapping(value = "/hasPermission")
    boolean hasPermission(@RequestHeader(SecurityConstants.AUTH_HEADER) String authentication, @RequestParam("url") String url, @RequestParam("method") String method);



}
