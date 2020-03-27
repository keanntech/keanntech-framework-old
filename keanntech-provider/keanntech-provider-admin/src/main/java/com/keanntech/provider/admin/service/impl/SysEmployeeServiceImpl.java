package com.keanntech.provider.admin.service.impl;

import com.keanntech.common.model.po.SysEmployee;
import com.keanntech.provider.admin.mapper.SysEmployeeMapper;
import com.keanntech.provider.admin.service.ISysEmployeeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 员工信息表(SysEmployee)表服务实现类
 *
 * @author eddey.miao
 * @since 2020-03-25 22:40:54
 */
@Service("sysEmployeeService")
public class SysEmployeeServiceImpl implements ISysEmployeeService {
    @Resource
    private SysEmployeeMapper sysEmployeeMapper;

    @Override
    public SysEmployee selectOneByUserId(Long userId) {
        return this.sysEmployeeMapper.selectOneByUserId(userId);
    }

    @Override
    public SysEmployee queryByJobNumber(String jobNumber) {
        return this.sysEmployeeMapper.queryByJobNumber(jobNumber);
    }

    /**
     * 新增数据
     *
     * @param sysEmployee 实例对象
     * @return 实例对象
     */
    @Override
    public SysEmployee insert(SysEmployee sysEmployee) {
        this.sysEmployeeMapper.insert(sysEmployee);
        return sysEmployee;
    }

    /**
     * 修改数据
     *
     * @param sysEmployee 实例对象
     * @return 实例对象
     */
    @Override
    public SysEmployee update(SysEmployee sysEmployee) {
        this.sysEmployeeMapper.update(sysEmployee);
        return this.queryById(sysEmployee.getId());
    }

    @Override
    public SysEmployee queryById(Long id) {
        return this.queryById(id);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.sysEmployeeMapper.deleteById(id) > 0;
    }
}