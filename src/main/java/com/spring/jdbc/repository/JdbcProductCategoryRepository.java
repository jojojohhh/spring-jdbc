package com.spring.jdbc.repository;

import com.spring.jdbc.model.ProductCategory;

import lombok.RequiredArgsConstructor;

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
@RequiredArgsConstructor
public class JdbcProductCategoryRepository implements ProductCategoryRepository{

    private final JdbcTemplate jdbcTemplate;

    static RowMapper<ProductCategory> productCategoryRowMapper = (rs, rowNum) -> new ProductCategory(
            rs.getLong("id"),
            rs.getString("name"),
            new ProductCategory(rs.getLong("category_parent"), rs.getString("parent_name"))
    );

    @Override
    public Optional<ProductCategory> findById(Long id) throws Exception {
        return Optional.ofNullable(jdbcTemplate.query(
                "SELECT CATEGORY.ID AS ID, CATEGORY.NAME AS NAME, CATEGORY.CATEGORY_PARENT AS CATEGORY_PARENT, CATEGORY1.NAME AS PARENT_NAME " +
                        "FROM PRODUCT_CATEGORY AS CATEGORY, PRODUCT_CATEGORY AS CATEGORY1 " +
                        "WHERE CATEGORY.ID = ? AND CATEGORY.CATEGORY_PARENT = CATEGORY1.ID",
                productCategoryRowMapper,
                id
        ).stream().findAny().orElseThrow(() -> new Exception("exception")));
    }

    @Override
    public Stream<ProductCategory> findAll() {
        return jdbcTemplate.query(
                "SELECT CATEGORY.ID AS ID, CATEGORY.NAME AS NAME, CATEGORY.CATEGORY_PARENT AS CATEGORY_PARENT, CATEGORY1.NAME AS PARENT_NAME " +
                        "FROM PRODUCT_CATEGORY AS CATEGORY, PRODUCT_CATEGORY AS CATEGORY1 " +
                        "WHERE CATEGORY.CATEGORY_PARENT = CATEGORY1.ID",
                productCategoryRowMapper
        ).stream();
    }

    @Override
    public Optional<ProductCategory> save(ProductCategory productCategory) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("user").usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", productCategory.getId());
        parameters.put("name", productCategory.getName());
        if (productCategory.getCategoryParent() != null) parameters.put("category_parent", productCategory.getCategoryParent());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        productCategory.setId(key.longValue());
        return Optional.of(productCategory);
    }

    @Override
    public Optional<ProductCategory> findByName(String name) throws Exception {
        return Optional.ofNullable(jdbcTemplate.query(
                "SELECT CATEGORY.ID AS ID, CATEGORY.NAME AS NAME, CATEGORY.CATEGORY_PARENT AS CATEGORY_PARENT, CATEGORY1.NAME AS PARENT_NAME " +
                        "FROM PRODUCT_CATEGORY AS CATEGORY, PRODUCT_CATEGORY AS CATEGORY1 " +
                        "WHERE CATEGORY.NAME = ? AND CATEGORY.CATEGORY_PARENT = CATEGORY1.ID",
                productCategoryRowMapper,
                name
        ).stream().findAny().orElseThrow(() -> new Exception("exception")));
    }
}
