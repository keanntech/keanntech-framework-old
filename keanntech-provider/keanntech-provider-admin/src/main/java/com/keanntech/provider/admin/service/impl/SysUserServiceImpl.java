package com.keanntech.provider.admin.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baidu.fsg.uid.impl.CachedUidGenerator;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.keanntech.common.base.exception.ActionException;
import com.keanntech.common.base.utils.BCryptPwEncoder;
import com.keanntech.common.model.auth.OauthClient;
import com.keanntech.common.model.dto.UserDTO;
import com.keanntech.common.model.po.SysEmployee;
import com.keanntech.common.model.po.SysRole;
import com.keanntech.common.model.po.SysUser;
import com.keanntech.provider.admin.mapper.SysEmployeeMapper;
import com.keanntech.provider.admin.mapper.SysUserMapper;
import com.keanntech.provider.admin.service.ISysUserRoleRelationService;
import com.keanntech.provider.admin.service.ISysUserService;
import com.keanntech.provider.api.auth.OauthClientApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service("sysUserService")
@Slf4j
public class SysUserServiceImpl implements ISysUserService {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private SysUserMapper sysUserMapper;
    private SysEmployeeMapper sysEmployeeMapper;
    private CachedUidGenerator cachedUidGenerator;
    private OauthClientApi oauthClientApi;
    private ISysUserRoleRelationService sysUserRoleRelationService;

    @Autowired
    public void setSysUserMapper(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    @Autowired
    public void setSysEmployeeMapper(SysEmployeeMapper sysEmployeeMapper){
        this.sysEmployeeMapper = sysEmployeeMapper;
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
    public PageInfo<SysUser> loadAllUsers(SysUser sysUser, int curtPage, int pageSize, Long companyId) {
        PageHelper.startPage(curtPage, pageSize);
        List<SysUser> sysUsers = sysUserMapper.loadAllUsers(sysUser, companyId);
        PageInfo<SysUser> pageInfo = new PageInfo<>(sysUsers);
        return pageInfo;
    }

    @Override
    public List<SysUser> loadAdmin() {
        return sysUserMapper.loadAdmin();
    }

    @Override
    @Transactional(rollbackFor = ActionException.class)
    public boolean createUser(UserDTO userDTO) {

        BCryptPwEncoder bCryptPwEncoder = new BCryptPwEncoder();

        try {
            SysUser sysUser = userDTO.getSysUser();
            SysEmployee employee = userDTO.getSysEmployee();

            LocalDateTime localDateTime = LocalDateTime.parse(DateUtil.now(),formatter);
            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            sysUser.setCreateDate(timestamp);
            sysUser.setUpdateDate(timestamp);
            sysUser.setLastLoginDate(timestamp);
            sysUser.setId(cachedUidGenerator.getUID());
            sysUser.setPassword(bCryptPwEncoder.encode(DigestUtils.md5DigestAsHex(employee.getJobNumber().getBytes())));
            sysUserMapper.saveUser(sysUser);

            employee.setId(cachedUidGenerator.getUID());
            employee.setSysUserId(sysUser.getId());
            employee.setCreateDate(timestamp);
            employee.setUpdateDate(timestamp);
            sysEmployeeMapper.insert(employee);


            //获取角色
            List<SysRole> sysRoles = sysUser.getUserRoles();
            if(!CollectionUtils.isEmpty(sysRoles)){
                List<Long> roleIds = sysRoles.stream().map(SysRole::getId).collect(Collectors.toList());
                sysUserRoleRelationService.batchInsert(sysUser.getId(), roleIds);
            }

            OauthClient oauthClient = new OauthClient();
            oauthClient.setClientId(sysUser.getUserName());
            oauthClient.setClientSecret(sysUser.getPassword());
            oauthClientApi.createClient(oauthClient);


        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw e;
        }

        return true;
    }

    @Override
    @Transactional(rollbackFor = ActionException.class)
    public boolean updateUser(UserDTO userDTO) {
        try {
            SysUser sysUser = userDTO.getSysUser();
            SysEmployee sysEmployee = userDTO.getSysEmployee();

            LocalDateTime localDateTime = LocalDateTime.parse(DateUtil.now(),formatter);
            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            sysUser.setUpdateDate(timestamp);
            sysUserMapper.updateUser(sysUser);

            sysEmployee.setUpdateDate(timestamp);
            sysEmployeeMapper.update(sysEmployee);

            List<SysRole> sysRoles = sysUser.getUserRoles();
            if(!CollectionUtils.isEmpty(sysRoles) && sysRoles.size() > 0) {
                this.sysUserRoleRelationService.deleteByUserId(sysUser.getId());
                List<Long> roleIds = sysRoles.stream().map(SysRole::getId).collect(Collectors.toList());
                sysUserRoleRelationService.batchInsert(sysUser.getId(), roleIds);
            }
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw e;
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = ActionException.class)
    public boolean updateEnabled(int enabled, String userName) {
        try {
            return sysUserMapper.updateEnabled(enabled, userName) > 0;
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    @Transactional(rollbackFor = ActionException.class)
    public boolean resetPassword(String userName) {
        try {
            BCryptPwEncoder bCryptPwEncoder = new BCryptPwEncoder();
            SysUser sysUser = this.loadUserByUserName(userName);
            String pw = bCryptPwEncoder.encode(DigestUtils.md5DigestAsHex(sysUser.getEmployee().getJobNumber().getBytes()));
            sysUserMapper.resetPassword(pw, userName);
            oauthClientApi.resetClientSecret(DigestUtils.md5DigestAsHex(sysUser.getEmployee().getJobNumber().getBytes()), userName);
            return true;
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}
