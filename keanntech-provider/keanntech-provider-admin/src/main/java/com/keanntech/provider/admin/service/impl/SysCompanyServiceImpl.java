package com.keanntech.provider.admin.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baidu.fsg.uid.impl.CachedUidGenerator;
import com.keanntech.common.base.exception.ActionException;
import com.keanntech.common.model.po.SysCompany;
import com.keanntech.provider.admin.mapper.SysCompanyMapper;
import com.keanntech.provider.admin.service.ISysCompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
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
@Slf4j
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
    @Transactional(rollbackFor = {ActionException.class})
    public int saveCompany(SysCompany sysCompany) {
        try{
            LocalDateTime localDateTime = LocalDateTime.parse(DateUtil.now(),formatter);
            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            sysCompany.setCreateDate(timestamp);
            sysCompany.setUpdateDate(timestamp);
            sysCompany.setId(cachedUidGenerator.getUID());
            return sysCompanyMapper.saveCompany(sysCompany);
        }catch (DataAccessException e){
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * 修改数据
     *
     * @param sysCompany 实例对象
     * @return 实例对象
     */
    @Override
    @Transactional(rollbackFor = ActionException.class)
    public SysCompany update(SysCompany sysCompany) {
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(DateUtil.now(),formatter);
            Timestamp timestamp = Timestamp.valueOf(localDateTime);
            sysCompany.setUpdateDate(timestamp);
            this.sysCompanyMapper.update(sysCompany);
            return sysCompany;
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}