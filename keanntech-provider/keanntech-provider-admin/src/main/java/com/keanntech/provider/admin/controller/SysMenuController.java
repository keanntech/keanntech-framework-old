package com.keanntech.provider.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.keanntech.common.base.annotation.CurrentUser;
import com.keanntech.common.base.reponse.ResponseData;
import com.keanntech.common.base.reponse.ResponseDataUtil;
import com.keanntech.common.base.reponse.ResultEnums;
import com.keanntech.common.model.methodresolver.CurrentUserResolver;
import com.keanntech.common.model.po.SysMenu;
import com.keanntech.common.model.po.SysUser;
import com.keanntech.provider.admin.service.ISysMenuService;
import com.keanntech.provider.admin.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@Api("系统菜单")
@RestController
@RequestMapping("/api/admin/sysMenu")
public class SysMenuController {

    private ISysMenuService sysMenuService;
    private ISysUserService sysUserService;

    @Autowired
    public void setSysMenuService(ISysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    @Autowired
    public void setSysUserService(ISysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    /**
     * 根据角色ID获取菜单
     * @param roleIds
     * @return
     */
    @ApiOperation(value = "根据角色获取菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleIds", value = "角色ID", required = true, dataType = "List")
    })
    @PostMapping("/loadMenus")
    public ResponseData<SysMenu> loadMenusByRole(@RequestBody List<Long> roleIds, @CurrentUser CurrentUserResolver currentUser){
        SysUser sysUser = sysUserService.loadUserByUserName(currentUser.getUserName());
        List<SysMenu> sysMenus;
        if(sysUser.getSuperAdmin()){
            sysMenus = sysMenuService.loadAllSysMenus();
        }else{
            sysMenus = sysMenuService.loadSysMenus(roleIds);
        }

        if(Objects.isNull(sysMenus)){
            return ResponseDataUtil.buildError(ResultEnums.MENU_EMPTY.getCode(),"未设置菜单，请与管理员联系");
        }
        JSONArray jsonMenus = sysMenuService.createMenuTree(sysMenus);
        return ResponseDataUtil.buildSuccess(jsonMenus);
    }



}
