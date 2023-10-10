package com.devkh.onlinestore.product;

import com.devkh.onlinestore.product.web.CreateProductDto;
import com.devkh.onlinestore.product.web.ProductDto;
import com.devkh.onlinestore.product.web.UpdateProductDto;

import java.util.List;

public interface ProductService {

    void createNew(CreateProductDto createProductDto);

    void updateByUuid(String uuid, UpdateProductDto updateProductDto);

    void deleteByUuid(String uuid);

    List<ProductDto> findAll();

    ProductDto findByUuid(String uuid);

}
