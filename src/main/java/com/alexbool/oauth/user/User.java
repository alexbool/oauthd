package com.alexbool.oauth.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {
    
    private static final long serialVersionUID = -3066380991869635502L;

    private final String username;
    private final String password;
    private final boolean deleted;
    private final Collection<? extends GrantedAuthority> authorities;

    public User(String username, String password, boolean deleted, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.deleted = deleted;
        this.authorities = authorities;
    }

    public User(String username, String password, boolean deleted, List<String> authorities) {
        this.username = username;
        this.password = password;
        this.deleted = deleted;
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        for (String authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }
        this.authorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !deleted;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !deleted;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !deleted;
    }

    @Override
    public boolean isEnabled() {
        return !deleted;
    }
}
