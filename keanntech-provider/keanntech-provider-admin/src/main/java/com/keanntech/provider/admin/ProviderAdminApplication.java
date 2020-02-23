package com.keanntech.provider.admin;

import com.keanntech.common.base.annotation.EnableResJWTTokenStore;
import com.keanntech.common.base.config.HttpMessageConvertersConfig;
import com.keanntech.common.base.config.RestTemplateConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = {"com.keanntech.common.base","com.keanntech.provider.admin"})
@EnableFeignClients(basePackages = {"com.keanntech.provider.api"})
@MapperScan(basePackages = {"com.keanntech.provider.admin.mapper"})
@Import({HttpMessageConvertersConfig.class, RestTemplateConfig.class})
@EnableResJWTTokenStore
public class ProviderAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderAdminApplication.class, args);
    }

}
