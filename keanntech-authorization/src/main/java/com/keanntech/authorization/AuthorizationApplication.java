package com.keanntech.authorization;

import com.keanntech.authorization.annotation.EnableAuthJWTTokenStore;
import com.keanntech.common.base.config.WebMvcConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(
        scanBasePackages = {"com.keanntech.common.base","com.keanntech.authorization"}
        )

@EnableAuthJWTTokenStore
@MapperScan(basePackages = {"com.keanntech.authorization.mapper"})
@EnableSwagger2
public class AuthorizationApplication extends WebMvcConfig {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationApplication.class, args);
    }

}
