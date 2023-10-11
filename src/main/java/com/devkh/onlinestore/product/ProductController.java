package com.devkh.onlinestore.product;

import com.devkh.onlinestore.product.web.CreateProductDto;
import com.devkh.onlinestore.product.web.UpdateProductDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{uuid}")
    public void updateByUuid(@PathVariable String uuid,
                             @RequestBody UpdateProductDto updateProductDto) {
        productService.updateByUuid(uuid, updateProductDto);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createNew(@RequestBody @Valid CreateProductDto createProductDto) {
        productService.createNew(createProductDto);
    }
}
