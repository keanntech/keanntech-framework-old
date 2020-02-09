package com.keanntech.provider.controller;

import com.keanntech.common.base.constants.GlobalsConstants;
import com.keanntech.common.model.auth.OauthClient;
import com.keanntech.provider.service.IOauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
public class OauthController {

    @Autowired
    IOauthService oauthService;

    @PostMapping(value = "/loadClient/{clientId}", produces = MediaType.APPLICATION_JSON_VALUE + GlobalsConstants.CHARSET, consumes = MediaType.APPLICATION_JSON_VALUE + GlobalsConstants.CHARSET)
    public OauthClient loadClientByClientId(@RequestParam("clientId") String clientId){
        return oauthService.loadClientByClientId(clientId);
    }

}
