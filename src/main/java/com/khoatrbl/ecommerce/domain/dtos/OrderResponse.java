package com.khoatrbl.ecommerce.domain.dtos;

import com.khoatrbl.ecommerce.domain.entities.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private UUID orderId;
    private UserResponse user;
    private String recipientName;
    private String phoneNumber;
    private Map<UUID, OrderItemResponse> items;
    private BigDecimal total;
    private OrderStatus status;
    private String shippingAddress;

}
