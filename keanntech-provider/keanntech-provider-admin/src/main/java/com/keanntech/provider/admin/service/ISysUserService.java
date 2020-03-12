package com.keanntech.provider.admin.service;

import com.keanntech.common.model.auth.OauthClient;
import com.keanntech.common.model.po.SysUser;

import java.util.List;

public interface ISysUserService {

    SysUser loadUserByUserName(String userName);

    SysUser loadUserByJobNumber(String jobNumber);

    List<SysUser> loadAllUsers();
    List<SysUser> loadAdmin();

    boolean createUser(SysUser sysUser, OauthClient oauthClient);

    boolean updateUser(SysUser sysUser);

}
