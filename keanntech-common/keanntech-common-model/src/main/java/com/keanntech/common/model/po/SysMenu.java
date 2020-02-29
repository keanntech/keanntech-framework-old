package com.keanntech.common.model.po;

import com.keanntech.common.model.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * 系统菜单sys_menu, 资源树，按钮(SysMenu)实体类
 *
 * @author eddey.miao
 * @since 2020-02-29 23:25:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SysMenu extends BasePO {

    /**
     * 父id
     */
    private Long parentId;
    /**
     * 资源名称
     */
    private String menuName;
    /**
     * 菜单标题
     */
    private String menuTitle;
    /**
     * 路径
     */
    private String path;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * 删除标记0：正常；1：删除；
     */
    private Boolean deleted;
    /**
     * 跳转地址
     */
    private String redirect;
    /**
     * 组件名称
     */
    private String component;
    /**
     * 图标
     */
    private String icon;
    /**
     * 是否展示，0：不展示，1：展示
     */
    private Boolean show;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 所在公司id
     */
    private Long sysCompanyId;
    /**
     * 创建者id
     */
    private Long createId;
    /**
     * 修改者id
     */
    private Long updateId;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 修改时间
     */
    private Date updateDate;

}