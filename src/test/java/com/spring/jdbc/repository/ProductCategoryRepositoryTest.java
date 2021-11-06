package com.spring.jdbc.repository;

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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(SpringExtension.class)
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JdbcProductCategoryRepository.class)
public class ProductCategoryRepositoryTest {

    @Autowired
    private JdbcProductCategoryRepository categoryRepository;

    private Stream<ProductCategory> entities() {
        return Stream.of(
          new ProductCategory(0L, "parent", null),
          new ProductCategory(1L, "test", new ProductCategory(0L, "parent", null))
        );
    }

    @BeforeEach
    public void setUp() {
        List<String> result = entities()
                .map(categoryRepository::save)
                .filter(s -> s.equals("success"))
                .collect(Collectors.toList());

        Assertions.assertEquals(entities().count(), result.size());
    }

    @Test
    public void findByIdSuccessTest() throws Exception {
        Optional<ProductCategory> expected = entities().filter(productCategory -> productCategory.getId().equals(0L)).findFirst();
        Optional<ProductCategory> actual = categoryRepository.findById(0L);

        Assertions.assertTrue(expected.isPresent() && actual.isPresent() &&
                expected.get().getId().equals(actual.get().getId()) &&
                expected.get().getName().equals(actual.get().getName()) &&
                Objects.equals(expected.get().getCategoryParent(), actual.get().getCategoryParent()));
    }
}
