package com.khoatrbl.ecommerce.services.impl;

import com.khoatrbl.ecommerce.domain.dtos.CreateOrderRequest;
import com.khoatrbl.ecommerce.domain.dtos.UpdateOrderStatusRequest;
import com.khoatrbl.ecommerce.domain.entities.*;
import com.khoatrbl.ecommerce.repositories.CartRepository;
import com.khoatrbl.ecommerce.repositories.OrderRepository;
import com.khoatrbl.ecommerce.repositories.UserRepository;
import com.khoatrbl.ecommerce.services.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    @Override
    public List<Orders> getAllOrders(UUID userId, OrderStatus status) {
        if (status != null) {
            return orderRepository.findAllByUserIdAndStatus(userId, status);
        }

        return orderRepository.findAllByUserId(userId);
    }

    @Override
    public Orders addCartToOrder(UUID userId, CreateOrderRequest request) {
        Cart cart = cartRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Cart does not exist with id: " + userId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User does not exist with id: " + userId));

        BigDecimal total = cart.calculateTotalAmount();

        Orders order = Orders.builder()
                .user(user)
                .items(new HashMap<>())
                .total(total)
                .recipientName(request.getRecipientName())
                .phoneNumber(request.getPhoneNumber())
                .shippingAddress(request.getShippingAddress())
                .status(OrderStatus.PENDING)
                .build();

        Map<UUID, CartItem> itemsInCart = cart.getItems();

        // Map cart to order
        for (UUID productId : itemsInCart.keySet()) {
            CartItem cartItem = itemsInCart.get(productId);

            OrderItem orderItem = OrderItem.builder()
                    .productId(productId)
                    .productNameAtPurchase(cartItem.getProductName())
                    .priceAtPurchase(cartItem.getPrice())
                    .quantity(cartItem.getQuantity())
                    .build();

            order.addOrderItem(productId, orderItem);
        }

        // After order is saved
        // Delete the cart
        cartRepository.deleteById(userId);

        return orderRepository.save(order);
    }

    @Override
    public Orders updateOrderStatus(UUID orderId, UpdateOrderStatusRequest request) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order does not exist with id: " + orderId));

        OrderStatus status = request.getStatus();
        order.setStatus(status);

        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(UUID orderId) {
        orderRepository.deleteById(orderId);
    }
}
