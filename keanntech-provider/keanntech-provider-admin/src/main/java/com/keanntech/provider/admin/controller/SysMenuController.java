package com.keanntech.provider.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.keanntech.common.base.reponse.ResponseData;
import com.keanntech.common.base.reponse.ResponseDataUtil;
import com.keanntech.common.base.reponse.ResultEnums;
import com.keanntech.common.model.po.SysMenu;
import com.keanntech.provider.admin.service.ISysMenuService;
import com.keanntech.provider.admin.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @ApiImplicitParam(name = "roleIds", value = "角色ID", required = true, dataType = "List")
    @PostMapping("/loadMenus")
    public ResponseData<SysMenu> loadMenusByRole(@RequestBody List<Long> roleIds, @RequestParam("isAdmin") Boolean isAdmin){
        List<SysMenu> sysMenus;
        if(isAdmin){
            sysMenus = sysMenuService.loadAllSysMenus();
        }else{
            sysMenus = sysMenuService.loadSysMenus(roleIds);
        }

        if(Objects.isNull(sysMenus)){
            return ResponseDataUtil.buildError(ResultEnums.MENU_EMPTY.getCode(),"未分配角色，无法获取菜单");
        }
        JSONArray jsonMenus = sysMenuService.createMenuTree(sysMenus);
        return ResponseDataUtil.buildSuccess(ResultEnums.SUCCESS.getCode(), "", jsonMenus);
    }



}
