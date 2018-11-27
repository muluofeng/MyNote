package com.example.xing.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

/**
 * @author xiexingxing
 * @Created by 2018-09-11 下午11:51.
 */
@Setter
@Getter
public class JwtUser implements UserDetails {
    private final String                                 username;
    private final String                                 password;
    private final Collection<? extends GrantedAuthority> authorities;
    // 最后更新密码的时间
    private final Date                                   lastPasswordResetDate;
    private       Boolean                                lock;

    public JwtUser(
            String username,
            String password,
            boolean lock,
            Collection<? extends GrantedAuthority> authorities,
            Date lastPasswordResetDate
    ) {
        this.username = username;
        this.password = password;
        this.lock = lock;
        this.authorities = authorities;
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    //返回分配给用户的角色列表
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // 账户是否未过期
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 账户是否未锁定
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 密码是否未过期
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return this.lock == false;
    }

    @JsonIgnore
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

}