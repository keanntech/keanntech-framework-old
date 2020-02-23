package com.keanntech.common.model.po;

import com.keanntech.common.model.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SysRole extends BasePO {

  private Long createId;
  private Long updateId;
  private java.sql.Timestamp createDate;
  private java.sql.Timestamp updateDate;
  private String remark;
  private String deleted;
  private String roleName;
  private String code;
  private String enabled;
  private Long sysCompanyId;
  private String dataScope;

}
