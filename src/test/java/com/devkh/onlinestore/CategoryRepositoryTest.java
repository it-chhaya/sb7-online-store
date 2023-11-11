package com.devkh.onlinestore;

import com.devkh.onlinestore.api.product.Category;
import com.devkh.onlinestore.api.product.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void test_findAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        categories.forEach(System.out::println);
    }

    @Test
    void test_createNewCategory() {

    }

}
