package com.spring.jdbc.repository;

import com.spring.jdbc.model.Cart;
import com.spring.jdbc.model.Product;
import com.spring.jdbc.model.ProductCategory;
import com.spring.jdbc.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(SpringExtension.class)
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JdbcCartRepository.class)
public class CartRepositoryTest {

    @Autowired
    private JdbcCartRepository cartRepository;

    static Stream<Cart> entity() {
        return Stream.of(
          Cart.builder()
                  .user(User.builder()
                          .id(99L)
                          .account("test")
                          .password("test")
                          .name("test")
                          .address("서울시")
                          .phoneNo("01012345678")
                          .email("test@test.test")
                          .role(User.UserRole.ROLE_USER)
                          .build())
                  .products(Arrays.asList(
                          Product.builder()
                                  .name("TOP")
                                  .category(ProductCategory.builder()
                                          .id(11L)
                                          .name("TOP")
                                          .build())
                                  .price(20000)
                                  .stock(10)
                                  .build(),
                          Product.builder()
                                  .name("PANTS")
                                  .category(ProductCategory.builder()
                                          .id(12L)
                                          .name("PANTS")
                                          .build())
                                  .price(50000)
                                  .stock(10)
                                  .build(),
                          Product.builder()
                                  .name("OUTER")
                                  .category(ProductCategory.builder()
                                          .id(13L)
                                          .name("OUTER")
                                          .build())
                                  .price(100000)
                                  .stock(10)
                                  .build()))
                  .productQuantity(1)
                  .build()
        );
    }

    @BeforeEach
    public void setUp() {
        List<String> result = entity()
                .map(cartRepository::save)
                .filter(s -> s.equals("success"))
                .collect(Collectors.toList());

        Assertions.assertEquals(entity().count(), result.size());
    }

    @Test
    public void findByUserIdSuccessTest() {
        Optional<Cart> expected = entity().findFirst();
        Optional<Cart> actual = cartRepository.findByUserId(2L);

        Assertions.assertTrue(expected.isPresent() && actual.isPresent() &&
                expected.get().getUser().getId().equals(actual.get().getUser().getId()));
    }
}
