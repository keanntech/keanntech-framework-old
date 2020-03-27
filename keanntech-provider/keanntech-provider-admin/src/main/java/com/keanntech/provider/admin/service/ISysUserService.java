package com.keanntech.provider.admin.service;

import com.github.pagehelper.PageInfo;
import com.keanntech.common.model.dto.UserDTO;
import com.keanntech.common.model.po.SysEmployee;
import com.keanntech.common.model.po.SysUser;

import java.util.List;

public interface ISysUserService {

    SysUser loadUserByUserName(String userName);

    SysUser loadUserByJobNumber(String jobNumber);

    PageInfo<SysUser> loadAllUsers(SysUser sysUser, int curtPage, int pageSize, Long companyId);
    List<SysUser> loadAdmin();

    boolean createUser(UserDTO userDTO);

    boolean updateUser(UserDTO userDTO);

    boolean updateEnabled(int enabled, String userName);

    boolean resetPassword(String userName);

}
