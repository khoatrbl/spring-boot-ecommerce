package com.khoatrbl.ecommerce.domain.dtos;

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
public class CartItemResponse {
    private UUID productId;
    private String productName;
    private BigDecimal price;
    private int quantity;
    private BigDecimal subTotal;

}
