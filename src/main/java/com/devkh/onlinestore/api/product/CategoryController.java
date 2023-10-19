package com.devkh.onlinestore.api.product;

import com.devkh.onlinestore.api.product.web.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping
    public List<CategoryDto> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{cateName}")
    public CategoryDto findByName(@PathVariable String cateName) {
        return categoryService.findByName(cateName);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createNew(@RequestBody CategoryDto categoryDto) {
        categoryService.createNew(categoryDto);
    }
}
