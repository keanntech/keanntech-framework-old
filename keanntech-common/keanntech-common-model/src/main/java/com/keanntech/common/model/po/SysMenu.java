package com.keanntech.common.model.po;

import com.keanntech.common.model.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统菜单sys_menu, 资源树，按钮(SysMenu)实体类
 *
 * @author makejava
 * @since 2020-02-13 20:02:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SysMenu extends BasePO {

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
    /**
    * 备注信息
    */
    private String remark;
    /**
    * 删除标记0：正常；1：删除；
    */
    private Boolean deleted;
    /**
    * 父id
    */
    private Long parentId;
    /**
    * 资源名称
    */
    private String menuName;
    /**
    * 菜单类型，按钮或者menu
    */
    private String type;
    /**
    * 链接地址
    */
    private String href;
    /**
    * 打开方式
    */
    private String target;
    /**
    * 图标
    */
    private String icon;
    /**
    * 是否展示，0：不展示，1：展示
    */
    private Boolean show;
    /**
    * 权限标识
    */
    private String permission;
    /**
    * 路径
    */
    private String path;
    /**
    * 排序
    */
    private Integer sort;
    /**
    * 所在公司id
    */
    private Long sysCompanyId;

}