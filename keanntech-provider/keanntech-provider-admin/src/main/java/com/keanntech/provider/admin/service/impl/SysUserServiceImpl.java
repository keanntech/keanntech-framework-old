package com.keanntech.provider.admin.service.impl;

import com.keanntech.common.model.po.SysUser;
import com.keanntech.provider.admin.mapper.SysUserMapper;
import com.keanntech.provider.admin.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysUserService")
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public SysUser loadUser(String userName) {
        return sysUserMapper.loadUser(userName);
    }
}
