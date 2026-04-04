package com.khoatrbl.ecommerce.repositories;

import com.khoatrbl.ecommerce.domain.entities.Orders;
import com.khoatrbl.ecommerce.domain.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Orders, UUID> {
    List<Orders> findAllByUserIdAndStatus(UUID userId, OrderStatus status);
    List<Orders> findAllByUserId(UUID userId);
    Optional<Orders> findByUserIdAndOrderId(UUID userId, UUID orderId);
}
