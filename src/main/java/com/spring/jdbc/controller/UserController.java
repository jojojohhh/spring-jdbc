package com.spring.jdbc.controller;

import com.spring.jdbc.model.User;
import com.spring.jdbc.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
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
    public Map<String, Object> join(@RequestBody User user) throws Exception {
        Map<String, Object> response = new HashMap<>();

        if (userService.findByAccount(user.getAccount()).isPresent()) {
            response.put("duplication", true);
        } else {
            response.put("success", userService.join(user) != null ? true : false);
        }
        return response;
    }
}
