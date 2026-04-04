package com.khoatrbl.ecommerce.controllers;

import com.khoatrbl.ecommerce.domain.dtos.*;
import com.khoatrbl.ecommerce.domain.entities.Product;
import com.khoatrbl.ecommerce.domain.entities.ProductBrand;
import com.khoatrbl.ecommerce.domain.entities.ProductCategory;
import com.khoatrbl.ecommerce.mappers.ProductMapper;
import com.khoatrbl.ecommerce.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")

public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping(path = "/products")
    public ResponseEntity<List<ProductResponse>> getAllProducts(
            @RequestParam(required = false)ProductCategory category,
            @RequestParam(required = false)ProductBrand brand,
            @RequestParam(required = false)Boolean active
            ) {
        List<Product> products = productService.getAllProducts(category, brand, active);

        List<ProductResponse> productResponses = products.stream().map(productMapper::toResponse).toList();

        return ResponseEntity.ok(productResponses);
    }

    @GetMapping(path = "/products/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") UUID productId) {
        Product product = productService.getProductById(productId);

        ProductResponse productResponse = productMapper.toResponse(product);

        return ResponseEntity.ok(productResponse);
    }

    @PostMapping(path = "/admin/products")
    public ResponseEntity<ProductResponse> addProduct(
            @Valid @RequestBody AddProductRequestDto addProductRequestDto) {

        AddProductRequest request = productMapper.toAddProductRequest(addProductRequestDto);

        Product addedProduct = productService.addProduct(request);
        ProductResponse productResponse = productMapper.toResponse(addedProduct);

        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    @PutMapping(path = "/admin/products/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable("id") UUID productId,
            @Valid @RequestBody UpdateProductRequestDto updateProductRequestDto) {

        UpdateProductRequest request = productMapper.toUpdateProductRequest(updateProductRequestDto);

        Product updatedProduct = productService.updateProduct(productId, request);

        ProductResponse updatedProductResponse = productMapper.toResponse(updatedProduct);

        return ResponseEntity.ok(updatedProductResponse);
    }

    @DeleteMapping(path = "/admin/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") UUID productId) {
        productService.deleteProduct(productId);

        return ResponseEntity.noContent().build();
    }
}
