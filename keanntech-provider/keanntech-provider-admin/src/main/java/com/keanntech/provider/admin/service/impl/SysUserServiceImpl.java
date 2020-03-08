package com.keanntech.provider.admin.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baidu.fsg.uid.impl.CachedUidGenerator;
import com.keanntech.common.base.constants.GlobalsConstants;
import com.keanntech.common.base.utils.BCryptPwEncoder;
import com.keanntech.common.model.auth.OauthClient;
import com.keanntech.common.model.po.SysUser;
import com.keanntech.provider.admin.mapper.SysUserMapper;
import com.keanntech.provider.admin.service.ISysUserService;
import com.keanntech.provider.api.auth.OauthClientApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service("sysUserService")
public class SysUserServiceImpl implements ISysUserService {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    @Qualifier("cacheUidGenerator")
    CachedUidGenerator cachedUidGenerator;

    @Autowired
    OauthClientApi oauthClientApi;

    @Override
    public SysUser loadUserByUserName(String userName) {
        return sysUserMapper.loadUserByUserName(userName);
    }

    @Override
    public SysUser laoadUserByJobNumber(String jobNumber) {
        return sysUserMapper.loadUserByJobNumber(jobNumber);
    }

    @Override
    public List<SysUser> loadAllUsers() {
        return sysUserMapper.loadAllUsers();
    }

    @Override
    @Transactional
    public int saveUser(SysUser sysUser) {
        BCryptPwEncoder bCryptPwEncoder = new BCryptPwEncoder();
        try {
            sysUser.setId(cachedUidGenerator.getUID());
            sysUser.setPassword(bCryptPwEncoder.encode(DigestUtils.md5DigestAsHex(GlobalsConstants.USER_INITIAL_PASSWORD.getBytes())));
            return sysUserMapper.saveUser(sysUser);
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    @Transactional
    public boolean createUser(SysUser sysUser, OauthClient oauthClient) {

        LocalDateTime localDateTime = LocalDateTime.parse(DateUtil.now(),formatter);
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        sysUser.setCreateDate(timestamp);
        sysUser.setUpdateDate(timestamp);
        sysUser.setLastLoginDate(timestamp);

        int isSaved = saveUser(sysUser);
        if(isSaved > 0){
            oauthClient.setClientId(sysUser.getUserName());
            oauthClient.setClientSecret(sysUser.getPassword());
            isSaved = oauthClientApi.createClient(oauthClient);
            if(isSaved > 0){
                return true;
            }
        }

        return false;
    }
}
