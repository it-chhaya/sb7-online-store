package com.devkh.onlinestore.product.web;

import jakarta.validation.constraints.*;

public record CreateProductDto(@NotBlank @Size(min = 5, max = 255)
                               String name,
                               @NotBlank @Size(min = 5)
                               String description,
                               @NotBlank
                               String image,
                               @NotNull @Positive
                               Integer categoryId) {
}
