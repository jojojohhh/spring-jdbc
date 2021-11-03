package com.spring.jdbc.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class ProductCategory {
    private Long id;
    private String name;
    private ProductCategory categoryParent;

    public ProductCategory(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
