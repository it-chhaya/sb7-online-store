package com.devkh.onlinestore.api.product.web;

import jakarta.validation.constraints.*;

public record CreateProductDto(@NotBlank(message = "Name is required!")
                               @Size(min = 5, max = 255)
                               String name,
                               @NotBlank(message = "Description is required!")
                               @Size(min = 5, message = "Description must be greater than 5")
                               String description,
                               @NotBlank
                               String image,
                               @NotNull(message = "Category ID is required!")
                               @Positive(message = "Category ID must be greater than 0")
                               Integer categoryId) {
}
