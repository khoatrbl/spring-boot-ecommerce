package com.khoatrbl.ecommerce.domain.dtos;

import com.khoatrbl.ecommerce.domain.entities.ProductBrand;
import com.khoatrbl.ecommerce.domain.entities.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddProductRequest {
    private String productName;

    private String productDescription;

    private BigDecimal price;

    private int stock;

    private ProductCategory category;

    private ProductBrand brand;
}
