package com.spring.jdbc.repository;

import com.spring.jdbc.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    static RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
            rs.getLong("id"),
            rs.getString("account"),
            rs.getString("password"),
            rs.getString("name"),
            rs.getString("address"),
            rs.getString("phone_no"),
            rs.getString("email"),
            rs.getDate("birth"),
            rs.getTimestamp("create_at"),
            rs.getTimestamp("password_update_at"),
            User.UserRole.findByUserRole(rs.getString("role"))
    );

    @Override
    public Optional<User> findById(Long id) throws Exception {
        return Optional.ofNullable(jdbcTemplate.query(
                "SELECT * FROM USER WHERE ID = ?",
                userRowMapper,
                id
        ).stream().findAny().orElseThrow(() -> new Exception("exception")));
    }

    @Override
    public Stream<User> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM USER",
                userRowMapper
        ).stream();
    }

    @Override
    public Optional<User> save(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("user").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("account", user.getAccount());
        parameters.put("password", user.getPassword());
        parameters.put("name", user.getName());
        parameters.put("address", user.getAddress());
        parameters.put("phone_no", user.getPhoneNo());
        parameters.put("email", user.getEmail());
        parameters.put("birth", user.getBirth());
        parameters.put("role", user.getRole());
        parameters.put("create_at", new Timestamp(System.currentTimeMillis()));
        parameters.put("password_update_at", new Timestamp(System.currentTimeMillis()));
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        user.setId(key.longValue());
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByAccount(String account) throws Exception {
        return jdbcTemplate.query(
                "SELECT * FROM USER WHERE ACCOUNT = ?",
                userRowMapper,
                account
        ).stream().findAny();
    }
}
