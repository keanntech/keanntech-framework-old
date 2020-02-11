package com.keanntech.gateway;

import com.keanntech.common.base.config.HttpMessageConvertersConfig;
import com.keanntech.common.base.config.RestTemplateConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.keanntech.provider.api"})
@Import({HttpMessageConvertersConfig.class, RestTemplateConfig.class})
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
