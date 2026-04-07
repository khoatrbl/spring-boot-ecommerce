package com.khoatrbl.ecommerce.controllers;

import com.khoatrbl.ecommerce.domain.dtos.CheckOutUrlResponse;
import com.khoatrbl.ecommerce.domain.entities.Orders;
import com.khoatrbl.ecommerce.services.OrderService;
import com.khoatrbl.ecommerce.services.PaymentService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final OrderService orderService;

    @PostMapping(path="/checkout")
    public ResponseEntity<CheckOutUrlResponse> checkOut(@RequestParam UUID orderId) throws StripeException {
        Orders order = orderService.getOrderByOrderId(orderId);

        String checkOutUrl = paymentService.createCheckoutSession(order);

        CheckOutUrlResponse checkOutUrlResponse = CheckOutUrlResponse.builder()
                .checkOutUrl(checkOutUrl)
                .build();

        return ResponseEntity.ok(checkOutUrlResponse);
    }

    // 2. The local testing success page
    @GetMapping("/success")
    public String paymentSuccess(@RequestParam("session_id") String sessionId) {
        return "Payment was successful! Stripe Session ID: " + sessionId + "\n\nYou can safely close this window and check your database.";
    }

    // 3. The local testing cancel page
    @GetMapping("/cancel")
    public String paymentCancel() {
        return "Payment was cancelled. You have not been charged.";
    }
}
