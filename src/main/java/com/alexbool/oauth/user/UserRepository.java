package com.alexbool.oauth.user;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Contains methods for operating users data store.
 * Note that most methods in this repository operate with user's login (not uid).
 *
 * @author Alexander Bulaev
 */
public interface UserRepository extends UserDetailsService {

    boolean exists(String login);
    void save(User user);
    void updatePassword(String login, String password);
    void saveAuthorities(String login, Collection<? extends GrantedAuthority> authorities);
    void delete(String login);
}
