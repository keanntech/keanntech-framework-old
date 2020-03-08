package com.keanntech.provider.admin.controller;

import cn.hutool.core.date.DateUtil;
import com.keanntech.common.base.annotation.CurrentUser;
import com.keanntech.common.base.constants.SecurityConstants;
import com.keanntech.common.base.controller.BaseController;
import com.keanntech.common.base.oauth.JWTHelper;
import com.keanntech.common.base.reponse.ResponseData;
import com.keanntech.common.base.reponse.ResponseDataUtil;
import com.keanntech.common.base.reponse.ResultEnums;
import com.keanntech.common.model.auth.OauthClient;
import com.keanntech.common.model.methodresolverparam.CurrentUserResolverParam;
import com.keanntech.common.model.po.SysRole;
import com.keanntech.common.model.po.SysUser;
import com.keanntech.common.model.po.SysUserRoleRelation;
import com.keanntech.provider.admin.service.ISysUserRoleRelationService;
import com.keanntech.provider.admin.service.ISysUserService;
import com.keanntech.common.base.utils.OauthUtil;
import com.keanntech.provider.api.auth.OauthClientApi;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Api("系统用户")
@RestController
@RequestMapping("/api/admin/sysUser")
public class SysUserController extends BaseController {



    @Autowired
    ISysUserService sysUserService;

    @Autowired
    ISysUserRoleRelationService sysUserRoleRelationService;

    @GetMapping(value = "/loadUser")
    @ApiOperation(value = "根据用户名获取用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "string")
    })
    public ResponseData<SysUser> loadUser(@NotNull(message = "用户名不能为空！") @RequestParam("userName") String userName){
        SysUser sysUser = sysUserService.loadUserByUserName(userName);
        if(Objects.isNull(sysUser)){
            return ResponseDataUtil.buildError(ResultEnums.USER_NOT_EXIST.getCode(), "用户" + userName + "不存在！");
        }
        sysUser.setPassword("");
        return ResponseDataUtil.buildSuccess(sysUser);
    }

    @GetMapping("/loadAllUsers")
    @ApiOperation(value = "获取所有用户")
    public ResponseData<SysUser> loadAllUsers(){
        List<SysUser> sysUserList = sysUserService.loadAllUsers();
        return ResponseDataUtil.buildSuccess(sysUserList);
    }

    @PostMapping("/saveUser")
    public ResponseData<SysUser> saveUser(
            @NotNull(message = "用户信息不能为空！") @RequestBody SysUser sysUser, @CurrentUser CurrentUserResolverParam currentUser) {
        //验证用户名、工号是否重复
        SysUser user = sysUserService.loadUserByUserName(sysUser.getUserName());
        if(!Objects.isNull(user)){
            return ResponseDataUtil.buildError("用户[" + sysUser.getUserName() + "]已存在！");
        }
        user = sysUserService.laoadUserByJobNumber(sysUser.getJobNumber());
        if(!Objects.isNull(user)){
            return ResponseDataUtil.buildError("工号[" + sysUser.getJobNumber() + "]已存在！");
        }
        sysUser.setCreateId(currentUser.getId());
        sysUser.setUpdateId(currentUser.getId());

        //保存用户信息
        boolean isCreated = sysUserService.createUser(sysUser, new OauthClient());
        if(isCreated){
            //获取角色
            List<SysRole> sysRoles = sysUser.getUserRoles();
            List<Long> roleIds = sysRoles.stream().map(SysRole::getId).collect(Collectors.toList());
            sysUserRoleRelationService.batchInsert(sysUser.getId(), roleIds);

            return ResponseDataUtil.buildSuccess(ResultEnums.SUCCESS.getCode(),"添加成功！",sysUser);
        }
        return ResponseDataUtil.buildError("添加失败！");
    }

}
