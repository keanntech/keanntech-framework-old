package com.keanntech.authorization.service;

import com.google.common.collect.Sets;
import com.keanntech.common.model.po.SysRole;
import com.keanntech.common.model.po.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class OauthUserDetails extends SysUser implements UserDetails {

    private SysUser user;
    private org.springframework.security.core.userdetails.User userDetails;

    public OauthUserDetails(SysUser user){
        this.user = user;
        this.userDetails = new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                user.getEnabled(),
                user.getAccountNonExpired(),
                user.getCredentialsNonExpired(),
                user.getAccountNonLocked(),
                this.obtainGrantedAuthorities(user)
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.userDetails.getAuthorities();
    }

    @Override
    public String getUsername() {
        return this.user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }

    protected Set<GrantedAuthority> obtainGrantedAuthorities(SysUser user) {
        if(Objects.isNull(user) || CollectionUtils.isEmpty(user.getUserRoles())){
            return Sets.newHashSet();
        }
        Set<SysRole> roles = new HashSet<SysRole>(user.getUserRoles());
        return roles.stream().map(role -> new SimpleGrantedAuthority(String.valueOf(role.getId()))).collect(Collectors.toSet());
    }
}
