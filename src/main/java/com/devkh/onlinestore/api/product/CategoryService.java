package com.devkh.onlinestore.api.product;

import com.devkh.onlinestore.api.product.web.CategoryDto;

import java.util.List;

public interface CategoryService {

    /**
     * This method is used to create a new category
     * resource into database
     * @param categoryDto is the request data from client
     */
    void createNew(CategoryDto categoryDto);

    /**
     * This method is used to retrieve resource category from database
     * @return List<CategoryDto>
     */
    List<CategoryDto> findAll();

    /**
     * This method is used to retrieve resource category by name
     * from database
     * @param name of category
     * @return CategoryDto
     */
    CategoryDto findByName(String name);
}
