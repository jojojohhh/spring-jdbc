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
}
