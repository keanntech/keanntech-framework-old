package com.keanntech.common.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 系统用户和角色的中间表sys_user_role_relation(SysUserRoleRelation)实体类
 *
 * @author eddey.miao
 * @since 2020-03-03 22:05:28
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SysUserRoleRelation implements Serializable {
    private static final long serialVersionUID = 160649078199270724L;
    /**
    * 系统用户id
    */
    private Long sysUserId;
    /**
    * 系统角色id
    */
    private Long sysRoleId;

}