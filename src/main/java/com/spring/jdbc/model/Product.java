package com.spring.jdbc.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {

    private Long id;
    private String name;
    private int price;
    private int stock;
}
