package com.keanntech.provider.api.org;

import com.keanntech.common.base.config.FeignClientsConfig;
import com.keanntech.common.base.constants.GlobalsConstants;
import com.keanntech.common.base.reponse.ResponseData;
import com.keanntech.common.model.po.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        contextId = "sysUser",
        name = GlobalsConstants.KEANNTECH_SERVICENAME_PROVIDER,
        url = GlobalsConstants.KEANNTECH_FEIGN_URL_PROVIDER,
        path = "/api/sysUser"
)
public interface SysUserApi {

    @PostMapping(
            value = "/loadUser",
            produces = MediaType.APPLICATION_JSON_VALUE + GlobalsConstants.CHARSET,
            consumes = MediaType.APPLICATION_JSON_VALUE + GlobalsConstants.CHARSET)
    ResponseData<SysUser> loadUser(@RequestParam("userName") String userName);

}
