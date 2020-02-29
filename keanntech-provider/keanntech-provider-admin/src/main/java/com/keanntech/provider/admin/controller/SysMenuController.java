package com.keanntech.provider.admin.controller;

import com.keanntech.common.base.reponse.ResponseData;
import com.keanntech.common.base.reponse.ResponseDataUtil;
import com.keanntech.common.base.reponse.ResultEnums;
import com.keanntech.common.model.po.SysMenu;
import com.keanntech.common.model.po.SysRole;
import com.keanntech.common.model.po.SysUser;
import com.keanntech.provider.admin.service.ISysMenuService;
import com.keanntech.provider.admin.service.ISysUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Api("系统菜单")
@RestController
@RequestMapping("/api/admin/sysMenu")
public class SysMenuController {

    @Autowired
    ISysMenuService sysMenuService;

    @Autowired
    ISysUserService sysUserService;

    @GetMapping("/loadMenus")
    public ResponseData<SysMenu> loadMenus(@RequestParam("userName") String userName){

        SysUser sysUser = sysUserService.loadUser(userName);
        if(Objects.isNull(sysUser)){
            return ResponseDataUtil.buildSuccess(ResultEnums.MENU_EMPTY.getCode(),"未设置菜单，请与管理员联系",null);
        }

        List<SysRole> roles = sysUser.getUserRoles();
        if(Objects.isNull(roles)){
            return ResponseDataUtil.buildSuccess(ResultEnums.MENU_EMPTY.getCode(),"未设置菜单，请与管理员联系",null);
        }

        List<Long> roleIds = roles.stream().map(SysRole::getId).collect(Collectors.toList());

        List<SysMenu> sysMenus = sysMenuService.loadSysMenus(roleIds);
        if(Objects.isNull(sysMenus)){
            return ResponseDataUtil.buildSuccess(ResultEnums.MENU_EMPTY.getCode(),"未设置菜单，请与管理员联系",null);
        }

        return ResponseDataUtil.buildSuccess(sysMenus);
    }

}
