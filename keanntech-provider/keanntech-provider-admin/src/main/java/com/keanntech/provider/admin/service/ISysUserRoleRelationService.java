package com.keanntech.provider.admin.service;

import com.keanntech.common.model.po.SysUserRoleRelation;

import java.util.List;

/**
 * 系统用户和角色的中间表sys_user_role_relation(SysUserRoleRelation)表服务接口
 *
 * @author eddey.miao
 * @since 2020-03-04 19:29:18
 */
public interface ISysUserRoleRelationService {

    /**
     * 通过ID查询单条数据
     *
     * @param sysUserId 主键
     * @return 实例对象
     */
    SysUserRoleRelation queryById(Long sysUserId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysUserRoleRelation> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysUserRoleRelation 实例对象
     * @return 实例对象
     */
    SysUserRoleRelation insert(SysUserRoleRelation sysUserRoleRelation);

    void batchInsert(Long sysUserId, List<Long> roleIds);

    /**
     * 修改数据
     *
     * @param sysUserRoleRelation 实例对象
     * @return 实例对象
     */
    SysUserRoleRelation update(SysUserRoleRelation sysUserRoleRelation);

    /**
     * 通过主键删除数据
     *
     * @param sysUserId 主键
     * @return 是否成功
     */
    boolean deleteById(Long sysUserId);

}