package com.devkh.onlinestore.product;

import com.devkh.onlinestore.product.web.CreateProductDto;
import com.devkh.onlinestore.product.web.ProductDto;
import com.devkh.onlinestore.product.web.UpdateProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "categoryId", target = "category.id")
    Product fromCreateProductDto(CreateProductDto dto);

    @Mapping(source = "categoryId", target = "category.id")
    Product fromUpdateProductDto(UpdateProductDto dto);

    ProductDto toProductDto(Product product);

    List<ProductDto> toProductDtoList(List<Product> products);

}
