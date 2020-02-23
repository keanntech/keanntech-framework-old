package com.keanntech.authorization;

import com.keanntech.authorization.annotation.EnableAuthJWTTokenStore;
import com.keanntech.common.base.config.HttpMessageConvertersConfig;
import com.keanntech.common.base.config.RestTemplateConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication(
        scanBasePackages = {"com.keanntech.common.base","com.keanntech.authorization"})
@EnableAuthJWTTokenStore
@EnableFeignClients(basePackages = {"com.keanntech.provider.api"})
@Import({HttpMessageConvertersConfig.class, RestTemplateConfig.class})
public class AuthorizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationApplication.class, args);
    }

}
