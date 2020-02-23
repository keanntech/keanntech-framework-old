package com.keanntech.common.model.po;

import com.keanntech.common.model.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * 系统组织架构表sys_department 如，xx公司--xx部门(SysDepartment)实体类
 *
 * @author miaoqingfu
 * @since 2020-02-13 20:26:34
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SysDepartment extends BasePO {

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
    * 组织名称
    */
    private String deptName;
    /**
    * 编码
    */
    private String code;
    /**
    * 图标
    */
    private String icon;
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