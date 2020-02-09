package com.keanntech.provider.service;

import com.keanntech.common.model.po.SysUser;

public interface ISysUserService {

    SysUser loadUser(String userName);

}
