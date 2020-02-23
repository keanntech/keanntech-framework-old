package com.keanntech.provider.admin.service;

import com.keanntech.common.model.auth.OauthClient;
import com.keanntech.common.model.po.SysUser;

import javax.servlet.http.HttpServletRequest;

public interface IOauthService {

    OauthClient loadClientByClientId(String clientId);

    boolean hasPermission(HttpServletRequest authRequest);

    SysUser loadUser(String userName);

}
