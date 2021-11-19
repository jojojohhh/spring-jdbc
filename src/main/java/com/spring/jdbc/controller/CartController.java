package com.spring.jdbc.controller;

import com.spring.jdbc.model.Cart;
import com.spring.jdbc.model.Product;
import com.spring.jdbc.model.User;
import com.spring.jdbc.service.CartService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/cart/user/{id}")
    public Cart getCart(@PathVariable Long id) {
        return cartService.findByUserId(id).get();
    }

    @PostMapping("/cart/user/{id}/product")
    public void postCart(@PathVariable Long id, @RequestBody Product product) {
        Optional<Cart> cart = cartService.findByUserId(id);
        cart.ifPresent(
                value -> cartService.save(
                        Cart.builder()
                                .id(value.getId())
                                .user(User.builder()
                                        .id(id)
                                        .build())
                                .products(Collections.singletonList(product))
                                .productQuantity(value.getProductQuantity())
                                .build()
        ));
    }
}
