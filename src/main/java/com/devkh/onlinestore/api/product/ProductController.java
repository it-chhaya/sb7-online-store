package com.devkh.onlinestore.api.product;

import com.devkh.onlinestore.api.product.web.CreateProductDto;
import com.devkh.onlinestore.api.product.web.ProductDto;
import com.devkh.onlinestore.api.product.web.UpdateProductDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{uuid}")
    public void findByUuid(@PathVariable String uuid) {
        productService.findByUuid(uuid);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    public void deleteByUuid(@PathVariable String uuid) {
        productService.deleteByUuid(uuid);
    }

    @GetMapping
    public List<ProductDto> findAll() {
        return productService.findAll();
    }

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
