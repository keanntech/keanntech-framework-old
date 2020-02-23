package com.keanntech.provider.admin.service.impl;

import com.keanntech.common.model.po.SysDepartment;
import com.keanntech.provider.admin.mapper.SysDepartmentMapper;
import com.keanntech.provider.admin.service.SysDepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统组织架构表sys_department 如，xx公司--xx部门(SysDepartment)表服务实现类
 *
 * @author makejava
 * @since 2020-02-13 20:29:31
 */
@Service("sysDepartmentService")
public class SysDepartmentServiceImpl implements SysDepartmentService {
    @Resource
    private SysDepartmentMapper sysDepartmentMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysDepartment queryById(Long id) {
        return this.sysDepartmentMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysDepartment> queryAllByLimit(int offset, int limit) {
        return this.sysDepartmentMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param sysDepartment 实例对象
     * @return 实例对象
     */
    @Override
    public SysDepartment insert(SysDepartment sysDepartment) {
        this.sysDepartmentMapper.insert(sysDepartment);
        return sysDepartment;
    }

    /**
     * 修改数据
     *
     * @param sysDepartment 实例对象
     * @return 实例对象
     */
    @Override
    public SysDepartment update(SysDepartment sysDepartment) {
        this.sysDepartmentMapper.update(sysDepartment);
        return this.queryById(sysDepartment.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.sysDepartmentMapper.deleteById(id) > 0;
    }
}