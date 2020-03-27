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

    List<SysCompany> loadAllCompanies();

    int saveCompany(SysCompany sysCompany);

    /**
     * 修改数据
     *
     * @param sysCompany 实例对象
     * @return 实例对象
     */
    SysCompany update(SysCompany sysCompany);

}