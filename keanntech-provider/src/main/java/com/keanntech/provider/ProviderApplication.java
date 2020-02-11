package com.keanntech.provider;

import com.keanntech.provider.annotation.EnableResJWTTokenStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "com.keanntech.common","com.keanntech.provider"
        }
)
@EnableResJWTTokenStore
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }

}
