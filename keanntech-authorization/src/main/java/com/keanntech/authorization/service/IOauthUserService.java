package com.keanntech.authorization.service;

import com.keanntech.common.model.po.SysUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IOauthUserService extends UserDetailsService {

    public SysUser getUserByUsername(String userName);

}
