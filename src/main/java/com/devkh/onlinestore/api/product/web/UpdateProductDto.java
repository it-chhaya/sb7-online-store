package com.devkh.onlinestore.api.product.web;

import jakarta.validation.constraints.*;

public record UpdateProductDto(@Size(min = 5, max = 255)
                               String name,
                               @Size(min = 5, message = "Description must be greater than 5")
                               String description,
                               @Positive(message = "Category ID must be greater than 0")
                               Integer categoryId) {
}
