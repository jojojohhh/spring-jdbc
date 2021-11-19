package com.spring.jdbc.service;

import com.spring.jdbc.model.Cart;
import com.spring.jdbc.repository.JdbcCartRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final JdbcCartRepository cartRepository;

    public Optional<Cart> findByUserId(Long id) {
        return cartRepository.findByUserId(id);
    }

    public void save(Cart cart) {
        cartRepository.save(cart);
    }
}
