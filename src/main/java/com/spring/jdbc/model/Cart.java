package com.spring.jdbc.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class Cart {
    private Long id;
    private User user;
    private List<Product> products;
    private int productQuantity;
}
