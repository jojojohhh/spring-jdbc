package com.spring.jdbc.repository;

import com.spring.jdbc.model.User;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class JdbcUserRepository {

    private final JdbcTemplate jdbcTemplate;


    static RowMapper<User> userRowMapper = (rs, rowNum) -> new User();
}
