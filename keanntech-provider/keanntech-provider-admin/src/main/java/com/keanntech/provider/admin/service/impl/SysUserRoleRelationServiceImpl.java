package com.keanntech.provider.admin.service.impl;

import com.keanntech.common.model.po.SysUser;
import com.keanntech.common.model.po.SysUserRoleRelation;
import com.keanntech.provider.admin.mapper.SysUserMapper;
import com.keanntech.provider.admin.mapper.SysUserRoleRelationMapper;
import com.keanntech.provider.admin.service.ISysUserRoleRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统用户和角色的中间表sys_user_role_relation(SysUserRoleRelation)表服务实现类
 *
 * @author makejava
 * @since 2020-03-04 19:29:18
 */
@Service("sysUserRoleRelationService")
public class SysUserRoleRelationServiceImpl implements ISysUserRoleRelationService {

    private SysUserRoleRelationMapper sysUserRoleRelationMapper;
    private SysUserMapper sysUserMapper;

    @Autowired
    public void setSysUserRoleRelationMapper(SysUserRoleRelationMapper sysUserRoleRelationMapper) {
        this.sysUserRoleRelationMapper = sysUserRoleRelationMapper;
    }

    @Autowired
    public void setSysUserMapper(SysUserMapper sysUserMapper){
        this.sysUserMapper = sysUserMapper;
    }

    /**
     * 通过ID查询单条数据
     *
     * @param sysUserId 主键
     * @return 实例对象
     */
    @Override
    public SysUserRoleRelation queryById(Long sysUserId) {
        return this.sysUserRoleRelationMapper.queryById(sysUserId);
    }

    @Override
    public List<SysUserRoleRelation> loadRoleIdsByUserId(Long userId) {
        return sysUserRoleRelationMapper.loadRoleIdsByUserId(userId);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysUserRoleRelation> queryAllByLimit(int offset, int limit) {
        return this.sysUserRoleRelationMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysUserRoleRelation 实例对象
     * @return 实例对象
     */
    @Override
    public SysUserRoleRelation insert(SysUserRoleRelation sysUserRoleRelation) {
        this.sysUserRoleRelationMapper.insert(sysUserRoleRelation);
        return sysUserRoleRelation;
    }

    @Override
    public void batchInsert(Long sysUserId, List<Long> roleIds) {
        List<SysUserRoleRelation> userRoleRelations = new ArrayList(roleIds.size());
        roleIds.stream().forEach(id -> {
            SysUserRoleRelation sysUserRoleRelation = new SysUserRoleRelation();
            sysUserRoleRelation.setSysRoleId(id);
            sysUserRoleRelation.setSysUserId(sysUserId);
            userRoleRelations.add(sysUserRoleRelation);
        });
        this.sysUserRoleRelationMapper.batchInsert(userRoleRelations);
    }

    /**
     * 修改数据
     *
     * @param sysUserRoleRelation 实例对象
     * @return 实例对象
     */
    @Override
    public SysUserRoleRelation update(SysUserRoleRelation sysUserRoleRelation) {
        this.sysUserRoleRelationMapper.update(sysUserRoleRelation);
        return this.queryById(sysUserRoleRelation.getSysUserId());
    }

    /**
     * 通过主键删除数据
     *
     * @param sysUserId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long sysUserId) {
        return this.sysUserRoleRelationMapper.deleteById(sysUserId) > 0;
    }

    @Override
    public boolean deleteByUserId(Long sysUserId) {
        return this.sysUserRoleRelationMapper.deleteByUserId(sysUserId) > 0;
    }
}