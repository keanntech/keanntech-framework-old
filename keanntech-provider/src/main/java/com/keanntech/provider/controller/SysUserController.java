package com.keanntech.provider.controller;

import com.keanntech.common.base.constants.GlobalsConstants;
import com.keanntech.common.base.reponse.ResponseData;
import com.keanntech.common.base.reponse.ResponseDataUtil;
import com.keanntech.common.base.reponse.ResultEnums;
import com.keanntech.common.model.po.SysUser;
import com.keanntech.provider.service.ISysUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Api("系统用户")
@RestController
@RequestMapping("/api/sysUser")
public class SysUserController {

    @Autowired
    ISysUserService sysUserService;

    @PostMapping(value = "/loadUser", produces = MediaType.APPLICATION_JSON_VALUE + GlobalsConstants.CHARSET, consumes = MediaType.APPLICATION_JSON_VALUE + GlobalsConstants.CHARSET)
    @ApiOperation(value = "根据用户名获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "string")
    })
    public ResponseData<SysUser> loadUser(@RequestParam("userName") String userName){
        SysUser sysUser = sysUserService.loadUser(userName);
        if(Objects.isNull(sysUser)){
            return ResponseDataUtil.buildSuccess(ResultEnums.USER_NOT_EXIST.getCode(), "用户" + userName + "不存在！");
        }
        return ResponseDataUtil.buildSuccess(sysUser);
    }

}
