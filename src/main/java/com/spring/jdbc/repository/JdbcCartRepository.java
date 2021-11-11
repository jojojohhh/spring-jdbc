package com.spring.jdbc.repository;

import com.spring.jdbc.model.Cart;
import com.spring.jdbc.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcCartRepository {

    private final JdbcTemplate jdbcTemplate;

//    static RowMapper<Cart> cartRowMapper = (rs, rowNum) -> new Cart(
//            rs.getLong("id"),
//            new User(
//                    rs.getLong("user_id"),
//                    rs.getString("user_account"),
//                    rs.getString("user_password"),
//                    rs.getString("user_name"),
//                    rs.getString("user_address"),
//                    rs.getString("user_phone_no"),
//                    rs.getString("user_email"),
//                    rs.getDate("user_birth"),
//                    rs.getTimestamp("user_create_at"),
//                    rs.getTimestamp("user_password_update_at"),
//                    User.UserRole.findByUserRole(rs.getString("user_role"))
//            ),
//
//
//    );
}
