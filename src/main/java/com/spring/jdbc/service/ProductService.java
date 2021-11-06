package com.spring.jdbc.service;

import com.spring.jdbc.repository.JdbcProductCategoryRepository;
import com.spring.jdbc.repository.JdbcProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final JdbcProductRepository jdbcProductRepository;
    private final JdbcProductCategoryRepository jdbcProductCategoryRepository;
}
