package com.keanntech.common.model.po;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SysUser {

  private long id;
  private long createId;
  private long updateId;
  private java.sql.Timestamp createDate;
  private java.sql.Timestamp updateDate;
  private String remark;
  private boolean deleted;
  private String userName;
  private String password;
  private String jobNumber;
  private String name;
  private String email;
  private String phone;
  private String mobile;
  private String photo;
  private Boolean enabled;
  private long sysCompanyId;
  private Boolean accountNonExpired;
  private Boolean credentialsNonExpired;
  private Boolean accountNonLocked;
  private List<SysRole> userRoles;

}
