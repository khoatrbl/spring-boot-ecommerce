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
public class OrderItemResponse {
    private UUID orderId;
    private UUID productId;
    private String productNameAtPurchase;
    private BigDecimal priceAtPurchase;
    private int quantity;
    private BigDecimal subTotal;
}
