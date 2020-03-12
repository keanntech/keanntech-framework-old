package com.keanntech.provider.admin.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baidu.fsg.uid.impl.CachedUidGenerator;
import com.keanntech.common.base.constants.GlobalsConstants;
import com.keanntech.common.base.exception.ActionException;
import com.keanntech.common.base.utils.BCryptPwEncoder;
import com.keanntech.common.model.auth.OauthClient;
import com.keanntech.common.model.po.SysRole;
import com.keanntech.common.model.po.SysUser;
import com.keanntech.provider.admin.mapper.SysUserMapper;
import com.keanntech.provider.admin.service.ISysUserRoleRelationService;
import com.keanntech.provider.admin.service.ISysUserService;
import com.keanntech.provider.api.auth.OauthClientApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("sysUserService")
public class SysUserServiceImpl implements ISysUserService {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private SysUserMapper sysUserMapper;
    private CachedUidGenerator cachedUidGenerator;
    private OauthClientApi oauthClientApi;
    private ISysUserRoleRelationService sysUserRoleRelationService;

    @Autowired
    public void setSysUserMapper(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    @Autowired
    @Qualifier("cacheUidGenerator")
    public void setCachedUidGenerator(CachedUidGenerator cachedUidGenerator) {
        this.cachedUidGenerator = cachedUidGenerator;
    }

    @Autowired
    public void setOauthClientApi(OauthClientApi oauthClientApi) {
        this.oauthClientApi = oauthClientApi;
    }

    @Override
    public SysUser loadUserByUserName(String userName) {
        return sysUserMapper.loadUserByUserName(userName);
    }

    @Override
    public SysUser loadUserByJobNumber(String jobNumber) {
        return sysUserMapper.loadUserByJobNumber(jobNumber);
    }

    @Autowired
    public void setSysUserRoleRelationService(ISysUserRoleRelationService sysUserRoleRelationService) {
        this.sysUserRoleRelationService = sysUserRoleRelationService;
    }

    @Override
    public List<SysUser> loadAllUsers() {
        return sysUserMapper.loadAllUsers();
    }

    @Override
    public List<SysUser> loadAdmin() {
        return sysUserMapper.loadAdmin();
    }

    @Override
    @Transactional(rollbackFor = ActionException.class, isolation = Isolation.READ_COMMITTED)
    public boolean createUser(SysUser sysUser, OauthClient oauthClient) {

        BCryptPwEncoder bCryptPwEncoder = new BCryptPwEncoder();

        try {
            LocalDateTime localDateTime = LocalDateTime.parse(DateUtil.now(),formatter);
            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            sysUser.setCreateDate(timestamp);
            sysUser.setUpdateDate(timestamp);
            sysUser.setLastLoginDate(timestamp);
            sysUser.setId(cachedUidGenerator.getUID());
            sysUser.setPassword(bCryptPwEncoder.encode(DigestUtils.md5DigestAsHex(sysUser.getJobNumber().getBytes())));
            sysUserMapper.saveUser(sysUser);

            //获取角色
            List<SysRole> sysRoles = sysUser.getUserRoles();
            if(!CollectionUtils.isEmpty(sysRoles)){
                List<Long> roleIds = sysRoles.stream().map(SysRole::getId).collect(Collectors.toList());
                sysUserRoleRelationService.batchInsert(sysUser.getId(), roleIds);
            }

            oauthClient.setClientId(sysUser.getUserName());
            oauthClient.setClientSecret(sysUser.getPassword());
            oauthClientApi.createClient(oauthClient);


        } catch (ActionException e) {
            throw new ActionException();
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = ActionException.class, isolation = Isolation.READ_COMMITTED)
    public boolean updateUser(SysUser sysUser) {
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(DateUtil.now(),formatter);
            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            sysUser.setUpdateDate(timestamp);
            sysUserMapper.updateUser(sysUser);
        } catch (ActionException e) {
            throw new ActionException();
        }
        return true;
    }
}
