package com.keanntech.provider.service;

import com.keanntech.common.base.reponse.ResponseData;
import com.keanntech.common.model.auth.OauthClient;
import com.keanntech.common.model.po.SysUser;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface IOauthService {

    OauthClient loadClientByClientId(String clientId);

    boolean hasPermission(HttpServletRequest authRequest);

    SysUser loadUser(String userName);

}
