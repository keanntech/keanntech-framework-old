package com.keanntech.authorization.service.impl;

import com.keanntech.authorization.service.IOauthUserService;
import com.keanntech.authorization.service.OauthUserDetails;
import com.keanntech.common.base.constants.GlobalsConstants;
import com.keanntech.common.model.po.SysUser;
import com.keanntech.provider.api.auth.OauthApi;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class OauthUserServiceImpl implements IOauthUserService {

    @Autowired
    OauthApi oauthApi;

    @Override
    @Cacheable(
            cacheNames = GlobalsConstants.CACHE_NAME_AUTH,
            key = "'loadUser_' + #userName",
            unless = "#result == null"
    )
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        SysUser user = oauthApi.loadUser(userName);
        if(Objects.isNull(user)){
            throw new UsernameNotFoundException("账户名或者密码输入错误!");
        }
        OauthUserDetails oauthUserDetails = new OauthUserDetails(user);
        BeanUtils.copyProperties(user, oauthUserDetails);
        return oauthUserDetails;
    }

    @Override
    public SysUser getUserByUsername(String userName){
        SysUser user  = oauthApi.loadUser(userName);
        return user;
    }


}
