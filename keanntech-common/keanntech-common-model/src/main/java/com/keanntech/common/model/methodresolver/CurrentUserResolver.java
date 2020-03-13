package com.keanntech.common.model.methodresolver;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CurrentUserResolver {

    private Long id;
    private String userName;
    private String name;
    private List<Long> roleIds;
    private String jobNumber;
    private Long sysCompanyId;

}
