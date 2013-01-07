package com.alexbool.oauth.user;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class InMemoryUserRepository implements UserRepository {

    private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<String, User>();

    public InMemoryUserRepository() {
    }

    public InMemoryUserRepository(Collection<? extends User> users) {
        for (User user : users) {
            this.users.put(user.getUsername(), user);
        }
    }

    @Override
    public boolean exists(String username) {
        return users.contains(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findOrThrow(username);
    }

    @Override
    public void save(User user) {
        if (users.containsKey(user.getUsername())) {
            throw new UsernameAlreadyExistsException(user.getUsername());
        }
        users.put(user.getUsername(), user);
    }

    @Override
    public void updatePassword(String username, String password) {
        User user = findOrThrow(username);
        User newUser = new User(user.getUid(), username, password, !user.isEnabled(), user.getAuthorities());
        users.put(username, newUser);
    }

    @Override
    public void saveAuthorities(String username, Collection<? extends GrantedAuthority> authorities) {
        User user = findOrThrow(username);
        User newUser = new User(user.getUid(), username, user.getPassword(), !user.isEnabled(), authorities);
        users.put(username, newUser);
    }

    @Override
    public void delete(String username) {
        users.remove(username);
    }

    private User findOrThrow(String username) {
        User user = users.get(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }
        return user;
    }
}
