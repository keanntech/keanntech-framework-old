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
     * 查询所有未删除的数据
     * @return
     */
    List<SysCompany> loadAllCompanies();

    int saveCompany(SysCompany sysCompany);

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