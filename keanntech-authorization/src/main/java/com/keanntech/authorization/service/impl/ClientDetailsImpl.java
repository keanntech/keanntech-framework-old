package com.keanntech.authorization.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.keanntech.common.model.auth.OauthClient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.StringUtils;

import java.util.*;

public class ClientDetailsImpl implements ClientDetails {

    private OauthClient oauthClient;

    public ClientDetailsImpl(OauthClient oauthClient){
        this.oauthClient = oauthClient;
    }

    @Override
    public String getClientId() {
        return this.oauthClient.getClientId();
    }

    @Override
    public Set<String> getResourceIds() {
        Set scopeList;
        String resourceIds = oauthClient.getResourceIds();
        if(StringUtils.hasText(resourceIds)){
            scopeList = StringUtils.commaDelimitedListToSet(resourceIds);
            if(!scopeList.isEmpty()){
                return scopeList;
            }
        }
        return Collections.emptySet();
    }

    @Override
    public boolean isSecretRequired() {
        return this.oauthClient.getIsSecretreQuired();
    }

    @Override
    public String getClientSecret() {
        return this.oauthClient.getClientSecret();
    }

    @Override
    public boolean isScoped() {
        return this.oauthClient.getIsScoped();
    }

    @Override
    public Set<String> getScope() {
        Set scopeList;
        String scope = this.oauthClient.getScope();
        if(StringUtils.hasText(scope)){
            scopeList = StringUtils.commaDelimitedListToSet(scope);
            if(!scopeList.isEmpty()){
                return scopeList;
            }
        }
        return Collections.emptySet();
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        String types = this.oauthClient.getAuthorizedGrantTypes();
        if(StringUtils.hasText(types)){
            return StringUtils.commaDelimitedListToSet(types);
        }else{
            return new HashSet(Arrays.asList("authorization_code", "refresh_token"));
        }
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return StringUtils.commaDelimitedListToSet(this.oauthClient.getRegisteredRedirectUris());
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(this.oauthClient.getAuthorities());
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return this.oauthClient.getAccessTokenValiditySeconds();
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return this.oauthClient.getRefreshTokenValiditySeconds();
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return  this.oauthClient.getIsAutoApprove() == null ? false: this
                .oauthClient.getIsAutoApprove();
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        JSONObject jsonObject = JSONObject.parseObject(this.oauthClient.getAdditionalInformation());
        Map<String, Object> map = jsonObject;
        return map;
    }
}
