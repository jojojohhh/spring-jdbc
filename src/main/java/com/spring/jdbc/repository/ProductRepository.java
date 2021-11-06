package com.spring.jdbc.repository;

import com.spring.jdbc.model.Product;

import java.util.Optional;
import java.util.stream.Stream;

public interface ProductRepository {
    Optional<Product> findById(Long id) throws Exception;
    Stream<Product> findAll();
    String save(Product product);
    Optional<Product> findByName(String name) throws Exception;
}
