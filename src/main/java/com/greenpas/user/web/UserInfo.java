package com.greenpas.user.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;

public class UserInfo {

    private final String username;
    private final List<String> authorities;

    public UserInfo(String username, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        List<String> authorityNames = new ArrayList<String>();
        for (GrantedAuthority authority : authorities) {
            authorityNames.add(authority.getAuthority());
        }
        this.authorities = Collections.unmodifiableList(authorityNames);
    }

    public String getUsername() {
        return username;
    }

    public List<String> getAuthorities() {
        return authorities;
    }
}
