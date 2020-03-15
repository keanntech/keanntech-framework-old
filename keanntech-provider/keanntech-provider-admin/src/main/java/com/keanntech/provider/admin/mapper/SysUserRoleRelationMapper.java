package com.keanntech.provider.admin.mapper;

import com.keanntech.common.model.po.SysUserRoleRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户和角色的中间表sys_user_role_relation(SysUserRoleRelation)表数据库访问层
 *
 * @author eddey.miao
 * @since 2020-03-04 19:31:21
 */
public interface SysUserRoleRelationMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param sysUserId 主键
     * @return 实例对象
     */
    SysUserRoleRelation queryById(Long sysUserId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysUserRoleRelation> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysUserRoleRelation 实例对象
     * @return 对象列表
     */
    List<SysUserRoleRelation> queryAll(SysUserRoleRelation sysUserRoleRelation);

    List<SysUserRoleRelation> loadRoleIdsByUserId(Long userId);

    /**
     * 新增数据
     *
     * @param sysUserRoleRelation 实例对象
     * @return 影响行数
     */
    int insert(SysUserRoleRelation sysUserRoleRelation);

    /**
     * 批量插入
     */
    void batchInsert(List<SysUserRoleRelation> userRoleRelations);


    /**
     * 修改数据
     *
     * @param sysUserRoleRelation 实例对象
     * @return 影响行数
     */
    int update(SysUserRoleRelation sysUserRoleRelation);

    /**
     * 通过主键删除数据
     *
     * @param sysUserId 主键
     * @return 影响行数
     */
    int deleteById(Long sysUserId);

    /**
     * 根据用户ID删除对应权限
     * @param sysUserId
     * @return
     */
    int deleteByUserId(Long sysUserId);

}