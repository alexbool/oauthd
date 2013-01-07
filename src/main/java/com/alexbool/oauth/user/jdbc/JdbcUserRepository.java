package com.alexbool.oauth.user.jdbc;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

import com.google.common.base.Optional;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.alexbool.oauth.user.User;
import com.alexbool.oauth.user.UserRepository;
import com.alexbool.oauth.user.UsernameAlreadyExistsException;

/**
 * JDBC implementation of {@link UserRepository}.
 * 
 * @author Alexander Bulaev
 */
public class JdbcUserRepository extends JdbcDaoSupport implements UserRepository {

    @Override
    public boolean exists(String login) {
        return getJdbcTemplate().queryForInt("SELECT COUNT(*) FROM users WHERE login = ?", login) == 1;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserDetails user;
        try {
            user = getJdbcTemplate().queryForObject("SELECT * FROM users WHERE login = ?",
                    new RowMapper<UserDetails>() {
                        @Override
                        public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                            return new User(bytesToUuid(rs.getBytes("uid")),
                                    Optional.fromNullable(rs.getString("login")),
                                    rs.getString("password"), rs.getBoolean("deleted"),
                                    Arrays.asList(rs.getString("authorities").split(",")));
                        }
                    }, login);
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException(String.format("User %s not found", login));
        }
        return user;
    }

    @Override
    public void save(User user) {
        try {
            getJdbcTemplate().update(
                    "INSERT INTO users (uid, login, password, authorities, deleted) VALUES (?, ?, ?, ?, ?)",
                    uuidToBytes(user.getUid()), user.getLogin().orNull(), user.getPassword(),
                    joinAuthorities(user.getAuthorities()), !user.isEnabled());
        }
        catch (DuplicateKeyException ex) {
            throw new UsernameAlreadyExistsException(user.getUsername());
        }
    }

    @Override
    public void updatePassword(String login, String password) {
        getJdbcTemplate().update("UPDATE users SET password = ? WHERE login = ?", password, login);
    }

    @Override
    public void saveAuthorities(String login, Collection<? extends GrantedAuthority> authorities) {
        getJdbcTemplate().update("UPDATE users SET authorities = ? WHERE login = ?",
                joinAuthorities(authorities), login);
    }

    @Override
    public void delete(String login) {
        getJdbcTemplate().update("DELETE FROM users WHERE login = ?", login);
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

    private byte[] uuidToBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    private UUID bytesToUuid(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        return new UUID(bb.getLong(), bb.getLong());
    }
}
