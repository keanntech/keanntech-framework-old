package com.keanntech.provider.admin.controller;

import com.keanntech.common.base.reponse.ResponseData;
import com.keanntech.common.base.reponse.ResponseDataUtil;
import com.keanntech.common.base.reponse.ResultEnums;
import com.keanntech.common.model.po.SysUser;
import com.keanntech.provider.admin.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Api("系统用户")
@RestController
@RequestMapping("/api/admin/sysUser")
public class SysUserController {

    @Autowired
    ISysUserService sysUserService;

    @PostMapping(value = "/loadUser")
    @ApiOperation(value = "根据用户名获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "string")
    })
    public ResponseData<SysUser> loadUser(@RequestParam("userName") String userName){
        return this.getUser(userName);
    }

    private ResponseData<SysUser> getUser(String userName){
        SysUser sysUser = sysUserService.loadUser(userName);
        sysUser.setPassword("");
        if(Objects.isNull(sysUser)){
            sysUser.setPassword("");
            return ResponseDataUtil.buildSuccess(ResultEnums.USER_NOT_EXIST.getCode(), "用户" + userName + "不存在！");
        }
        return ResponseDataUtil.buildSuccess(sysUser);
    }

}
