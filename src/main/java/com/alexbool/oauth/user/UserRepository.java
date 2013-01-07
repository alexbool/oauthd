package com.alexbool.oauth.user;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserRepository extends UserDetailsService {

    boolean exists(String username);
    void save(User user);
    void updatePassword(String username, String password);
    void saveAuthorities(String username, Collection<? extends GrantedAuthority> authorities);
    void delete(String username);
}
