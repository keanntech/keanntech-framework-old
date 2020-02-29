package com.keanntech.gateway;

import com.keanntech.gateway.config.HttpMessageConvertersConfig;
import com.keanntech.gateway.config.RestTemplateConfig;
import io.netty.handler.codec.http.cors.CorsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication(
        exclude = {RedisAutoConfiguration.class, RedisRepositoriesAutoConfiguration.class}
)
@EnableFeignClients(basePackages = {"com.keanntech.provider.api"})
@Import({HttpMessageConvertersConfig.class, RestTemplateConfig.class})
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
