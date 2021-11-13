package com.spring.jdbc.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private Long id;
    private User user;
    private List<Product> products;
    private int productQuantity;

    public Cart(Long id, User user, int productQuantity) {
        this.id = id;
        this.user = user;
        this.products = new ArrayList<>();
        this.productQuantity = productQuantity;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }
}
