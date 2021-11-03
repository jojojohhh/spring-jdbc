package com.spring.jdbc.repository;

import com.spring.jdbc.model.ProductCategory;

import java.util.Optional;
import java.util.stream.Stream;

public interface ProductCategoryRepository {

    Optional<ProductCategory> findById(Long id) throws Exception;
    Stream<ProductCategory> findAll();
    Optional<ProductCategory> save(ProductCategory productCategory);
    Optional<ProductCategory> findByName(String name) throws Exception;
}
