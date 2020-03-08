package com.keanntech.common.model.methodresolverparam;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CurrentUserResolverParam {

    private Long id;
    private String userName;
    private String name;
    private List<Long> roleIds;
    private String jobNumber;

}
