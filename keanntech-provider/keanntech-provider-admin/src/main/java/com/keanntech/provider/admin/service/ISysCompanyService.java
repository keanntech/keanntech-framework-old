package com.keanntech.provider.admin.service;

import com.keanntech.common.model.po.SysCompany;

import java.util.List;

/**
 * 公司表，为了实现对不同公司提供服务，提供出一个公司信息表(SysCompany)表服务接口
 *
 * @author eddey.miao
 * @since 2020-03-09 21:39:51
 */
public interface ISysCompanyService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysCompany queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysCompany> queryAllByLimit(int offset, int limit);

    List<SysCompany> loadAllCompanies();

    int saveCompany(SysCompany sysCompany);

    /**
     * 修改数据
     *
     * @param sysCompany 实例对象
     * @return 实例对象
     */
    SysCompany update(SysCompany sysCompany);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}