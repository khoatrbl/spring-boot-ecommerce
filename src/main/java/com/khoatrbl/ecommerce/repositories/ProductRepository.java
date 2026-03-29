package com.khoatrbl.ecommerce.repositories;

import com.khoatrbl.ecommerce.domain.entities.Product;
import com.khoatrbl.ecommerce.domain.entities.ProductBrand;
import com.khoatrbl.ecommerce.domain.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAllByCategoryAndBrandAndActive(ProductCategory category, ProductBrand brand, boolean active);
    List<Product> findAllByCategoryAndActive(ProductCategory category, boolean active);
    List<Product> findAllByBrandAndActive(ProductBrand brand, boolean active);
    List<Product> findAllByActive(boolean active);
    List<Product> findAllByCategoryAndBrand(ProductCategory category, ProductBrand brand);
    List<Product> findAllByCategory(ProductCategory category);
    List<Product> findAllByBrand(ProductBrand brand);
}
