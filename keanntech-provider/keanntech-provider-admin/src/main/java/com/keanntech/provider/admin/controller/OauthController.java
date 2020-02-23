package com.keanntech.provider.admin.controller;

import com.keanntech.common.base.http.CustomHttpServletRequestWrapper;
import com.keanntech.common.model.auth.OauthClient;
import com.keanntech.common.model.po.SysUser;
import com.keanntech.provider.admin.service.IOauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/oauth")
public class OauthController {

    @Autowired
    IOauthService oauthService;

    @PostMapping(value = "/loadClient")
    public OauthClient loadClientByClientId(@RequestParam("clientId") String clientId){
        return oauthService.loadClientByClientId(clientId);
    }

    @PostMapping(value = "/hasPermission")
    public boolean hasPermission(String url, String method, HttpServletRequest httpServletRequest){
        return oauthService.hasPermission(new CustomHttpServletRequestWrapper(httpServletRequest, url, method));
    }

    @PostMapping(value = "/loadUser")
    public SysUser loadUser(@RequestParam("userName") String userName){
        return oauthService.loadUser(userName);
    }

}
