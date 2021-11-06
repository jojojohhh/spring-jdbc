package com.spring.jdbc.repository;

import com.spring.jdbc.model.Product;
import com.spring.jdbc.model.ProductCategory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(SpringExtension.class)
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JdbcProductRepository.class)
public class ProductRepositoryTest {

    @Autowired
    private JdbcProductRepository jdbcProductRepository;

    static Stream<Product> entities() {
        return Stream.of(
                new Product("TOP", new ProductCategory(11L, "TOP"), 20000, 10),
                new Product("PANTS", new ProductCategory(12L, "PANTS"), 50000, 10),
                new Product("OUTER", new ProductCategory(13L, "OUTER"), 100000, 10)
        );
    }

    @BeforeEach
    public void setUp() {
        List<String> result = entities()
                .map(jdbcProductRepository::save)
                .filter(s -> s.equals("success"))
                .collect(Collectors.toList());

        Assertions.assertEquals(entities().count(), result.size());
    }

    @Test
    public void findByNameSuccessTest() throws Exception {
        Optional<Product> expected = entities().filter(product -> product.getName().equals("TOP")).findFirst();
        Optional<Product> actual = jdbcProductRepository.findByName("TOP");

        Assertions.assertTrue(expected.isPresent() && actual.isPresent() &&
                expected.get().getName().equals(actual.get().getName()) &&
                expected.get().getPrice() == actual.get().getPrice() &&
                expected.get().getStock() == actual.get().getStock() &&
                expected.get().getCategory().getId().equals(actual.get().getCategory().getId()));
    }
}
