package com.keanntech.authorization.service.impl;

import com.keanntech.authorization.service.IOauthUserService;
import com.keanntech.authorization.service.OauthUserDetails;
import com.keanntech.common.base.reponse.ResponseData;
import com.keanntech.common.model.po.SysUser;
import com.keanntech.provider.api.org.SysUserApi;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class OauthUserServiceImpl implements IOauthUserService {

    @Autowired
    SysUserApi sysUserApi;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        ResponseData<SysUser> responseData = sysUserApi.loadUser(userName);
        SysUser user = null;
        if(!Objects.isNull(responseData)){
            user = responseData.getData();
        }
        OauthUserDetails oauthUserDetails = new OauthUserDetails(user);
        BeanUtils.copyProperties(user, oauthUserDetails);
        return oauthUserDetails;
    }

    @Override
    public SysUser getUserByUsername(String userName){
        ResponseData<SysUser> responseData  = sysUserApi.loadUser(userName);
        SysUser user = null;
        if(!Objects.isNull(responseData)){
            user = responseData.getData();
        }
        return user;
    }


}
