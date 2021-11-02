package com.spring.jdbc.repository;

import com.spring.jdbc.model.User;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
@AllArgsConstructor
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
        parameters.put("name", user.getName());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        user.setId(key.longValue());
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByAccount(String account) throws Exception {
        return Optional.ofNullable(jdbcTemplate.query(
                "SELECT * FROM USER WHERE ACCOUNT = ?",
                userRowMapper,
                account
        ).stream().findAny().orElseThrow(() -> new Exception("exception")));
    }
}
