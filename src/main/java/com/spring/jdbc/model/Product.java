package com.spring.jdbc.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    private Long id;
    private String name;
    private ProductCategory category;
    private int price;
    private int stock;

    public Product(String name, ProductCategory category, int price, int stock) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }
}
