package com.keanntech.authorization.mapper;

import com.keanntech.common.model.po.SysRole;
import com.keanntech.common.model.po.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper {

    SysUser loadUser(String userName);

    List<SysRole> getRolesById(Long id);

}
