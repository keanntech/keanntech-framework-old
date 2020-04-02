package com.keanntech.provider.admin.controller;

import com.github.pagehelper.PageInfo;
import com.keanntech.common.base.annotation.CurrentUser;
import com.keanntech.common.base.controller.BaseController;
import com.keanntech.common.base.exception.ActionException;
import com.keanntech.common.base.reponse.Result;
import com.keanntech.common.model.dto.UserDTO;
import com.keanntech.common.model.methodresolver.CurrentUserResolver;
import com.keanntech.common.model.po.SysEmployee;
import com.keanntech.common.model.po.SysUser;
import com.keanntech.common.model.po.SysUserRoleRelation;
import com.keanntech.provider.admin.service.ISysEmployeeService;
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
    private ISysEmployeeService sysEmployeeService;
    private ISysUserRoleRelationService sysUserRoleRelationService;

    @Autowired
    public void setSysUserService(ISysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @Autowired
    public void setSysEmployeeService(ISysEmployeeService sysEmployeeService){
        this.sysEmployeeService = sysEmployeeService;
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
    public Result<SysUser> loadUser(@NotNull(message = "用户名不能为空！") @RequestParam("userName") String userName) {
        SysUser sysUser = sysUserService.loadUserByUserName(userName);
        if(Objects.isNull(sysUser)){
            throw new ActionException("用户" + userName + "不存在！");
        }
        sysUser.setPassword("");
        return Result.ok().data("data", sysUser);
    }

    @PostMapping("/loadAllUsers")
    @ApiOperation(value = "分页获取所有用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "curtPage", value = "当前页", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", paramType = "query")
    })
    public Result<SysUser> loadAllUsers(@RequestBody SysUser sysUser, @RequestParam("curtPage") int curtPage, @RequestParam("pageSize") int pageSize, @CurrentUser CurrentUserResolver currentUserResolver){
        PageInfo<SysUser> sysUserList = sysUserService.loadAllUsers(sysUser, curtPage, pageSize, currentUserResolver.getSysCompanyId());
        return Result.ok().data("data", sysUserList);
    }

    @GetMapping("/loadAdmin")
    @ApiOperation(value = "获取公司管理员")
    public Result<SysUser> loadAdmin(){
        List<SysUser> sysUserList = sysUserService.loadAdmin();
        return Result.ok().data("data", sysUserList);
    }

    @PostMapping("/saveUser")
    @ApiOperation(value = "保存用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUser", value = "用户信息", required = true)
    })
    public Result<SysUser> saveUser(
            @NotNull(message = "用户信息不能为空！") @RequestBody UserDTO userDTO,
            @CurrentUser CurrentUserResolver currentUser) {
        try {
            //验证用户名、工号是否重复
            SysUser user = sysUserService.loadUserByUserName(userDTO.getSysUser().getUserName());
            if(!Objects.isNull(user)){
                throw new ActionException("用户[" + userDTO.getSysUser().getUserName() + "]已存在！");
            }
            SysEmployee employee = sysEmployeeService.queryByJobNumber(userDTO.getSysEmployee().getJobNumber());
            if(!Objects.isNull(employee)){
                throw new ActionException("工号[" + employee.getJobNumber() + "]已存在！");
            }
            userDTO.getSysUser().setCreateId(currentUser.getId());
            userDTO.getSysUser().setUpdateId(currentUser.getId());
            userDTO.getSysEmployee().setCreateId(currentUser.getId());
            userDTO.getSysEmployee().setUpdateId(currentUser.getId());

            //保存用户信息
            sysUserService.createUser(userDTO);

            return Result.ok().message("保存成功").data("data", sysUserService.loadUserByUserName(userDTO.getSysUser().getUserName()));
        } catch (Exception e) {
            throw new ActionException("添加失败");
        }


    }


    @PostMapping("/updateUser")
    @ApiOperation(value = "更新用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysUser", value = "用户信息", required = true)
    })
    public Result<SysUser> updateUser(
            @NotNull(message = "用户信息不能为空") @RequestBody UserDTO userDTO, @CurrentUser CurrentUserResolver currentUserResolver) {
        try {
            //验证用户名、工号是否重复
            SysUser user = sysUserService.loadUserByUserName(userDTO.getSysUser().getUserName());
            if(!Objects.isNull(user) && user.getId().longValue() != userDTO.getSysUser().getId().longValue()){
                throw new ActionException("用户[" + userDTO.getSysUser().getUserName() + "]已存在！");
            }

            SysEmployee employee = sysEmployeeService.queryByJobNumber(userDTO.getSysEmployee().getJobNumber());
            if(!Objects.isNull(employee) && employee.getSysUserId().longValue() != userDTO.getSysUser().getId().longValue()){
                throw new ActionException("工号[" + userDTO.getSysEmployee().getJobNumber() + "]已存在！");
            }
            userDTO.getSysUser().setUpdateId(currentUserResolver.getId());
            userDTO.getSysEmployee().setUpdateId(currentUserResolver.getId());
            sysUserService.updateUser(userDTO);
            return Result.ok().message("修改成功").data("data", sysUserService.loadUserByUserName(userDTO.getSysUser().getUserName()));
        } catch (Exception e) {
            throw new ActionException("修改失败");
        }
    }

    @GetMapping("/resetPassword")
    @ApiOperation(value = "重置密码")
    @ApiImplicitParam(name = "userName", value = "用户名", required = true, paramType = "query")
    public Result resetPassword(@NotNull(message = "用户名不能为空") @RequestParam("userName") String userName) {
        try {
            sysUserService.resetPassword(userName);
            return Result.ok().message("更新成功");
        } catch (Exception e) {
            throw new ActionException("更新失败");
        }
    }

    @GetMapping("/loadRoleIdsByUserName")
    @ApiOperation(value = "获取用户对应的角色ID")
    @ApiImplicitParam(name = "userId", value = "用户 ID", required = true, paramType = "query")
    public Result<List<Long>> loadRoleIdsByUserName(@NotNull(message = "用户名不能为空") @RequestParam("userId") Long userId){
        List<SysUserRoleRelation> sysUserRoleRelations = sysUserRoleRelationService.loadRoleIdsByUserId(userId);
        List<Long> roleIds = Collections.emptyList();
        if(!CollectionUtils.isEmpty(sysUserRoleRelations) && sysUserRoleRelations.size() > 0){
            roleIds = sysUserRoleRelations.stream().map(SysUserRoleRelation::getSysRoleId).collect(Collectors.toList());
        }
        return Result.ok().data("data",roleIds);
    }

    @GetMapping("/updateEnabled")
    @ApiOperation(value = "禁用/启用 用户")
    public Result updateEnabled(@RequestParam("enabled") int enabled, @RequestParam("userName") String userName) {
        try {
            sysUserService.updateEnabled(enabled, userName);
            return Result.ok().message("更新成功");
        } catch (Exception e){
            throw new ActionException("更新失败");
        }
    }

}
