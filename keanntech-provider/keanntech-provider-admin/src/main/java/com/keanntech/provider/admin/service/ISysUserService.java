package com.keanntech.provider.admin.service;

import com.keanntech.common.model.po.SysUser;

public interface ISysUserService {

    SysUser loadUser(String userName);

}
