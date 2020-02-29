package com.keanntech.provider.api.admin;

import com.keanntech.common.base.constants.GlobalsConstants;
import com.keanntech.common.model.po.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        contextId = "sysUser",
        name = "sysUserApi",
        url = "${keanntech.feignUrl.provider.admin}",
        path = "/api/admin/sysUser"
)
public interface SysUserApi {

    @PostMapping(
            value = "/loadUser",
            produces = MediaType.APPLICATION_JSON_VALUE + GlobalsConstants.CHARSET,
            consumes = MediaType.APPLICATION_JSON_VALUE + GlobalsConstants.CHARSET)
    SysUser loadUser(@RequestParam("userName") String userName);

    @PostMapping(
            value = "/loaCurrentUser"
    )
    SysUser loadCurrentUser();

}
