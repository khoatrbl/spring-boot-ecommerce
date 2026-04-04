package com.khoatrbl.ecommerce.services;

import com.khoatrbl.ecommerce.domain.dtos.CreateOrderRequest;
import com.khoatrbl.ecommerce.domain.entities.Orders;
import com.khoatrbl.ecommerce.domain.entities.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<Orders> getAllOrders(UUID userId, OrderStatus status);
    Orders addCartToOrder(UUID userId, CreateOrderRequest request);
}
