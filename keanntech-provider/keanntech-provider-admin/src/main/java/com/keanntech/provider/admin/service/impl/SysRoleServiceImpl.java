package com.keanntech.provider.admin.service.impl;

import com.keanntech.common.model.po.SysRole;
import com.keanntech.provider.admin.mapper.SysRoleMapper;
import com.keanntech.provider.admin.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统角色表sys_role(SysRole)表服务实现类
 *
 * @author eddey.miao
 * @since 2020-03-03 16:01:44
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements ISysRoleService {

    private SysRoleMapper sysRoleMapper;

    @Autowired
    public void setSysRoleMapper(SysRoleMapper sysRoleMapper) {
        this.sysRoleMapper = sysRoleMapper;
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysRole queryById(Long id) {
        return this.sysRoleMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysRole> queryAllByLimit(int offset, int limit) {
        return this.sysRoleMapper.queryAllByLimit(offset, limit);
    }

    @Override
    public List<SysRole> queryAll(SysRole sysRole) {
        return this.sysRoleMapper.queryAll(sysRole);
    }

    /**
     * 新增数据
     *
     * @param sysRole 实例对象
     * @return 实例对象
     */
    @Override
    public SysRole insert(SysRole sysRole) {
        this.sysRoleMapper.insert(sysRole);
        return sysRole;
    }

    /**
     * 修改数据
     *
     * @param sysRole 实例对象
     * @return 实例对象
     */
    @Override
    public SysRole update(SysRole sysRole) {
        this.sysRoleMapper.update(sysRole);
        return this.queryById(sysRole.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.sysRoleMapper.deleteById(id) > 0;
    }
}