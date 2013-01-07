package com.alexbool.oauth.user.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.google.common.base.Optional;
import org.springframework.security.core.GrantedAuthority;

/**
 * DTO class used as token validation endpoint response.
 *
 * @author Alexander Bulaev
 */
public class UserInfo {

    private final UUID uid;
    private final Optional<String> login;
    private final List<String> authorities;

    public UserInfo(UUID uid, Optional<String> login, Collection<? extends GrantedAuthority> authorities) {
        this.uid = uid;
        this.login = login;
        List<String> authorityNames = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            authorityNames.add(authority.getAuthority());
        }
        this.authorities = Collections.unmodifiableList(authorityNames);
    }

    public UUID getUid() {
        return uid;
    }

    public String getLogin() {
        return login.orNull();
    }

    public List<String> getAuthorities() {
        return authorities;
    }
}
