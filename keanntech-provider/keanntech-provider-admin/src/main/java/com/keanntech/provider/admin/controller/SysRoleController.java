package com.keanntech.provider.admin.controller;

import com.keanntech.common.base.reponse.ResponseData;
import com.keanntech.common.base.reponse.ResponseDataUtil;
import com.keanntech.common.model.po.SysRole;
import com.keanntech.provider.admin.service.ISysRoleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 系统角色表sys_role(SysRole)表控制层
 *
 * @author eddey.miao
 * @since 2020-03-03 16:21:12
 */
@RestController
@RequestMapping("/api/admin/sysRole")
public class SysRoleController {
    /**
     * 服务对象
     */
    private ISysRoleService sysRoleService;

    @Autowired
    public void setSysRoleService(ISysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/selectOne")
    @ApiOperation(value = "通过ID获取角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "角色ID", required = true)
    })
    public ResponseData<SysRole> selectOne(@RequestParam("id") Long id) {
        return ResponseDataUtil.buildSuccess(this.sysRoleService.queryById(id));
    }

    @PostMapping("/loadAllRoles")
    @ApiOperation(value = "获取所有角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysRole", value = "角色对象", required = false)
    })
    public ResponseData<SysRole> loadAllRoles(@RequestBody SysRole sysRole){
        return ResponseDataUtil.buildSuccess(this.sysRoleService.queryAll(sysRole));
    }

}