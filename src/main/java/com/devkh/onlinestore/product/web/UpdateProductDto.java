package com.devkh.onlinestore.product.web;

import jakarta.validation.constraints.*;

public record UpdateProductDto(String name,
                               String description,
                               Integer categoryId) {
}
