package com.spring.jdbc.service;

import com.spring.jdbc.model.ProductCategory;
import com.spring.jdbc.repository.JdbcProductCategoryRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    private final JdbcProductCategoryRepository categoryRepository;

    public Optional<ProductCategory> findById(Long id) throws Exception {
        return categoryRepository.findById(id);
    }

    public Stream<ProductCategory> findAll() {
        return categoryRepository.findAll();
    }

}
