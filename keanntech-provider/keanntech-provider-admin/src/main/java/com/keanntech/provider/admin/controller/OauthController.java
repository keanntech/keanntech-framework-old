package com.keanntech.provider.admin.controller;

import com.keanntech.common.base.http.CustomHttpServletRequestWrapper;
import com.keanntech.provider.admin.service.IOauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/oauth")
public class OauthController {

    @Autowired
    IOauthService oauthService;

    @PostMapping(value = "/hasPermission")
    public boolean hasPermission(String url, String method, HttpServletRequest httpServletRequest){
        return oauthService.hasPermission(new CustomHttpServletRequestWrapper(httpServletRequest, url, method));
    }

}
