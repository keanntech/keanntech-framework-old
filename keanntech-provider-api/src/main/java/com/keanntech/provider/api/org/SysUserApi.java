package com.keanntech.provider.api.org;

import com.keanntech.common.base.config.FeignClientsConfig;
import com.keanntech.common.base.constants.GlobalsConstants;
import com.keanntech.common.base.reponse.ResponseData;
import com.keanntech.common.model.po.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        contextId = "sysUser",
        name = GlobalsConstants.KEANNTECH_SERVICENAME_PROVIDER,
        url = GlobalsConstants.KEANNTECH_FEIGN_URL_PROVIDER,
        path = "/api/sysUser",
        configuration = FeignClientsConfig.class
)
public interface SysUserApi {

    @GetMapping(
            value = "/loadUser/{userName}",
            produces = MediaType.APPLICATION_JSON_VALUE + GlobalsConstants.CHARSET,
            consumes = MediaType.APPLICATION_JSON_VALUE + GlobalsConstants.CHARSET)
    ResponseData<SysUser> loadUser(@PathVariable("userName") String userName);

}
