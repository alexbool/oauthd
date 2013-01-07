package com.alexbool.oauth.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.google.common.base.Optional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {

    private final UUID uid;
    private final Optional<String> login;
    private final String password;
    private final boolean deleted;
    private final Collection<? extends GrantedAuthority> authorities;

    public User(UUID uid, Optional<String> login, String password, boolean deleted,
            Collection<? extends GrantedAuthority> authorities) {
        this.uid = uid;
        this.login = login;
        this.password = password;
        this.deleted = deleted;
        this.authorities = authorities;
    }

    public User(UUID uid, Optional<String> login, String password, boolean deleted, List<String> authorities) {
        this.uid = uid;
        this.login = login;
        this.password = password;
        this.deleted = deleted;
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }
        this.authorities = grantedAuthorities;
    }

    public UUID getUid() {
        return uid;
    }

    public Optional<String> getLogin() {
        return login;
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
        return uid.toString();
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
