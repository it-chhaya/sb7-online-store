package com.devkh.onlinestore.api.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    // Derived Query Method: automatically generate query
    // follow structure of method name
    Optional<Category> findByName(String name);

}
