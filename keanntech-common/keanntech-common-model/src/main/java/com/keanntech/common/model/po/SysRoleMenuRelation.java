package com.keanntech.common.model.po;

import java.io.Serializable;

/**
 * 角色资源表sys_role_menu_relation   资源表和角色表的中间表(SysRoleMenuRelation)实体类
 *
 * @author miaoqingfu
 * @since 2020-02-28 19:46:16
 */
public class SysRoleMenuRelation implements Serializable {
    private static final long serialVersionUID = -86885654462828937L;
    /**
    * 系统资源id
    */
    private Long sysMenuId;
    /**
    * 系统角色id
    */
    private Long sysRoleId;


    public Long getSysMenuId() {
        return sysMenuId;
    }

    public void setSysMenuId(Long sysMenuId) {
        this.sysMenuId = sysMenuId;
    }

    public Long getSysRoleId() {
        return sysRoleId;
    }

    public void setSysRoleId(Long sysRoleId) {
        this.sysRoleId = sysRoleId;
    }

}