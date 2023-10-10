package com.devkh.onlinestore.product.web;

import jakarta.validation.constraints.*;

public record UpdateProductDto(@NotBlank
                               @Min(5)
                               @Max(255)
                               String name,
                               @NotBlank
                               @Size(min = 5)
                               String description,
                               @NotNull
                               @Positive
                               Integer categoryId) {
}
