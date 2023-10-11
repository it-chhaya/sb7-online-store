package com.devkh.onlinestore.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Boolean existsByUuid(String uuid);

    Optional<Product> findByUuid(String uuid);
}
