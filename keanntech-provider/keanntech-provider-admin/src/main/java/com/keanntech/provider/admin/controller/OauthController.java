package com.keanntech.provider.admin.controller;

import com.keanntech.common.base.http.CustomHttpServletRequestWrapper;
import com.keanntech.provider.admin.service.IOauthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/oauth")
public class OauthController {

    @Autowired
    IOauthService oauthService;

    /**
     *  验证权限
     * @param url
     * @param method
     * @param httpServletRequest
     * @return
     */
    @GetMapping(value = "/hasPermission")
    public boolean hasPermission(@RequestParam("url") String url, @RequestParam("method") String method, HttpServletRequest httpServletRequest){
        return oauthService.hasPermission(new CustomHttpServletRequestWrapper(httpServletRequest, url, method));
    }

}
