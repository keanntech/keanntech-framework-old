package com.keanntech.authorization.controller;

import com.keanntech.authorization.service.IClientDetailsService;
import com.keanntech.common.base.reponse.ResponseData;
import com.keanntech.common.base.reponse.ResponseDataUtil;
import com.keanntech.common.model.auth.OauthClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/oauth/client")
@Api(value = "Oauth2客户端")
public class ClientDetailController {

    private IClientDetailsService clientDetailsService;

    @Autowired
    public void setClientDetailsService(IClientDetailsService clientDetailsService){
        this.clientDetailsService = clientDetailsService;
    }

    @PostMapping("/createClient")
    @ApiOperation(value = "创建客户端", httpMethod = "POST")
    @ApiImplicitParam(name = "oauthClient", value = "客户端对象", paramType = "body", dataType = "OauthClient")
    public int createClient(@RequestBody OauthClient oauthClient){
        return clientDetailsService.insertClient(oauthClient);
    }

    @GetMapping("/resetClientSecret")
    public int resetClientSecret(@RequestParam("secret") String secret, @RequestParam("clientId") String clientId){
        int intResult = clientDetailsService.resetClientSecret(secret, clientId);
        return intResult;
    }

}
