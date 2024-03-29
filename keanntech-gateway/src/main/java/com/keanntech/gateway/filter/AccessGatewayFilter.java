package com.keanntech.gateway.filter;

import com.keanntech.gateway.properties.FilterIgnoreProperties;
import com.keanntech.provider.api.auth.OauthApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Stream;

@Configuration
@Slf4j
public class AccessGatewayFilter implements GlobalFilter, Ordered {

    private FilterIgnoreProperties ignorePropertiesConfig;

    private OauthApi oauthApi;

    @Autowired
    public void setIgnorePropertiesConfig(FilterIgnoreProperties ignorePropertiesConfig) {
        this.ignorePropertiesConfig = ignorePropertiesConfig;
    }

    @Autowired
    public void setOauthApi(OauthApi oauthApi) {
        this.oauthApi = oauthApi;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        //获取URL进行验证
        String url = request.getPath().value();
        //如果是不需要验证的URL则放行
        if(ignoreAuthentication(url)){
            return chain.filter(exchange);
        }

        //获取TOKEN、USERNAME、METHOD进行验证
        String authentication = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String method = request.getMethodValue();

        //如果TOKEN为空返回401
        if(StringUtils.isEmpty(authentication)){
            return unauthorized(exchange);
        }

        //验证TOKEN是否合法；验证是否有URL、METHOD权限
        boolean isHasPermission = oauthApi.hasPermission(authentication, url, method);
        if(isHasPermission){
            ServerHttpRequest.Builder builder = request.mutate();
            builder.header("Authorization", authentication);
            return chain.filter(exchange.mutate().request(builder.build()).build());
        }

        return unauthorized(exchange);
    }

    private boolean ignoreAuthentication(String url) {
        List<String> authUrls = ignorePropertiesConfig.getAuthUrls();
        String[] arrIgnoreUrl = authUrls.toArray(new String[authUrls.size()]);
        return Stream.of(arrIgnoreUrl).anyMatch(ignoreUrl -> url.startsWith(StringUtils.trim(ignoreUrl)));
    }

    /**
     * 网关拒绝，返回401
     *
     * @param
     */
    private Mono<Void> unauthorized(ServerWebExchange serverWebExchange) {
        serverWebExchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        DataBuffer buffer = serverWebExchange.getResponse()
                .bufferFactory().wrap(HttpStatus.UNAUTHORIZED.getReasonPhrase().getBytes());
        return serverWebExchange.getResponse().writeWith(Flux.just(buffer));
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
