package com.keanntech.provider.admin.mapper;

import com.keanntech.common.model.po.SysRole;
import com.keanntech.common.model.po.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper {

    SysUser loadUserByUserName(String userName);

    SysUser loadUserByJobNumber(String jobNumber);

    List<SysUser> loadAllUsers();

    List<SysRole> getRolesById(Long id);

    int saveUser(SysUser sysUser);

}
