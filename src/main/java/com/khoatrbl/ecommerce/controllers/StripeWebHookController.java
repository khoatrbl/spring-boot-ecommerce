package com.khoatrbl.ecommerce.controllers;

import com.khoatrbl.ecommerce.domain.entities.OrderStatus;
import com.khoatrbl.ecommerce.repositories.OrderRepository;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.checkout.Session;
import com.stripe.net.ApiResource;
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

        log.info("Received Stripe event: id={}, type={}, apiVersion={}",
                event.getId(), event.getType(), event.getApiVersion());

        if (!"checkout.session.completed".equals(event.getType())) {
            return ResponseEntity.ok("Ignored event type: " + event.getType());
        }

        EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();
        Session session = null;

        if (deserializer.getObject().isPresent()) {
            session = (Session) deserializer.getObject().get();
            log.info("Session deserialized safely");
        } else {
            // Fallback when safe deserialization fails (version mismatch, etc.)
            try {
                session = (Session) deserializer.deserializeUnsafe();
                log.warn("Session deserialized UNSAFELY for event {}", event.getId());
            } catch (Exception ex) {
                log.error("Could not deserialize checkout session. eventId={}", event.getId(), ex);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid checkout session payload");
            }
        }

        if (session == null) {
            log.error("Session is null after deserialization. eventId={}", event.getId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Session missing");
        }

        String orderIdStr = null;

        if (session.getMetadata() != null) {
            orderIdStr = session.getMetadata().get("order_id");
        }

        log.info("checkout.session.completed metadata order_id={}", orderIdStr);

        if (orderIdStr == null || orderIdStr.isBlank()) {
            log.warn("Missing order_id in session metadata. eventId={}, sessionId={}", event.getId(), session.getId());
            return ResponseEntity.ok("Missing order_id metadata");
        }

        final UUID orderId;
        try {
            orderId = UUID.fromString(orderIdStr);
        } catch (IllegalArgumentException ex) {
            log.warn("Invalid UUID in order_id metadata: {}. eventId={}", orderIdStr, event.getId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid order_id metadata");
        }

        return orderRepository.findById(orderId)
                .map(order -> {
                    order.setStatus(OrderStatus.PAID);
                    orderRepository.save(order);
                    log.info("Order marked PAID: orderId={}, eventId={}", orderId, event.getId());
                    return ResponseEntity.ok("Processed");
                })
                .orElseGet(() -> {
                    log.warn("Order not found for order_id={}. eventId={}", orderId, event.getId());
                    return ResponseEntity.ok("Order not found, ignored");
                });
    }
}
