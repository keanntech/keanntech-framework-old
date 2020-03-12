package com.keanntech.common.model.po;

import com.keanntech.common.model.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SysUser extends BasePO {

  private Long createId;
  private Long updateId;
  private Timestamp createDate;
  private Timestamp updateDate;
  private Timestamp lastLoginDate;
  private String remark;
  private Boolean deleted;
  private String userName;
  private String password;
  private String jobNumber;
  private String name;
  private String email;
  private String phone;
  private String mobile;
  private String photo;
  private Boolean superAdmin;
  private Boolean admin;
  private Boolean enabled;
  private Long sysCompanyId;
  private SysCompany sysCompany;
  private Boolean accountNonExpired;
  private Boolean credentialsNonExpired;
  private Boolean accountNonLocked;
  private List<SysRole> userRoles;
}
