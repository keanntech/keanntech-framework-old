package com.keanntech.provider.admin.service.impl;

import com.keanntech.common.model.po.SysRoleMenuRelation;
import com.keanntech.provider.admin.mapper.SysRoleMenuRelationMapper;
import com.keanntech.provider.admin.service.SysRoleMenuRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色资源表sys_role_menu_relation   资源表和角色表的中间表(SysRoleMenuRelation)表服务实现类
 *
 * @author miaoqingfu
 * @since 2020-02-28 19:55:06
 */
@Service("sysRoleMenuRelationService")
public class SysRoleMenuRelationServiceImpl implements SysRoleMenuRelationService {
    @Resource
    private SysRoleMenuRelationMapper sysRoleMenuRelationMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param sysMenuId 主键
     * @return 实例对象
     */
    @Override
    public SysRoleMenuRelation queryById(Long sysMenuId) {
        return this.sysRoleMenuRelationMapper.queryById(sysMenuId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysRoleMenuRelation> queryAllByLimit(int offset, int limit) {
        return this.sysRoleMenuRelationMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysRoleMenuRelation 实例对象
     * @return 实例对象
     */
    @Override
    public SysRoleMenuRelation insert(SysRoleMenuRelation sysRoleMenuRelation) {
        this.sysRoleMenuRelationMapper.insert(sysRoleMenuRelation);
        return sysRoleMenuRelation;
    }

    /**
     * 修改数据
     *
     * @param sysRoleMenuRelation 实例对象
     * @return 实例对象
     */
    @Override
    public SysRoleMenuRelation update(SysRoleMenuRelation sysRoleMenuRelation) {
        this.sysRoleMenuRelationMapper.update(sysRoleMenuRelation);
        return this.queryById(sysRoleMenuRelation.getSysMenuId());
    }

    /**
     * 通过主键删除数据
     *
     * @param sysMenuId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long sysMenuId) {
        return this.sysRoleMenuRelationMapper.deleteById(sysMenuId) > 0;
    }
}