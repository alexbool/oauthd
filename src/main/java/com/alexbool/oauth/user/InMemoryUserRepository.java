package com.alexbool.oauth.user;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.base.Optional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class InMemoryUserRepository implements UserRepository {

    private ConcurrentHashMap<String, User> users = new ConcurrentHashMap<String, User>();

    public InMemoryUserRepository() {
    }

    public InMemoryUserRepository(Collection<? extends User> users) {
        for (User user : users) {
            this.users.put(user.getLogin().get(), user);
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
            throw new LoginAlreadyExistsException(user.getUsername());
        }
        users.put(user.getUsername(), user);
    }

    @Override
    public void updatePassword(String login, String password) {
        User user = findOrThrow(login);
        User newUser = new User(user.getUid(), Optional.of(login), password, !user.isEnabled(), user.getAuthorities());
        users.put(login, newUser);
    }

    @Override
    public void saveAuthorities(String login, Collection<? extends GrantedAuthority> authorities) {
        User user = findOrThrow(login);
        User newUser = new User(user.getUid(), Optional.of(login), user.getPassword(), !user.isEnabled(), authorities);
        users.put(login, newUser);
    }

    @Override
    public void delete(String login) {
        users.remove(login);
    }

    private User findOrThrow(String login) {
        User user = users.get(login);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", login));
        }
        return user;
    }
}
