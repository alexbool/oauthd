package com.alexbool.oauth.user.web;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;

/**
 * DTO class used as token validation endpoint response.
 *
 * @author Alexander Bulaev
 */
public class UserInfo {

    private final UUID uid;
    private final String username;
    private final List<String> authorities;

    public UserInfo(UUID uid, String username, Collection<? extends GrantedAuthority> authorities) {
        this.uid = uid;
        this.username = username;
        List<String> authorityNames = new ArrayList<String>();
        for (GrantedAuthority authority : authorities) {
            authorityNames.add(authority.getAuthority());
        }
        this.authorities = Collections.unmodifiableList(authorityNames);
    }

    public UUID getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getAuthorities() {
        return authorities;
    }
}
