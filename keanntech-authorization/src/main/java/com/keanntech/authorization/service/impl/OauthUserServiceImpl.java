package com.keanntech.authorization.service.impl;

import com.keanntech.authorization.mapper.SysUserMapper;
import com.keanntech.authorization.service.IOauthUserService;
import com.keanntech.authorization.service.OauthUserDetails;
import com.keanntech.common.model.po.SysUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class OauthUserServiceImpl implements IOauthUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        SysUser user = sysUserMapper.loadUser(userName);
        if(Objects.isNull(user)){
            throw new UsernameNotFoundException("账户名或者密码输入错误!");
        }
        OauthUserDetails oauthUserDetails = new OauthUserDetails(user);
        BeanUtils.copyProperties(user, oauthUserDetails);
        return oauthUserDetails;
    }


}
