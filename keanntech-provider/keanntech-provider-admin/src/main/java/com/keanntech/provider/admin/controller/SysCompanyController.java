package com.keanntech.provider.admin.controller;

import com.keanntech.common.base.reponse.ResponseData;
import com.keanntech.common.base.reponse.ResponseDataUtil;
import com.keanntech.common.model.po.SysCompany;
import com.keanntech.provider.admin.service.ISysCompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 公司表，为了实现对不同公司提供服务，提供出一个公司信息表(SysCompany)表控制层
 *
 * @author eddey.miao
 * @since 2020-03-09 21:39:53
 */
@RestController
@RequestMapping("/api/admin/sysCompany")
@Api(value = "公司管理")
public class SysCompanyController {
    /**
     * 服务对象
     */
    private ISysCompanyService sysCompanyService;

    @Autowired
    public void setSysCompanyService(ISysCompanyService sysCompanyService) {
        this.sysCompanyService = sysCompanyService;
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/selectOne")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "公司ID", required = true, dataType = "Long")
    })
    public ResponseData<SysCompany> selectOne(Long id) {
        SysCompany sysCompany = this.sysCompanyService.queryById(id);
        return ResponseDataUtil.buildSuccess(sysCompany);
    }

    @GetMapping("/loadAllCompanies")
    public ResponseData<SysCompany> loadAllCompanies(){
        List<SysCompany> companyList = sysCompanyService.loadAllCompanies();
        return ResponseDataUtil.buildSuccess(companyList);
    }

}