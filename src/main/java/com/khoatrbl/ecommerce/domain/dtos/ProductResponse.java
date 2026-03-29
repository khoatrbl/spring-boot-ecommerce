package com.khoatrbl.ecommerce.domain.dtos;

import com.khoatrbl.ecommerce.domain.entities.ProductBrand;
import com.khoatrbl.ecommerce.domain.entities.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private UUID productId;
    private String productName;
    private String productDescription;
    private BigDecimal price;
    private int stock;
    private ProductCategory category;
    private ProductBrand brand;
    private Boolean active;
}
