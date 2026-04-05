package com.khoatrbl.ecommerce.services;

import com.khoatrbl.ecommerce.domain.entities.Payment;

import java.util.UUID;

public interface PaymentService {
    Payment addPayment(UUID orderId, UUID userId);
}
