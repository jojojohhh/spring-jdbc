package com.spring.jdbc.controller;

import com.spring.jdbc.model.User;
import com.spring.jdbc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    public List<User> getUsers() {
        return userService.findAllUsers().collect(Collectors.toList());
    }

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) throws Exception {
        return userService.findById(id).get();
    }

    @PostMapping(value = "/user", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public User join(@RequestBody User user) throws Exception {
        return !userService.findByAccount(user.getAccount()).isPresent() ? userService.join(user).get() : user;
    }
}
