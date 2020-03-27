package com.keanntech.provider.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.keanntech.common.base.exception.ActionException;
import com.keanntech.common.base.reponse.Result;
import com.keanntech.common.model.po.SysMenu;
import com.keanntech.provider.admin.service.ISysMenuService;
import com.keanntech.provider.admin.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
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
    public Result<SysMenu> loadMenusByRole(@RequestBody List<Long> roleIds, @RequestParam("isAdmin") Boolean isAdmin){
        List<SysMenu> sysMenus;
        if(isAdmin){
            sysMenus = sysMenuService.loadAllSysMenus();
        }else{
            sysMenus = sysMenuService.loadSysMenus(roleIds);
        }

        if(Objects.isNull(sysMenus)){
            throw new ActionException("未分配角色，无法获取菜单");
        }
        JSONArray jsonMenus = sysMenuService.createMenuTree(sysMenus);
        return Result.ok().data("data",jsonMenus).message("");
    }

    @GetMapping("/loadMenuByParentId")
    public Result<SysMenu> loadMenuByParentId(@NotNull(message = "父ID不能为空") @RequestParam("parentId") Long parentId){
        List<SysMenu> sysMenuList = sysMenuService.loadMenusByParentId(parentId);
        return Result.ok().data("data", sysMenuList).message("");
    }

}
