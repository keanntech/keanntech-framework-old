package com.keanntech.provider.admin.service.impl;

import com.keanntech.common.model.po.SysCompany;
import com.keanntech.provider.admin.mapper.SysCompanyMapper;
import com.keanntech.provider.admin.service.ISysCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公司表，为了实现对不同公司提供服务，提供出一个公司信息表(SysCompany)表服务实现类
 *
 * @author eddey.miao
 * @since 2020-03-09 21:39:53
 */
@Service("sysCompanyService")
public class SysCompanyServiceImpl implements ISysCompanyService {

    private SysCompanyMapper sysCompanyMapper;

    @Autowired
    public void setSysCompanyMapper(SysCompanyMapper sysCompanyMapper) {
        this.sysCompanyMapper = sysCompanyMapper;
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysCompany queryById(Long id) {
        return this.sysCompanyMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysCompany> queryAllByLimit(int offset, int limit) {
        return this.sysCompanyMapper.queryAllByLimit(offset, limit);
    }

    @Override
    public List<SysCompany> loadAllCompanies() {
        return sysCompanyMapper.loadAllCompanies();
    }

    /**
     * 新增数据
     *
     * @param sysCompany 实例对象
     * @return 实例对象
     */
    @Override
    public SysCompany insert(SysCompany sysCompany) {
        this.sysCompanyMapper.insert(sysCompany);
        return sysCompany;
    }

    /**
     * 修改数据
     *
     * @param sysCompany 实例对象
     * @return 实例对象
     */
    @Override
    public SysCompany update(SysCompany sysCompany) {
        this.sysCompanyMapper.update(sysCompany);
        return this.queryById(sysCompany.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.sysCompanyMapper.deleteById(id) > 0;
    }
}