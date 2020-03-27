package com.keanntech.provider.admin.service;

import com.keanntech.common.model.po.SysEmployee;

import java.util.List;

/**
 * 员工信息表(SysEmployee)表服务接口
 *
 * @author eddey.miao
 * @since 2020-03-25 22:40:53
 */
public interface ISysEmployeeService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysEmployee queryById(Long id);

    SysEmployee selectOneByUserId(Long userId);

    SysEmployee queryByJobNumber(String jobNumber);

    /**
     * 新增数据
     *
     * @param sysEmployee 实例对象
     * @return 实例对象
     */
    SysEmployee insert(SysEmployee sysEmployee);

    /**
     * 修改数据
     *
     * @param sysEmployee 实例对象
     * @return 实例对象
     */
    SysEmployee update(SysEmployee sysEmployee);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}