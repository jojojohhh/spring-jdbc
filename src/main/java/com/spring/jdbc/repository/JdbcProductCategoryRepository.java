package com.spring.jdbc.repository;

import com.spring.jdbc.model.ProductCategory;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class JdbcProductCategoryRepository implements ProductCategoryRepository{

    private final JdbcTemplate jdbcTemplate;

    static RowMapper<ProductCategory> productCategoryRowMapper = (rs, rowNum) -> new ProductCategory(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getInt("category_parent") != -1 ? new ProductCategory(rs.getLong("category_parent"), rs.getString("parent_name")) : null
    );

    @Override
    public Optional<ProductCategory> findById(Long id) throws Exception {
        return jdbcTemplate.query(
                "SELECT CATEGORY.ID AS ID, CATEGORY.NAME AS NAME, IFNULL(CATEGORY.PARENT, -1) AS CATEGORY_PARENT, PARENT.NAME AS PARENT_NAME " +
                        "FROM PRODUCT_CATEGORY AS CATEGORY LEFT JOIN PRODUCT_CATEGORY AS PARENT ON CATEGORY.PARENT = PARENT.ID " +
                        "WHERE CATEGORY.ID = ?",
                productCategoryRowMapper,
                id
        ).stream().findAny();
    }

    @Override
    public Stream<ProductCategory> findAll() {
        return jdbcTemplate.query(
                "SELECT CATEGORY.ID AS ID, CATEGORY.NAME AS NAME, IFNULL(CATEGORY.PARENT, -1) AS CATEGORY_PARENT, PARENT.NAME AS PARENT_NAME " +
                        "FROM PRODUCT_CATEGORY AS CATEGORY LEFT JOIN PRODUCT_CATEGORY AS PARENT ON CATEGORY.PARENT = PARENT.ID ",
                productCategoryRowMapper
        ).stream();
    }

    @Override
    public String save(ProductCategory productCategory) {
        return jdbcTemplate.update(
                "INSERT INTO product_category (id, name, parent) VALUES (?, ?, ?)",
                productCategory.getId(),
                productCategory.getName(),
                productCategory.getCategoryParent() == null ? null : productCategory.getCategoryParent().getId()
        ) > 0 ? "success" : "fail";
    }

    @Override
    public Optional<ProductCategory> findByName(String name) throws Exception {
        return Optional.ofNullable(jdbcTemplate.query(
                "SELECT CATEGORY.ID AS ID, CATEGORY.NAME AS NAME, IFNULL(CATEGORY.PARENT, -1) AS CATEGORY_PARENT, PARENT.NAME AS PARENT_NAME " +
                        "FROM PRODUCT_CATEGORY AS CATEGORY LEFT JOIN PRODUCT_CATEGORY AS PARENT ON CATEGORY.PARENT = PARENT.ID " +
                        "WHERE CATEGORY.NAME = ?",
                productCategoryRowMapper,
                name
        ).stream().findAny().orElseThrow(() -> new Exception("exception")));
    }
}
