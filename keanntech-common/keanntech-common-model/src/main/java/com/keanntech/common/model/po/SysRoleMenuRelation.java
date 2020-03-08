package com.keanntech.common.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色资源表sys_role_menu_relation   资源表和角色表的中间表(SysRoleMenuRelation)实体类
 *
 * @author eddey.miao
 * @since 2020-02-28 19:46:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
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

}