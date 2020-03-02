package com.keanntech.provider.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

import javax.validation.constraints.NotNull;
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
    public ResponseData<SysMenu> loadMenus(@NotNull(message = "用户名不能为空！") @RequestParam("userName") String userName){

        SysUser sysUser = sysUserService.loadUser(userName);
        if(Objects.isNull(sysUser)){
            return ResponseDataUtil.buildSuccess(ResultEnums.USER_NOT_EXIST.getCode(),"用户不存在！",null);
        }

        List<SysRole> roles = sysUser.getUserRoles();
        if(Objects.isNull(roles)){
            return ResponseDataUtil.buildSuccess(ResultEnums.ROLE_EMPTY.getCode(),"未分配角色，无法获取菜单！",null);
        }

        List<Long> roleIds = roles.stream().map(SysRole::getId).collect(Collectors.toList());

        List<SysMenu> sysMenus = sysMenuService.loadSysMenus(roleIds);
        if(Objects.isNull(sysMenus)){
            return ResponseDataUtil.buildSuccess(ResultEnums.MENU_EMPTY.getCode(),"未设置菜单，请与管理员联系",null);
        }

        List<SysMenu> rootMenus = sysMenus.stream().filter(sysMenu -> sysMenu.getParentId().longValue() == 0).collect(Collectors.toList());
        final JSONArray jsonMenus = new JSONArray();
        rootMenus.stream().forEach(rootMenu -> {
            JSONObject menu = new JSONObject();
            menu.put("path", rootMenu.getPath());
            menu.put("component", rootMenu.getComponent());
            menu.put("redirect", rootMenu.getRedirect());
            menu.put("name", rootMenu.getMenuName());
            JSONObject meta = new JSONObject();
            meta.put("title", rootMenu.getMenuTitle());
            meta.put("icon", rootMenu.getIcon());
            meta.put("noCache", true);
            menu.put("meta", meta);
            //调用获取子菜单方法
            JSONArray children = this.getChildren(rootMenu.getId(), sysMenus);
            menu.put("children", children);
            jsonMenus.add(menu);
        });

        return ResponseDataUtil.buildSuccess(jsonMenus);
    }

    private JSONArray getChildren(Long parentId, List<SysMenu> sysMenus){
        List<SysMenu> menus = sysMenus.stream().filter(sysMenu -> sysMenu.getParentId().longValue() == parentId).collect(Collectors.toList());

        JSONArray jsonMenus = new JSONArray();

        if(Objects.isNull(menus) || menus.size() == 0){
            return jsonMenus;
        }
        for(int i = 0, len = menus.size(); i < len; i++){
            SysMenu menu = menus.get(i);

            JSONObject cldMenu = new JSONObject();
            cldMenu.put("path", menu.getPath());
            cldMenu.put("component", menu.getComponent());
            cldMenu.put("redirect", menu.getRedirect());
            cldMenu.put("name", menu.getMenuName());
            JSONObject meta = new JSONObject();
            meta.put("title", menu.getMenuTitle());
            meta.put("icon", menu.getIcon());
            meta.put("noCache", true);
            cldMenu.put("meta", meta);
            JSONArray children = this.getChildren(menu.getId(), sysMenus);
            cldMenu.put("children", children);
            jsonMenus.add(cldMenu);
        }
        return jsonMenus;
    }

}
