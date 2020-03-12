package com.keanntech.provider.admin.mapper;

import com.keanntech.common.model.po.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统菜单sys_menu, 资源树，按钮(SysMenu)表数据库访问层
 *
 * @author makejava
 * @since 2020-02-13 20:03:00
 */
@Mapper
public interface SysMenuMapper {

    /**
     * 根据角色加载菜单
     * @param roleIds
     * @return
     */
    List<SysMenu> loadSysMenus(List<Long> roleIds);

    /**
     * 加载全部菜单
     * @return
     */
    List<SysMenu> loadAllSysMenus();

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
     * @return 影响行数
     */
    int insert(SysMenu sysMenu);

    /**
     * 修改数据
     *
     * @param sysMenu 实例对象
     * @return 影响行数
     */
    int update(SysMenu sysMenu);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}