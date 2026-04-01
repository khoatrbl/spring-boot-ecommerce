package com.khoatrbl.ecommerce.services;

import com.khoatrbl.ecommerce.domain.dtos.AddProductRequest;
import com.khoatrbl.ecommerce.domain.dtos.UpdateProductRequest;
import com.khoatrbl.ecommerce.domain.entities.Product;
import com.khoatrbl.ecommerce.domain.entities.ProductBrand;
import com.khoatrbl.ecommerce.domain.entities.ProductCategory;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<Product> getAllProducts(ProductCategory category, ProductBrand brand, Boolean active);

    Product getProductById(UUID productId);

    Product addProduct(AddProductRequest request);

    Product updateProduct(UUID productId, UpdateProductRequest request);

    void deleteProduct(UUID productId);
}
