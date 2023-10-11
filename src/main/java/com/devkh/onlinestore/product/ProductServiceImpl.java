package com.devkh.onlinestore.product;

import com.devkh.onlinestore.product.web.CreateProductDto;
import com.devkh.onlinestore.product.web.ProductDto;
import com.devkh.onlinestore.product.web.UpdateProductDto;
import com.devkh.onlinestore.util.RandomUtil;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public void createNew(CreateProductDto createProductDto) {
        Product product = productMapper.fromCreateProductDto(createProductDto);
        product.setUuid(UUID.randomUUID().toString());
        product.setCode("PRO-" + RandomUtil.generateCode());
        productRepository.save(product);
    }


    @Override
    public void updateByUuid(String uuid, UpdateProductDto updateProductDto) {
        // Step 1: Check uuid of product in the database
        if (productRepository.existsByUuid(uuid)) {

            // Step 2: Load old product
            Product product = productRepository.findByUuid(uuid)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            String.format("Product UUID = %s doesn't exist in db!", uuid)));

            // Step 3: Map updating product partially
            productMapper.fromUpdateProductDto(product, updateProductDto);

            if (updateProductDto.categoryId() != null) {
                Category newCategory = new Category();
                newCategory.setId(updateProductDto.categoryId());
                product.setCategory(newCategory);
            }

            // Step 4: Save latest product
            productRepository.save(product);

            return;
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Product UUID = %s doesn't exist in db!", uuid));
    }

    @Override
    public void deleteByUuid(String uuid) {

    }

    @Override
    public List<ProductDto> findAll() {
        return null;
    }

    @Override
    public ProductDto findByUuid(String uuid) {
        return null;
    }
}
