package com.keanntech.provider.admin.mapper;

import com.keanntech.common.model.po.SysRoleMenuRelation;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 角色资源表sys_role_menu_relation   资源表和角色表的中间表(SysRoleMenuRelation)表数据库访问层
 *
 * @author eddey.miao
 * @since 2020-02-28 19:54:13
 */
public interface SysRoleMenuRelationMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param sysMenuId 主键
     * @return 实例对象
     */
    SysRoleMenuRelation queryById(Long sysMenuId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysRoleMenuRelation> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysRoleMenuRelation 实例对象
     * @return 对象列表
     */
    List<SysRoleMenuRelation> queryAll(SysRoleMenuRelation sysRoleMenuRelation);

    /**
     * 新增数据
     *
     * @param sysRoleMenuRelation 实例对象
     * @return 影响行数
     */
    int insert(SysRoleMenuRelation sysRoleMenuRelation);

    /**
     * 修改数据
     *
     * @param sysRoleMenuRelation 实例对象
     * @return 影响行数
     */
    int update(SysRoleMenuRelation sysRoleMenuRelation);

    /**
     * 通过主键删除数据
     *
     * @param sysMenuId 主键
     * @return 影响行数
     */
    int deleteById(Long sysMenuId);

}