package com.keanntech.provider.admin.service;

import com.alibaba.fastjson.JSONArray;
import com.keanntech.common.model.po.SysMenu;

import java.util.List;

/**
 * 系统菜单sys_menu, 资源树，按钮(SysMenu)表服务接口
 *
 * @author eddey.miao
 * @since 2020-02-13 20:03:01
 */
public interface ISysMenuService {

    /**
     * 根据角色ID查询该角色下的菜单
     * @param roleIds
     * @return
     */
    List<SysMenu> loadSysMenus(List<Long> roleIds);

    List<SysMenu> loadAllSysMenus();

    /**
     * 创建菜单树
     * @param sysMenus
     * @return
     */
    JSONArray createMenuTree(List<SysMenu> sysMenus);

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysMenu queryById(Long id);

    /**
     * 新增数据
     *
     * @param sysMenu 实例对象
     * @return 实例对象
     */
    SysMenu insert(SysMenu sysMenu);

    /**
     * 修改数据
     *
     * @param sysMenu 实例对象
     * @return 实例对象
     */
    SysMenu update(SysMenu sysMenu);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}