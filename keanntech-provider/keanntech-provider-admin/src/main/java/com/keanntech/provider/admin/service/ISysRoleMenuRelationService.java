package com.keanntech.provider.admin.service;

import com.keanntech.common.model.po.SysRoleMenuRelation;

import java.util.List;

/**
 * 角色资源表sys_role_menu_relation   资源表和角色表的中间表(SysRoleMenuRelation)表服务接口
 *
 * @author eddey.miao
 * @since 2020-02-28 19:55:05
 */
public interface ISysRoleMenuRelationService {

    /**
     * 通过ID查询单条数据
     *
     * @param sysMenuId 主键
     * @return 实例对象
     */
    SysRoleMenuRelation queryById(Long sysMenuId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysRoleMenuRelation> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysRoleMenuRelation 实例对象
     * @return 实例对象
     */
    SysRoleMenuRelation insert(SysRoleMenuRelation sysRoleMenuRelation);

    /**
     * 修改数据
     *
     * @param sysRoleMenuRelation 实例对象
     * @return 实例对象
     */
    SysRoleMenuRelation update(SysRoleMenuRelation sysRoleMenuRelation);

    /**
     * 通过主键删除数据
     *
     * @param sysMenuId 主键
     * @return 是否成功
     */
    boolean deleteById(Long sysMenuId);

}