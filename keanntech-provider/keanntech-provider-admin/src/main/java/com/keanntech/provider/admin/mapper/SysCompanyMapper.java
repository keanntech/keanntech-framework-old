package com.keanntech.provider.admin.mapper;

import com.keanntech.common.model.po.SysCompany;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 公司表，为了实现对不同公司提供服务，提供出一个公司信息表(SysCompany)表数据库访问层
 *
 * @author eddey.miao
 * @since 2020-03-09 21:39:48
 */
public interface SysCompanyMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysCompany queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysCompany> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 查询所有未删除的数据
     * @return
     */
    List<SysCompany> loadAllCompanies();

    int saveCompany(SysCompany sysCompany);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param sysCompany 实例对象
     * @return 对象列表
     */
    List<SysCompany> queryAll(SysCompany sysCompany);

    /**
     * 修改数据
     *
     * @param sysCompany 实例对象
     * @return 影响行数
     */
    int update(SysCompany sysCompany);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}