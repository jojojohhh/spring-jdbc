package com.spring.jdbc.repository;

import com.spring.jdbc.model.ProductCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(SpringExtension.class)
@DataJdbcTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private JdbcProductCategoryRepository categoryRepository;

    private Stream<ProductCategory> entities() {
        return Stream.of(
          new ProductCategory(0L, "parent", null),
          new ProductCategory(1L, "test", null)
        );
    }

    @Test
    public void findByIdSuccessTest() throws Exception {
        Optional<ProductCategory> expected = entities()
                .map(categoryRepository::save)
                .filter(productCategory -> productCategory.get().getId().equals(0L))
                .collect(Collectors.toList()).get(0);

        Optional<ProductCategory> actual = categoryRepository.findById(0L);

        Assertions.assertEquals(expected, actual);
    }
}
