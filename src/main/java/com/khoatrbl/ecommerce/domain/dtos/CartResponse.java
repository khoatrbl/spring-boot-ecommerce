package com.khoatrbl.ecommerce.domain.dtos;

import com.khoatrbl.ecommerce.domain.entities.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {
    private UUID userId;
    private Map<UUID, CartItem> items;
    private BigDecimal totalAmount;
    private int totalNumberOfItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
