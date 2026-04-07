package com.khoatrbl.ecommerce.controllers;

import com.khoatrbl.ecommerce.domain.entities.OrderStatus;
import com.khoatrbl.ecommerce.repositories.OrderRepository;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/webhooks")
@RequiredArgsConstructor
@Slf4j
public class StripeWebHookController {

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    private final OrderRepository orderRepository;

    @PostMapping(path = "/stripe")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload,
                                                      @RequestHeader("Stripe-Signature") String sigHeader) {

        Event event;

        try {
            // SECURITY: Verify the request actually came from Stripe
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch(SignatureVerificationException e) {
            log.error("Invalid Stripe signature: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }

        // We only care if the checkout was successful
        if ("checkout.session.completed".equals(event.getType())) {

            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            if (dataObjectDeserializer.getObject().isPresent()) {
                Session session = (Session) dataObjectDeserializer.getObject().get();

                // 1. Extract the order_id we passed in Step 4
                String orderIdStr = session.getMetadata().get("order_id");

                if (orderIdStr != null) {
                    UUID orderId = UUID.fromString(orderIdStr);

                    // 2. Find the Order and update the status
                    orderRepository.findById(orderId).ifPresent(order -> {
                        order.setStatus(OrderStatus.PAID);
                        orderRepository.save(order);
                        log.info("Successfully processed payment for Order: {}", orderId);
                    });
                }
            }
        }

        // Always return 200 OK so Stripe knows we received the webhook
        return ResponseEntity.ok().build();
    }
}
