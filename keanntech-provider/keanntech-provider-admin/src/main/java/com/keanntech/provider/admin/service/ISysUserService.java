package com.keanntech.provider.admin.service;

import com.github.pagehelper.PageInfo;
import com.keanntech.common.model.auth.OauthClient;
import com.keanntech.common.model.po.SysUser;

import java.util.List;

public interface ISysUserService {

    SysUser loadUserByUserName(String userName);

    SysUser loadUserByJobNumber(String jobNumber);

    PageInfo<SysUser> loadAllUsers(SysUser sysUser, int curtPage, int pageSize, Long companyId);
    List<SysUser> loadAdmin();

    boolean createUser(SysUser sysUser);

    boolean updateUser(SysUser sysUser);

    boolean updateEnabled(int enabled, String userName);

    boolean resetPassword(String userName);

}
