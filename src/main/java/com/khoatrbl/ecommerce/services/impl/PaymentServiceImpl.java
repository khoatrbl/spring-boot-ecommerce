package com.khoatrbl.ecommerce.services.impl;

import com.khoatrbl.ecommerce.domain.entities.OrderItem;
import com.khoatrbl.ecommerce.domain.entities.Orders;
import com.khoatrbl.ecommerce.domain.entities.Payment;
import com.khoatrbl.ecommerce.domain.entities.User;
import com.khoatrbl.ecommerce.repositories.OrderRepository;
import com.khoatrbl.ecommerce.repositories.PaymentRepository;
import com.khoatrbl.ecommerce.repositories.UserRepository;
import com.khoatrbl.ecommerce.services.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Value("${stripe.success-url}")
    private String successfulUrl;

    @Value("${stripe.cancel-url}")
    private String cancelUrl;

    @Override
    public Payment addPayment(UUID orderId, UUID userId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order does not exist with id: " + orderId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User does not exist with id: " + userId));

        return null;
    }

    @Override
    public String createCheckoutSession(Orders order) throws StripeException {

        // 1. Create an empty list to hold all the Stripe items
        List<SessionCreateParams.LineItem> stripeLineItems = new ArrayList<>();

        Map<UUID, OrderItem> items = order.getItems();

        for (UUID itemId : items.keySet()) {
            OrderItem item = items.get(itemId);
            long unitPriceOfItemInCents = item.getPriceAtPurchase().multiply(new BigDecimal("100")).longValue();

            SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                    .setName("Order: " + item.getProductNameAtPurchase())
                    .build();

            SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                    .setCurrency("usd")
                    .setUnitAmount(unitPriceOfItemInCents)
                    .setProductData(productData)
                    .build();

            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                    .setQuantity((long)item.getQuantity())
                    .setPriceData(priceData)
                    .build();

            stripeLineItems.add(lineItem);
        }

        // 3. Build the Session
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successfulUrl)
                .setCancelUrl(cancelUrl)
                // NOTICE THIS CHANGE: We use addAllLineItem instead of addLineItem
                .addAllLineItem(stripeLineItems)
                .putMetadata("order_id", order.getOrderId().toString())
                .build();

        Session session = Session.create(params);
        return session.getUrl();
    }
}
