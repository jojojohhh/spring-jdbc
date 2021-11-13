package com.spring.jdbc.controller;

import com.spring.jdbc.model.Cart;
import com.spring.jdbc.service.CartService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/cart/user/{id}")
    public Cart getCart(@PathVariable Long id) {
        return cartService.findByUserId(id).get();
    }
}
