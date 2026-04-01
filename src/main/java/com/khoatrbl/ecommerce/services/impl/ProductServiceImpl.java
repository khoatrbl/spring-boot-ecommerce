package com.khoatrbl.ecommerce.services.impl;

import com.khoatrbl.ecommerce.domain.dtos.AddProductRequest;
import com.khoatrbl.ecommerce.domain.dtos.UpdateProductRequest;
import com.khoatrbl.ecommerce.domain.entities.Product;
import com.khoatrbl.ecommerce.domain.entities.ProductBrand;
import com.khoatrbl.ecommerce.domain.entities.ProductCategory;
import com.khoatrbl.ecommerce.repositories.ProductRepository;
import com.khoatrbl.ecommerce.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts(ProductCategory category, ProductBrand brand, Boolean active) {

        // Find every product with the specified category, brand and active status
        if (category != null && brand != null && active != null) {
            return productRepository
                    .findAllByCategoryAndBrandAndActive(category, brand, active);
        }

        // Find every product with the specified category and active status
        if (category != null && active != null) {
            return productRepository.findAllByCategoryAndActive(category, active);
        }

        // Find every product with the specified brand and active status
        if (brand != null && active != null) {
            return productRepository.findAllByBrandAndActive(brand, active);
        }

        if (active != null) {
            return productRepository.findAllByActive(active);
        }

        // Find every product with the specified category and brand, despite its active status
        if (category != null && brand != null) {
            return productRepository.findAllByCategoryAndBrand(category, brand);
        }

        // Find every product with the specified category, despite its active status
        if (category != null) {
            return productRepository.findAllByCategory(category);
        }

        // Find every product with the specified brand, despite its active status
        if (brand != null) {
            return productRepository.findAllByBrand(brand);
        }

        // Find every product in database, despite its active status
        return productRepository.findAll();

    }

    @Override
    public Product getProductById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product does not exist with id: " + productId));
    }

    @Override
    public Product addProduct(AddProductRequest request) {
        Product productToAdd = Product.builder()
                .productName(request.getProductName())
                .productDescription(request.getProductDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .category(request.getCategory())
                .brand(request.getBrand())
                .build();

        return productRepository.save(productToAdd);
    }

    @Override
    @Transactional
    public Product updateProduct(UUID productId, UpdateProductRequest request) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId));

        existingProduct.setProductName(request.getProductName());
        existingProduct.setProductDescription(request.getProductDescription());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setStock(request.getStock());
        existingProduct.setCategory(request.getCategory());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setActive(request.getActive());

        return productRepository.save(existingProduct);
    }

    @Override
    public void deleteProduct(UUID productId) {
        productRepository.deleteById(productId);
    }
}
