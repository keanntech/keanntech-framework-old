package com.keanntech.provider.admin.controller;

import com.keanntech.common.base.annotation.CurrentUser;
import com.keanntech.common.base.exception.ActionException;
import com.keanntech.common.base.reponse.Result;
import com.keanntech.common.model.methodresolver.CurrentUserResolver;
import com.keanntech.common.model.po.SysCompany;
import com.keanntech.provider.admin.service.ISysCompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

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

    private ISysCompanyService sysCompanyService;

    @Autowired
    public void setSysCompanyService(ISysCompanyService sysCompanyService) {
        this.sysCompanyService = sysCompanyService;
    }

    /**
     * 加载所有公司信息
     * @return
     */
    @GetMapping("/loadAllCompanies")
    @ApiOperation(value = "加载所有公司")
    public Result<SysCompany> loadAllCompanies(){
        List<SysCompany> companyList = sysCompanyService.loadAllCompanies();
        return Result.ok().data("data", companyList).message("");
    }

    /**
     * 保存公司信息
     * @param sysCompany
     * @param currentUser 当前登录用户
     * @return
     */
    @PostMapping("/saveCompany")
    @ApiImplicitParam(name = "sysCompany", value = "公司信息对象", required = true, paramType = "body", dataType = "SysCompany")
    public Result<SysCompany> saveCompany(
            @NotNull(message = "公司信息不能为空！") @RequestBody SysCompany sysCompany, @CurrentUser CurrentUserResolver currentUser){

        sysCompany.setCreateId(currentUser.getId());
        sysCompany.setUpdateId(currentUser.getId());

        try {
            sysCompanyService.saveCompany(sysCompany);
            return Result.ok().data("data",sysCompany).message("");
        } catch (ActionException e) {
            throw new ActionException("保存失败");
        }
    }

    @PostMapping("/updateCompany")
    public Result<SysCompany> updateCompany(
            @NotNull(message = "公司信息不能为空！") @RequestBody SysCompany sysCompany, @CurrentUser CurrentUserResolver currentUser){

        sysCompany.setUpdateId(currentUser.getId());
        try {
            sysCompanyService.update(sysCompany);
            return Result.ok().message("").data("data", sysCompany);
        } catch (ActionException e) {
            throw new ActionException("更新失败");
        }

    }

}