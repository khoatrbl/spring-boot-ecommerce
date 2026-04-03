package com.khoatrbl.ecommerce.services.impl;

import com.khoatrbl.ecommerce.domain.entities.Orders;
import com.khoatrbl.ecommerce.domain.entities.OrderStatus;
import com.khoatrbl.ecommerce.repositories.OrderRepository;
import com.khoatrbl.ecommerce.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public List<Orders> getAllOrders(UUID userId, OrderStatus status) {
        if (status != null) {
            return orderRepository.findAllByUserIdAndStatus(userId, status);
        }

        return orderRepository.findAllByUserId(userId);
    }
}
