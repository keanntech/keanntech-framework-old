package com.keanntech.provider.admin.controller;

import com.github.pagehelper.PageInfo;
import com.keanntech.common.base.annotation.CurrentUser;
import com.keanntech.common.base.controller.BaseController;
import com.keanntech.common.base.exception.ActionException;
import com.keanntech.common.base.reponse.ResponseData;
import com.keanntech.common.base.reponse.ResponseDataUtil;
import com.keanntech.common.base.reponse.ResultEnums;
import com.keanntech.common.model.auth.OauthClient;
import com.keanntech.common.model.methodresolver.CurrentUserResolver;
import com.keanntech.common.model.po.SysUser;
import com.keanntech.common.model.po.SysUserRoleRelation;
import com.keanntech.provider.admin.service.ISysUserRoleRelationService;
import com.keanntech.provider.admin.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Api("系统用户")
@RestController
@RequestMapping("/api/admin/sysUser")
public class SysUserController extends BaseController {

    private ISysUserService sysUserService;
    private ISysUserRoleRelationService sysUserRoleRelationService;

    @Autowired
    public void setSysUserService(ISysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Autowired
    public void setSysUserRoleRelationService(ISysUserRoleRelationService sysUserRoleRelationService) {
        this.sysUserRoleRelationService = sysUserRoleRelationService;
    }

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
        return ResponseDataUtil.buildSuccess(ResultEnums.SUCCESS.getCode(), "", sysUser);
    }

    @PostMapping("/loadAllUsers")
    @ApiOperation(value = "分页获取所有用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "curtPage", value = "当前页", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", paramType = "query")
    })
    public ResponseData<SysUser> loadAllUsers(@RequestBody SysUser sysUser, @RequestParam("curtPage") int curtPage, @RequestParam("pageSize") int pageSize, @CurrentUser CurrentUserResolver currentUserResolver){
        PageInfo<SysUser> sysUserList = sysUserService.loadAllUsers(sysUser, curtPage, pageSize, currentUserResolver.getSysCompanyId());
        return ResponseDataUtil.buildSuccess(ResultEnums.SUCCESS.getCode(), "", sysUserList);
    }

    @GetMapping("/loadAdmin")
    @ApiOperation(value = "获取公司管理员")
    public ResponseData<SysUser> loadAdmin(){
        List<SysUser> sysUserList = sysUserService.loadAdmin();
        return ResponseDataUtil.buildSuccess(ResultEnums.SUCCESS.getCode(), "", sysUserList);
    }

    @PostMapping("/saveUser")
    @ApiOperation(value = "保存用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUser", value = "用户信息", required = true)
    })
    public ResponseData<SysUser> saveUser(
            @NotNull(message = "用户信息不能为空！") @RequestBody SysUser sysUser, @CurrentUser CurrentUserResolver currentUser) {
        try {
            //验证用户名、工号是否重复
            SysUser user = sysUserService.loadUserByUserName(sysUser.getUserName());
            if(!Objects.isNull(user)){
                return ResponseDataUtil.buildError("用户[" + sysUser.getUserName() + "]已存在！");
            }
            user = sysUserService.loadUserByJobNumber(sysUser.getJobNumber());
            if(!Objects.isNull(user)){
                return ResponseDataUtil.buildError("工号[" + sysUser.getJobNumber() + "]已存在！");
            }
            sysUser.setCreateId(currentUser.getId());
            sysUser.setUpdateId(currentUser.getId());
            sysUser.setSysCompanyId(currentUser.getSysCompanyId());

            //保存用户信息
            sysUserService.createUser(sysUser);

            return ResponseDataUtil.buildSuccess(ResultEnums.SUCCESS.getCode(),"添加成功！",sysUserService.loadUserByUserName(sysUser.getUserName()));
        } catch (ActionException e) {
            return ResponseDataUtil.buildError("添加失败！");
        }


    }


    @PostMapping("/updateUser")
    @ApiOperation(value = "更新用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUser", value = "用户信息", required = true)
    })
    public ResponseData<SysUser> updateUser(
            @NotNull(message = "用户信息不能为空") @RequestBody SysUser sysUser, @CurrentUser CurrentUserResolver currentUserResolver){
        try {
            //验证用户名、工号是否重复
            SysUser user = sysUserService.loadUserByUserName(sysUser.getUserName());
            if(!Objects.isNull(user) && user.getId().longValue() != sysUser.getId().longValue()){
                return ResponseDataUtil.buildError("用户[" + sysUser.getUserName() + "]已存在！");
            }
            user = sysUserService.loadUserByJobNumber(sysUser.getJobNumber());
            if(!Objects.isNull(user) && user.getId().longValue() != sysUser.getId().longValue()){
                return ResponseDataUtil.buildError("工号[" + sysUser.getJobNumber() + "]已存在！");
            }
            sysUser.setUpdateId(currentUserResolver.getId());
            sysUserService.updateUser(sysUser);
            return ResponseDataUtil.buildSuccess(ResultEnums.SUCCESS.getCode(),"修改成功！",sysUser);
        } catch (ActionException e) {
            return ResponseDataUtil.buildError("修改失败！");
        }
    }

    @GetMapping("/resetPassword")
    @ApiOperation(value = "重置密码")
    @ApiImplicitParam(name = "userName", value = "用户名", required = true, paramType = "query")
    public ResponseData resetPassword(@NotNull(message = "用户名不能为空") @RequestParam("userName") String userName){
        try {
            sysUserService.resetPassword(userName);
            return ResponseDataUtil.buildSuccess("更新成功！");
        } catch (ActionException e) {
            return ResponseDataUtil.buildError("更新失败！");
        }
    }

    @GetMapping("/loadRoleIdsByUserName")
    @ApiOperation(value = "获取用户对应的角色ID")
    @ApiImplicitParam(name = "userId", value = "用户 ID", required = true, paramType = "query")
    public ResponseData<List<Long>> loadRoleIdsByUserName(@NotNull(message = "用户名不能为空") @RequestParam("userId") Long userId){
        List<SysUserRoleRelation> sysUserRoleRelations = sysUserRoleRelationService.loadRoleIdsByUserId(userId);
        List<Long> roleIds = Collections.emptyList();
        if(!CollectionUtils.isEmpty(sysUserRoleRelations) && sysUserRoleRelations.size() > 0){
            roleIds = sysUserRoleRelations.stream().map(SysUserRoleRelation::getSysRoleId).collect(Collectors.toList());
        }
        return ResponseDataUtil.buildSuccess(ResultEnums.SUCCESS.getCode(), "", roleIds);
    }

    @GetMapping("/updateEnabled")
    @ApiOperation(value = "禁用/启用 用户")
    public ResponseData updateEnabled(@RequestParam("enabled") int enabled, @RequestParam("userName") String userName){
        try {
            sysUserService.updateEnabled(enabled, userName);
            return ResponseDataUtil.buildSuccess("更新成功！");
        } catch (ActionException e){
            return ResponseDataUtil.buildError("更新失败！");
        }
    }

}
