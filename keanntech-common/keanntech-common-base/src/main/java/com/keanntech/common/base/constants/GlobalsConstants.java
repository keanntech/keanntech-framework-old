package com.keanntech.common.base.constants;

import org.springframework.beans.factory.annotation.Value;

public final class GlobalsConstants {

    /**
     * ServiceName
     */
    public static final String KEANNTECH_SERVICENAME_PROVIDER = "keanntech-provider";
    public static final String KEANNTECH_SERVICENAME_AUTH = "keanntech-auth-authorization";

    @Value("${keanntech.feignurl.provider}")
    public static final String KEANNTECH_FEIGN_URL_PROVIDER = "127.0.0.1:9300";

    @Value("${keanntech.feignurl.auth}")
    public static final String KEANNTECH_FEIGN_URL_AUTH = "127.0.0.1:9100";

    /**
     * 缓存
     */
    public static final String CACHE_NAMES_USER = "cache-sys-user";
    public static final String CACHE_KEYS_USER = "sys-user";
    public static final String CACHE_NAMES_RESOURCE = "cache-sys-resource";
    public static final String CACHE_KEYS_RESOURCE = "sys-resource";

    /**
     * 路径
     */
    public static final String FEIGN_CONTROLLER_PATH_USER = "/api/user";

    public static final String CHARSET = ";charset=UTF-8";

    /**
     * druid配置
     */
    public static final String DB_PREFIX = "spring.datasource";

    /**
     * security  过滤url 配置
     */
    public static final String FILTER_IGNORE = "ignore";

    /**
     * 信息提示
     */
    public static final String INFO_USERAME_NOT_NULL = "用户名不能为空!";

}
