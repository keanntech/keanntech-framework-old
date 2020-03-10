package com.keanntech.common.model.po;

import com.keanntech.common.model.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 公司表，为了实现对不同公司提供服务，提供出一个公司信息表(SysCompany)实体类
 *
 * @author eddey.miao
 * @since 2020-03-09 21:37:39
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SysCompany extends BasePO {

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
    * 删除标记0：正常；1：删除;
    */
    private Boolean deleted;
    /**
    * 公司地址
    */
    private String address;
    /**
    * 区域id
    */
    private String areaId;
    /**
    * 邮政编码
    */
    private String zipCode;
    /**
    * 法人
    */
    private String master;
    /**
    * 联系电话
    */
    private String phone;
    /**
    * 手机
    */
    private String mobile;
    /**
    * 传真
    */
    private String fax;
    /**
    * 邮箱
    */
    private String email;
    /**
    * 版权信息
    */
    private String copyright;
    /**
    * 公司名称
    */
    private String companyName;

}