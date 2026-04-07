package com.khoatrbl.ecommerce.services;

import com.khoatrbl.ecommerce.domain.entities.Orders;
import com.khoatrbl.ecommerce.domain.entities.Payment;
import com.stripe.exception.StripeException;

import java.util.UUID;

public interface PaymentService {
    Payment addPayment(UUID orderId, UUID userId);
    String createCheckoutSession(Orders order) throws StripeException;
}
