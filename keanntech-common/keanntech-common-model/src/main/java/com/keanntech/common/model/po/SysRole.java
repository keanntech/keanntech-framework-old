package com.keanntech.common.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SysRole {

  private long id;
  private long createId;
  private long updateId;
  private java.sql.Timestamp createDate;
  private java.sql.Timestamp updateDate;
  private String remark;
  private String deleted;
  private String roleName;
  private String code;
  private String enabled;
  private long sysCompanyId;
  private String dataScope;

}
