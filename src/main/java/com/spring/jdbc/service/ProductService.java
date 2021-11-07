package com.spring.jdbc.service;

import com.spring.jdbc.model.Product;
import com.spring.jdbc.repository.JdbcProductCategoryRepository;
import com.spring.jdbc.repository.JdbcProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final JdbcProductRepository jdbcProductRepository;
    private final JdbcProductCategoryRepository jdbcProductCategoryRepository;

    public void save(Product product) throws Exception {
        jdbcProductRepository.save(product);
    }

    public Stream<Product> findAll() {
        return jdbcProductRepository.findAll();
    }
}
