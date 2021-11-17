package com.spring.jdbc.repository;

import com.spring.jdbc.model.Cart;

import java.util.Optional;

public interface CartRepository {
    Optional<Cart> findByUserId(Long id);
    String save(Cart cart);
}
