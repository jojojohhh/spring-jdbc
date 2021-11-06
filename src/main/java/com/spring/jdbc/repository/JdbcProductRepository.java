package com.spring.jdbc.repository;

import com.spring.jdbc.model.Product;
import com.spring.jdbc.model.ProductCategory;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class JdbcProductRepository implements ProductRepository{

    private final JdbcTemplate jdbcTemplate;

    static RowMapper<Product> productRowMapper = (rs, rowNum) -> new Product(
      rs.getLong("id"),
      rs.getString("name"),
      new ProductCategory(rs.getLong("product_category_id"), rs.getString("product_category_name"), new ProductCategory(rs.getLong("parent_category"), rs.getString("parent_name"), null)),
      rs.getInt("price"),
      rs.getInt("stock")
    );

    @Override
    public Optional<Product> findById(Long id) throws Exception {
        return Optional.ofNullable(jdbcTemplate.query(
                "SELECT PRODUCT.ID AS ID, PRODUCT.NAME AS NAME, PRODUCT.CATEGORY_ID AS PRODUCT_CATEGORY_ID, PRODUCT.PRICE AS PRICE, PRODUCT.STOCK AS STOCK, " +
                        "CATEGORY.NAME AS PRODUCT_CATEGORY_NAME, CATEGORY.PARENT AS PARENT_CATEGORY, PARENT_CATEGORY.NAME AS PARENT_NAME " +
                        "FROM PRODUCT, PRODUCT_CATEGORY AS CATEGORY LEFT JOIN PRODUCT_CATEGORY AS PARENT_CATEGORY ON CATEGORY.PARENT = PARENT_CATEGORY.ID " +
                        "WHERE PRODUCT.CATEGORY_ID = CATEGORY.ID AND PRODUCT.ID = ?",
                productRowMapper,
                id
        ).stream().findAny().orElseThrow(() -> new Exception("exception")));
    }

    @Override
    public Stream<Product> findAll() {
        return jdbcTemplate.query(
                "SELECT PRODUCT.ID AS ID, PRODUCT.NAME AS NAME, PRODUCT.CATEGORY_ID AS PRODUCT_CATEGORY_ID, PRODUCT.PRICE AS PRICE, PRODUCT.STOCK AS STOCK, " +
                        "CATEGORY.NAME AS PRODUCT_CATEGORY_NAME, CATEGORY.PARENT AS PARENT_CATEGORY, PARENT_CATEGORY.NAME AS PARENT_NAME " +
                        "FROM PRODUCT, PRODUCT_CATEGORY AS CATEGORY LEFT JOIN PRODUCT_CATEGORY AS PARENT_CATEGORY ON CATEGORY.PARENT = PARENT_CATEGORY.ID " +
                        "WHERE PRODUCT.CATEGORY_ID = CATEGORY.ID",
                productRowMapper
        ).stream();
    }

    @Override
    public String save(Product product) {
        return jdbcTemplate.update(
                "INSERT INTO PRODUCT(NAME, CATEGORY_ID, PRICE, STOCK) VALUES (?, ?, ?, ?)",
                product.getName(),
                product.getCategory().getId(),
                product.getPrice(),
                product.getStock()
        ) > 0 ? "success" : "fail";
    }

    @Override
    public Optional<Product> findByName(String name) throws Exception {
        return Optional.ofNullable(jdbcTemplate.query(
                "SELECT PRODUCT.ID AS ID, PRODUCT.NAME AS NAME, PRODUCT.CATEGORY_ID AS PRODUCT_CATEGORY_ID, PRODUCT.PRICE AS PRICE, PRODUCT.STOCK AS STOCK, " +
                        "CATEGORY.NAME AS PRODUCT_CATEGORY_NAME, CATEGORY.PARENT AS PARENT_CATEGORY, PARENT_CATEGORY.NAME AS PARENT_NAME " +
                        "FROM PRODUCT, PRODUCT_CATEGORY AS CATEGORY LEFT JOIN PRODUCT_CATEGORY AS PARENT_CATEGORY ON CATEGORY.PARENT = PARENT_CATEGORY.ID " +
                        "WHERE PRODUCT.CATEGORY_ID = CATEGORY.ID AND PRODUCT.NAME = ?",
                productRowMapper,
                name
        ).stream().findAny().orElseThrow(() -> new Exception("exception")));
    }
}
