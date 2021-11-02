package com.spring.jdbc.repository;

import com.spring.jdbc.model.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface UserRepository {

    Optional<User> findById(Long id);
    Stream<User> findAll();
    Optional<User> save(User user);
}
