package com.keanntech.provider.admin.service;

import com.keanntech.common.model.po.SysDepartment;

import java.util.List;

/**
 * 系统组织架构表sys_department 如，xx公司--xx部门(SysDepartment)表服务接口
 *
 * @author eddey.miao
 * @since 2020-02-13 20:29:31
 */
public interface SysDepartmentService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysDepartment queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysDepartment> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param sysDepartment 实例对象
     * @return 实例对象
     */
    SysDepartment insert(SysDepartment sysDepartment);

    /**
     * 修改数据
     *
     * @param sysDepartment 实例对象
     * @return 实例对象
     */
    SysDepartment update(SysDepartment sysDepartment);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}