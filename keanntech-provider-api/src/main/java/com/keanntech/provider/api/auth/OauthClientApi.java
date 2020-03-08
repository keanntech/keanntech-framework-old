package com.keanntech.provider.api.auth;

import com.keanntech.common.model.auth.OauthClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        contextId = "oauthClientApi",
        name = "oauthClientApi",
        url = "${keanntech.feignUrl.auth}",
        path = "/api/oauth/client"
)
public interface OauthClientApi {

    @PostMapping("/createClient")
    int createClient(@RequestBody OauthClient oauthClient);

}
