package com.khoatrbl.ecommerce.controllers;

import com.khoatrbl.ecommerce.domain.dtos.OrderResponse;
import com.khoatrbl.ecommerce.domain.entities.Orders;
import com.khoatrbl.ecommerce.domain.entities.OrderStatus;
import com.khoatrbl.ecommerce.mappers.OrderMapper;
import com.khoatrbl.ecommerce.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders(
            @RequestAttribute("userId") UUID userId,
            @RequestParam(required = false) OrderStatus status) {

        List<Orders> orders = orderService.getAllOrders(userId, status);
        List<OrderResponse> orderResponses = orders.stream().map(orderMapper::toOrderResponse).toList();

        return ResponseEntity.ok(orderResponses);
    }
}
