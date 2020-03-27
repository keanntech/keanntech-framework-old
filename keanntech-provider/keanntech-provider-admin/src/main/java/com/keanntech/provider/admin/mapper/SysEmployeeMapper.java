package com.keanntech.provider.admin.mapper;

import com.keanntech.common.model.po.SysEmployee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 员工信息表(SysEmployee)表数据库访问层
 *
 * @author eddey.miao
 * @since 2020-03-25 22:40:52
 */
public interface SysEmployeeMapper {

    /**
     * 根据USERID查询
     * @param userId
     * @return
     */
    SysEmployee selectOneByUserId(Long userId);

    /**
     * 根据工号查询
     * @param jobNumber
     * @return
     */
    SysEmployee queryByJobNumber(String jobNumber);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysEmployee 实例对象
     * @return 对象列表
     */
    List<SysEmployee> queryAll(SysEmployee sysEmployee);

    /**
     * 新增数据
     *
     * @param sysEmployee 实例对象
     * @return 影响行数
     */
    int insert(SysEmployee sysEmployee);

    /**
     * 修改数据
     *
     * @param sysEmployee 实例对象
     * @return 影响行数
     */
    int update(SysEmployee sysEmployee);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}