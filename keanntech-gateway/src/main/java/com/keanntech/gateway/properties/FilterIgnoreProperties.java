package com.keanntech.gateway.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@ConditionalOnExpression("!'${ignore}'.isEmpty()")
@ConfigurationProperties(prefix = "ignore")
public class FilterIgnoreProperties {

    /**
     * 放行url,放行的url不再被安全框架拦截
     */
    private List<String> authUrls = new ArrayList<>();

    private String[] webUnInterceptUris = {};

}
