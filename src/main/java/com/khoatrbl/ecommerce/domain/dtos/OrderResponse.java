package com.khoatrbl.ecommerce.domain.dtos;

import com.khoatrbl.ecommerce.domain.entities.OrderItem;
import com.khoatrbl.ecommerce.domain.entities.OrderStatus;
import com.khoatrbl.ecommerce.domain.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private UUID orderId;
    private User user;
    private Map<UUID ,OrderItem> items;
    private BigDecimal total;
    private OrderStatus status;
    private String shippingAddress;

}
