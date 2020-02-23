package com.keanntech.provider.admin.mapper;

import com.keanntech.common.model.po.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper {

    SysUser loadUser(String userName);

}
