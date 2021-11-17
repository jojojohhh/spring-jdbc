package com.spring.jdbc.repository;

import com.spring.jdbc.model.Cart;
import com.spring.jdbc.model.Product;
import com.spring.jdbc.model.ProductCategory;
import com.spring.jdbc.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class JdbcCartRepository implements CartRepository{

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Cart> findByUserId(Long id) {
        return Objects.requireNonNull(jdbcTemplate.query(
                "SELECT CART.ID AS ID, CART.USER_ID AS USER_ID, USER.ACCOUNT AS USER_ACCOUNT, USER.PASSWORD AS USER_PASSWORD, USER.NAME AS USER_NAME, USER.ADDRESS AS USER_ADDRESS, " +
                        "USER.PHONE_NO AS USER_PHONE_NO, USER.EMAIL AS USER_EMAIL, USER.BIRTH AS USER_BIRTH, USER.CREATE_AT AS USER_CREATE_AT, USER.PASSWORD_UPDATE_AT AS USER_PASSWORD_UPDATE_AT, USER.ROLE AS USER_ROLE, " +
                        "CART.PRODUCT_ID AS PRODUCT_ID, PRODUCT.NAME AS PRODUCT_NAME, PRODUCT.CATEGORY_ID AS PRODUCT_CATEGORY_ID, PRODUCT_CATEGORY.NAME AS PRODUCT_CATEGORY_NAME, PRODUCT.PRICE AS PRODUCT_PRICE, PRODUCT.STOCK AS PRODUCT_STOCK, " +
                        "PRODUCT_CATEGORY.PARENT AS PRODUCT_CATEGORY_PARENT_ID, PRODUCT_CATEGORY.NAME AS PRODUCT_CATEGORY_PARENT_NAME, CART.PRODUCT_QUANTITY AS CART_PRODUCT_QUANTITY " +
                        "FROM CART LEFT JOIN USER ON CART.USER_ID = USER.ID LEFT JOIN PRODUCT ON CART.PRODUCT_ID = PRODUCT.ID LEFT JOIN PRODUCT_CATEGORY ON PRODUCT.CATEGORY_ID = PRODUCT_CATEGORY.ID " +
                        "WHERE CART.USER_ID = ?",
                new ResultSetExtractor<Map<Long, Cart>>() {
                    @Override
                    public Map<Long, Cart> extractData(ResultSet rs) throws SQLException, DataAccessException {
                        Map<Long, Cart> cartById = new HashMap<>();
                        while (rs.next()) {
                            Long id = rs.getLong("id");
                            Cart cart = cartById.getOrDefault(id,
                                    new Cart(
                                            id,
                                            new User(
                                                    rs.getLong("user_id"),
                                                    rs.getString("user_account"),
                                                    rs.getString("user_password"),
                                                    rs.getString("user_name"),
                                                    rs.getString("user_address"),
                                                    rs.getString("user_phone_no"),
                                                    rs.getString("user_email"),
                                                    rs.getDate("user_birth"),
                                                    rs.getTimestamp("user_create_at"),
                                                    rs.getTimestamp("user_password_update_at"),
                                                    User.UserRole.findByUserRole(rs.getString("user_role"))),
                                            rs.getInt(rs.getInt("cart_product_quantity"))));
                            cart.addProduct(new Product(
                                    rs.getLong("product_id"),
                                    rs.getString("product_name"),
                                    new ProductCategory(
                                            rs.getLong("product_category_id"),
                                            rs.getString("product_category_name"),
                                            new ProductCategory(
                                                    rs.getLong("product_category_parent_id"),
                                                    rs.getString("product_category_parent_name")
                                            )
                                    ),
                                    rs.getInt("product_price"),
                                    rs.getInt("product_stock")));
                            cartById.put(id, cart);
                        }
                        return cartById;
                    }
                },
                id
        )).values().stream().findAny();
    }

    @Override
    public String save(Cart cart) {
        return jdbcTemplate.update(
                "INSERT INTO CART(USER_ID, PRODUCT_ID, PRODUCT_QUANTITY) VALUES (?, ?, ?) ",
                cart.getUser().getId(),
                cart.getProducts().get(0).getId(),
                cart.getProductQuantity()
        ) > 0 ? "success" : "fail";
    }

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
