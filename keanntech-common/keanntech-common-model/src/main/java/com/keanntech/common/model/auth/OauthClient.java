package com.keanntech.common.model.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OauthClient implements Serializable {

    private static final long serialVersionUID = -5929714592823290134L;

    /**
     * 客户端ID，唯一性标识，不可重复
     */
    private String clientId;

    /**
     * 此客户端可以访问的资源。如果为空，则可被调用方忽略
     */
    private String resourceIds;

    /**
     * 客户端密码（是否有效，与is_secretre_quired有关）
     */
    private String clientSecret;

    /**
     * 此客户端的范围。如果客户端没有作用域，则为空。
     */
    private String scope;

    /**
     * 为此客户端授权的授予类型。
     */
    private String authorizedGrantTypes;

    /**
     * 此客户端在“授权代码”访问授予期间使用的预定义重定向URI。
     */
    private String registeredRedirectUris;

    /**
     * 返回授予OAuth客户端的权限。请注意，这些权限不是使用授权访问令牌授予用户的权限。相反，这些权限是客户本身固有的。
     */
    private String authorities;

    /**
     * 此客户端的访问令牌有效期，单位：秒；
     */
    private Integer accessTokenValiditySeconds;

    /**
     * 此客户端的刷新令牌有效期，单位：秒；
     */
    private Integer refreshTokenValiditySeconds;

    /**
     * 客户端是否需要用户批准特定范围。（1：true；其他：false）
     */
    private Boolean isAutoApprove;

    /**
     * 预留字段
     */
    private String additionalInformation;

    private Boolean isScoped;

    private Boolean isSecretreQuired;

}
