package com.keanntech.authorization;

import com.keanntech.authorization.annotation.EnableAuthJWTTokenStore;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {"com.keanntech.common.base","com.keanntech.authorization"}
        )

@EnableAuthJWTTokenStore
@MapperScan(basePackages = {"com.keanntech.authorization.mapper"})
public class AuthorizationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationApplication.class, args);
    }

}
