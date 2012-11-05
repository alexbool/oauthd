package com.greenpas.user.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.greenpas.user.User;
import com.greenpas.user.UserRepository;
import com.greenpas.user.UsernameAlreadyExistsException;

public class JdbcUserRepository extends JdbcDaoSupport implements UserRepository {

    private static final String USERS_TABLE_DDL =
            "CREATE TABLE users (" +
            "username VARCHAR(255) NOT NULL PRIMARY KEY, " +
            "password VARCHAR(255) NOT NULL, " +
            "authorities VARCHAR(1024) NOT NULL, " +
            "deleted TINYINT)";

    @Override
    public boolean exists(String username) {
        return getJdbcTemplate().queryForInt("SELECT COUNT(*) FROM users WHERE username = ?", username) == 1;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = getJdbcTemplate().queryForObject("SELECT * FROM users WHERE username = ?",
                new RowMapper<UserDetails>() {
                    @Override
                    public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new User(rs.getString("username"), rs.getString("password"), rs.getBoolean("deleted"),
                                Arrays.asList(rs.getString("authorities").split(",")));
                    }
                }, username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }

        return user;
    }

    @Override
    public void save(UserDetails user) {
        try {
            getJdbcTemplate().update(
                    "INSERT INTO users (username, password, authorities, deleted) VALUES (?, ?, ?, ?)",
                    user.getUsername(), user.getPassword(), joinAuthorities(user.getAuthorities()), !user.isEnabled());
        }
        catch (DuplicateKeyException ex) {
            throw new UsernameAlreadyExistsException(user.getUsername());
        }
    }

    @Override
    public void updatePassword(String username, String password) {
        getJdbcTemplate().update("UPDATE users SET password = ? WHERE username = ?", password, username);
    }

    @Override
    public void saveAuthorities(String username, Collection<? extends GrantedAuthority> authorities) {
        getJdbcTemplate().update("UPDATE users SET password = ? WHERE username = ?",
                joinAuthorities(authorities), username);
    }

    @Override
    public void delete(String username) {
        getJdbcTemplate().update("DELETE FROM users WHERE username = ?", username);
    }

    private String joinAuthorities(Collection<? extends GrantedAuthority> authorities) {
        StringBuilder builder = new StringBuilder();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        while (iter.hasNext()) {
            builder.append(iter.next().getAuthority());
            if (!iter.hasNext()) {
                break;
            }
            builder.append(",");
        }
        return builder.toString();
    }

    public void createTable() {
        getJdbcTemplate().execute(USERS_TABLE_DDL);
    }
}
