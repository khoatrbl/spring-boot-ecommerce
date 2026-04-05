package com.khoatrbl.ecommerce.services.impl;

import com.khoatrbl.ecommerce.domain.entities.Orders;
import com.khoatrbl.ecommerce.domain.entities.Payment;
import com.khoatrbl.ecommerce.domain.entities.User;
import com.khoatrbl.ecommerce.repositories.OrderRepository;
import com.khoatrbl.ecommerce.repositories.PaymentRepository;
import com.khoatrbl.ecommerce.repositories.UserRepository;
import com.khoatrbl.ecommerce.services.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public Payment addPayment(UUID orderId, UUID userId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order does not exist with id: " + orderId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User does not exist with id: " + userId));


    }
}
