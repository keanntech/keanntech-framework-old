package com.keanntech.authorization.controller;

import com.keanntech.authorization.service.IClientDetailsService;
import com.keanntech.common.model.auth.OauthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oauth/client")
public class ClientDetailController {

    @Autowired
    IClientDetailsService clientDetailsService;

    @PostMapping("/createClient")
    public int createClient(@RequestBody OauthClient oauthClient){
        return clientDetailsService.insertClient(oauthClient);
    }

}
