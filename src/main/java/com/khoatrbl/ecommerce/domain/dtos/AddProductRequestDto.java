package com.khoatrbl.ecommerce.domain.dtos;

import com.khoatrbl.ecommerce.domain.entities.ProductBrand;
import com.khoatrbl.ecommerce.domain.entities.ProductCategory;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddProductRequestDto {

    @NotBlank(message = "Product name is required!")
    @Size(max = 120, message = "Product name must be less than {max} characters!")
    private String productName;

    @NotBlank(message = "Product description is required!")
    @Size(max = 1000, message = "Product description must be less than {max} characters!")
    private String productDescription;

    @NotNull(message = "Price is required!")
    @Positive
    private BigDecimal price;

    @NotNull(message = "Stock is required!")
    @Min(value = 1, message = "Stock must be greater or equal to {value}")
    private Integer stock;

    @NotNull(message = "Category is required!")
    private ProductCategory category;

    @NotNull(message = "Brand is required!")
    private ProductBrand brand;
}
