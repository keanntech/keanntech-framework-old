package com.keanntech.provider.admin.mapper;

import com.keanntech.common.model.po.SysRole;
import com.keanntech.common.model.po.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserMapper {

    SysUser loadUserByUserName(String userName);

    SysUser loadUserByJobNumber(String jobNumber);

    List<SysUser> loadAllUsers(Long companyId);

    List<SysUser> loadAdmin();

    List<SysRole> getRolesById(Long userId);

    int saveUser(SysUser sysUser);

    int updateUser(SysUser sysUser);

    int resetPassword(@Param("passWord") String passWord, @Param("userName") String userName);

}
