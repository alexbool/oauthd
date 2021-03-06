package com.alexbool.oauth.user.jdbc;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import com.alexbool.oauth.user.LoginAlreadyExistsException;
import com.google.common.base.Optional;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.jdbc.JdbcTestUtils;
import com.alexbool.oauth.test.AbstractJdbcDaoTest;
import com.alexbool.oauth.user.User;
import com.alexbool.oauth.user.UserRepository;

/**
 * @author Alexander Bulaev
 */
public class JdbcUserRepositoryTest extends AbstractJdbcDaoTest {

    private static final User USER = new User(UUID.randomUUID(), Optional.of("user"), "changeme", false,
            Arrays.asList("user"));

    @Autowired
    private UserRepository userRepository;

    @Before
    public void clear() {
        JdbcTestUtils.deleteFromTables(getJdbcTemplate(), "users");
    }

    @Test(expected = LoginAlreadyExistsException.class)
    public void saveDuplicate() {
        insert();
        insert();
    }

    @Test(expected = LoginAlreadyExistsException.class)
    public void saveDuplicateLogin() {
        insert();
        userRepository.save(new User(UUID.randomUUID(), USER.getLogin(), USER.getPassword(), !USER.isAccountNonLocked(),
                USER.getAuthorities()));
    }

    @Test
    public void save() {
        insert();
        User user = (User) userRepository.loadUserByUsername("user");
        assertEquals(USER.getUid(), user.getUid());
    }

    @Test
    public void exists() {
        assertFalse(userRepository.exists("user"));
        insert();
        assertTrue(userRepository.exists("user"));
    }

    @Test
    public void updatePassword() {
        insert();
        userRepository.updatePassword("user", "newpass");
        assertEquals("newpass", userRepository.loadUserByUsername("user").getPassword());
    }

    @Test
    public void saveAuthorities() {
        insert();
        userRepository.saveAuthorities("user",
                Arrays.asList(new SimpleGrantedAuthority("ololo"), new SimpleGrantedAuthority("trololo")));
        Collection<? extends GrantedAuthority> auth = userRepository.loadUserByUsername("user").getAuthorities();
        assertEquals(2, auth.size());
        assertTrue(auth.contains(new SimpleGrantedAuthority("ololo")));
        assertTrue(auth.contains(new SimpleGrantedAuthority("trololo")));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void delete() {
        insert();
        userRepository.delete("user");
        userRepository.loadUserByUsername("user");
    }

    private void insert() {
        userRepository.save(USER);
    }
}
