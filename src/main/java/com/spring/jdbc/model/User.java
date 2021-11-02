package com.spring.jdbc.model;

import lombok.*;

import java.util.Date;

@Getter @Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private String account;
    private String password;
    private String name;
    private String address;
    private String phoneNo;
    private String email;
    private Date birth;
    private UserRole role;

    public enum UserRole {
        ROLE_USER, ROLE_ADMIN;

        public static UserRole findByUserRole(String role) {
            for (UserRole userRole : UserRole.values()) {
                if (userRole.name().equals(role)) {
                    return userRole;
                }
            }
            throw new RuntimeException();
        }
    }
}
