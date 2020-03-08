package com.keanntech.provider.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.keanntech.common.model.po.SysMenu;
import com.keanntech.provider.admin.mapper.SysMenuMapper;
import com.keanntech.provider.admin.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 系统菜单sys_menu
 *
 * @author eddey.miao
 * @since 2020-02-13 20:03:02
 */
@Service("sysMenuService")
public class SysMenuServiceImpl implements ISysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
     * 根据角色ID加载该角色拥有的菜单
     * @param roleIds
     * @return
     */
    @Override
    public List<SysMenu> loadSysMenus(List<Long> roleIds){
        return sysMenuMapper.loadSysMenus(roleIds);
    }

    /**
     * 创建菜单树
     * @param sysMenus
     * @return
     */
    @Override
    public JSONArray createMenuTree(List<SysMenu> sysMenus) {

        //获取父ID为0的菜单即根菜单
        List<SysMenu> rootMenus = sysMenus.stream().filter(sysMenu -> sysMenu.getParentId().longValue() == 0).collect(Collectors.toList());
        final JSONArray menuTree = new JSONArray();
        //遍历根菜单
        rootMenus.stream().forEach(rootMenu -> {
            JSONObject menuItem = setMenuItem(rootMenu);
            //获取子菜单
            JSONArray children = this.getChildren(rootMenu.getId(), sysMenus);
            menuItem.put("children", children);
            menuTree.add(menuItem);
        });

        return menuTree;
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysMenu queryById(Long id) {
        return this.sysMenuMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysMenu> queryAllByLimit(int offset, int limit) {
        return this.sysMenuMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysMenu 实例对象
     * @return 实例对象
     */
    @Override
    public SysMenu insert(SysMenu sysMenu) {
        this.sysMenuMapper.insert(sysMenu);
        return sysMenu;
    }

    /**
     * 修改数据
     *
     * @param sysMenu 实例对象
     * @return 实例对象
     */
    @Override
    public SysMenu update(SysMenu sysMenu) {
        this.sysMenuMapper.update(sysMenu);
        return this.queryById(sysMenu.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.sysMenuMapper.deleteById(id) > 0;
    }


    /**
     * 获取给定的父ID下的子菜单
     * @param parentId  父ID
     * @param sysMenus  角色拥有的菜单集合
     * @return
     */
    private JSONArray getChildren(Long parentId, List<SysMenu> sysMenus){
        //获取父ID下的子菜单
        List<SysMenu> childMenus = sysMenus.stream().filter(sysMenu -> sysMenu.getParentId().longValue() == parentId).collect(Collectors.toList());

        JSONArray menuTree = new JSONArray();
        if(Objects.isNull(childMenus) || childMenus.size() == 0){
            return menuTree;
        }
        for(int i = 0, len = childMenus.size(); i < len; i++){
            SysMenu menu = childMenus.get(i);
            JSONObject menuItem = setMenuItem(menu);
            JSONArray children = this.getChildren(menu.getId(), sysMenus);
            menuItem.put("children", children);
            menuTree.add(menuItem);
        }
        return menuTree;
    }

    /**
     * 设置菜单属性值
     * @param sysMenu
     * @return
     */
    private JSONObject setMenuItem(SysMenu sysMenu){
        JSONObject menuItem = new JSONObject();
        menuItem.put("path", sysMenu.getPath());
        menuItem.put("component", sysMenu.getComponent());
        menuItem.put("redirect", sysMenu.getRedirect());
        menuItem.put("name", sysMenu.getMenuName());

        JSONObject meta = new JSONObject();
        meta.put("title", sysMenu.getMenuTitle());
        meta.put("icon", sysMenu.getIcon());
        meta.put("noCache", true);
        menuItem.put("meta", meta);
        return menuItem;
    }
}