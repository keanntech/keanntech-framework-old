package com.keanntech.common.model.dto;

import com.keanntech.common.model.po.SysEmployee;
import com.keanntech.common.model.po.SysUser;
import lombok.Data;

@Data
public class UserDTO {

    private SysUser sysUser;
    private SysEmployee sysEmployee;

}
