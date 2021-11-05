package com.spring.jdbc.controller;

import com.spring.jdbc.model.ProductCategory;
import com.spring.jdbc.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductCategoryService categoryService;

    @GetMapping("/product/category/{id}")
    public ProductCategory getCategory(@PathVariable Long id) throws Exception {
        return categoryService.findById(id).get();
    }

    @GetMapping("/product/categorys")
    public List<ProductCategory> getAllCategory() {
        return categoryService.findAll().collect(Collectors.toList());
    }
}
