package com.keanntech.provider.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.keanntech.common.base.annotation.CurrentUser;
import com.keanntech.common.base.exception.ActionException;
import com.keanntech.common.base.reponse.Result;
import com.keanntech.common.model.methodresolver.CurrentUserResolver;
import com.keanntech.common.model.po.SysMenu;
import com.keanntech.provider.admin.service.ISysMenuService;
import com.keanntech.provider.admin.service.ISysUserService;
import io.jsonwebtoken.lang.Collections;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    public Result<JSONArray> loadMenusByRole(@RequestBody List<Long> roleIds, @RequestParam("isAdmin") Boolean isAdmin) {
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

    @ApiOperation(value = "获取所有菜单")
    @GetMapping("/loadAllMenus")
    public Result<JSONArray> loadAllMenus() {
        List<SysMenu> sysMenus = sysMenuService.loadAllSysMenus();
        if(Objects.isNull(sysMenus)){
            throw new ActionException("未分配角色，无法获取菜单");
        }
        JSONArray jsonMenus = sysMenuService.createMenuTree(sysMenus);
        return Result.ok().data("data",jsonMenus).message("");
    }

    @ApiOperation(value = "根据父菜单ID获取菜单")
    @ApiImplicitParam(name = "parentId", value = "父菜单ID", required = true, dataType = "Long")
    @GetMapping("/loadMenuByParentId")
    public Result<SysMenu> loadMenuByParentId(@NotNull(message = "父ID不能为空") @RequestParam("parentId") Long parentId){
        List<SysMenu> sysMenuList = sysMenuService.loadMenusByParentId(parentId);
        return Result.ok().data("data", sysMenuList).message("");
    }

    @ApiOperation(value = "保存菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysMenu", value = "菜单信息", required = true, paramType = "body", dataType = "SysMenu")
    })
    @PostMapping("/saveMenu")
    public Result<SysMenu> saveMenu(@RequestBody SysMenu sysMenu, @CurrentUser CurrentUserResolver currentUser) {
        SysMenu sysMenu1;
        try {
            sysMenu.setUpdateId(currentUser.getId());
            sysMenu.setCreateId(currentUser.getId());
            sysMenu1 = sysMenuService.insert(sysMenu);
        } catch (DataAccessException e) {
            throw new ActionException("保存失败");
        }
        return Result.ok().data("data", sysMenu1);
    }

    @ApiOperation(value = "删除菜单")
    @ApiImplicitParam(name = "id", value = "菜单ID", required = true, paramType = "query", dataType = "Long")
    @GetMapping("/delMenuById")
    public Result delMenuById(@RequestParam("id") Long id){
        List<SysMenu> sysMenuList = sysMenuService.loadMenusByParentId(id);
        if(Collections.isEmpty(sysMenuList)){
            sysMenuService.deleteById(id);
            return Result.ok().message("删除成功！");
        }
        throw new ActionException("此菜单下包含子菜单，请先删除子菜单！");
    }

    @ApiOperation(value = "更新菜单")
    @ApiImplicitParam(name = "sysMenu", value = "待更新的菜单", required = true, paramType = "body", dataType = "SysMenu")
    @PostMapping("/updateMenu")
    public Result updateMenu(@RequestBody SysMenu sysMenu, @CurrentUser CurrentUserResolver currentUser) {
        try {
            sysMenu.setUpdateId(currentUser.getId());
            sysMenuService.update(sysMenu);
            return Result.ok().message("修改成功");
        } catch (DataAccessException e) {
            throw new ActionException("修改失败");
        }
    }



}
