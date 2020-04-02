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

    List<SysUser> loadAllUsers(@Param("sysUser") SysUser sysUser, @Param("companyId") Long companyId);

    List<SysUser> loadAdmin();

    int saveUser(SysUser sysUser);

    int updateUser(SysUser sysUser);

    int  updateEnabled(@Param("enabled") int enabled, @Param("userName") String userName);

    int resetPassword(@Param("passWord") String passWord, @Param("userName") String userName);

}
