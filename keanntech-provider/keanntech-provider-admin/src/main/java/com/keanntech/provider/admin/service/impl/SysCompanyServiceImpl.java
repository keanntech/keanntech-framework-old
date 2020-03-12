package com.keanntech.provider.admin.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baidu.fsg.uid.impl.CachedUidGenerator;
import com.keanntech.common.base.exception.ActionException;
import com.keanntech.common.model.po.SysCompany;
import com.keanntech.provider.admin.mapper.SysCompanyMapper;
import com.keanntech.provider.admin.service.ISysCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 公司表，为了实现对不同公司提供服务，提供出一个公司信息表(SysCompany)表服务实现类
 *
 * @author eddey.miao
 * @since 2020-03-09 21:39:53
 */
@Service("sysCompanyService")
public class SysCompanyServiceImpl implements ISysCompanyService {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private SysCompanyMapper sysCompanyMapper;
    private CachedUidGenerator cachedUidGenerator;

    @Autowired
    public void setSysCompanyMapper(SysCompanyMapper sysCompanyMapper) {
        this.sysCompanyMapper = sysCompanyMapper;
    }

    @Autowired
    @Qualifier("cacheUidGenerator")
    public void setCachedUidGenerator(CachedUidGenerator cachedUidGenerator) {
        this.cachedUidGenerator = cachedUidGenerator;
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
     * 加载所有公司
     * @return
     */
    @Override
    public List<SysCompany> loadAllCompanies() {
        return sysCompanyMapper.loadAllCompanies();
    }

    /**
     * 保存公司信息
     * @param sysCompany
     * @return
     */
    @Override
    @Transactional(rollbackFor = {ActionException.class}, isolation = Isolation.READ_COMMITTED)
    public int saveCompany(SysCompany sysCompany) {
        try{
            LocalDateTime localDateTime = LocalDateTime.parse(DateUtil.now(),formatter);
            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            sysCompany.setCreateDate(timestamp);
            sysCompany.setUpdateDate(timestamp);
            sysCompany.setId(cachedUidGenerator.getUID());
            return sysCompanyMapper.saveCompany(sysCompany);
        }catch (ActionException e){
            throw new ActionException();
        }
    }

    /**
     * 修改数据
     *
     * @param sysCompany 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = ActionException.class, isolation = Isolation.READ_COMMITTED)
    public SysCompany update(SysCompany sysCompany) {
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(DateUtil.now(),formatter);
            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            sysCompany.setUpdateDate(timestamp);
            this.sysCompanyMapper.update(sysCompany);
            return this.queryById(sysCompany.getId());
        } catch (Exception e) {
            throw new ActionException();
        }
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