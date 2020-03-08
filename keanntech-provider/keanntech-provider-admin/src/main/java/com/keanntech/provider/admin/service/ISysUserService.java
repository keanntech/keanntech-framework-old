package com.keanntech.provider.admin.service;

import com.keanntech.common.model.auth.OauthClient;
import com.keanntech.common.model.po.SysUser;

import java.util.List;

public interface ISysUserService {

    SysUser loadUserByUserName(String userName);

    SysUser laoadUserByJobNumber(String jobNumber);

    List<SysUser> loadAllUsers();

    int saveUser(SysUser sysUser);

    boolean createUser(SysUser sysUser, OauthClient oauthClient);

}
