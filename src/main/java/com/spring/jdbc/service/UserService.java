package com.spring.jdbc.service;

import com.spring.jdbc.model.User;
import com.spring.jdbc.repository.JdbcUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class UserService {

    private final JdbcUserRepository jdbcUserRepository;

    public Stream<User> findAllUsers() {
        return jdbcUserRepository.findAll();
    }

    public Optional<User> findById(Long id) throws Exception {
        return jdbcUserRepository.findById(id);
    }

    public Optional<User> findByAccount(String account) throws Exception {
        return jdbcUserRepository.findByAccount(account);
    }

    public Optional<User> join(User user) throws Exception {
        return jdbcUserRepository.save(user);
    }
}
