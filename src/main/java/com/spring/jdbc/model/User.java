package com.spring.jdbc.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter
@ToString
public class User {
    private int id;
    private String account;
    private String password;
    private String name;
    private String address;
    private String phoneMo;
    private String email;
    private Date birth;
}
