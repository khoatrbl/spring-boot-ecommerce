package com.khoatrbl.ecommerce.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, name = "user_id")
    private User user;

    // The relationship: One Order -> Many OrderItems
    // cascade = CascadeType.ALL means if we save the Order, it saves the items automatically
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Map<UUID, OrderItem> items = new HashMap<>();

    @Column(nullable = false)
    private BigDecimal total;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(nullable = false)
    private String recipientName;

    @Column(nullable = false)
    private String shippingAddress;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();

        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return Objects.equals(orderId, orders.orderId) && Objects.equals(user, orders.user) && Objects.equals(total, orders.total) && status == orders.status && Objects.equals(shippingAddress, orders.shippingAddress) && Objects.equals(createdAt, orders.createdAt) && Objects.equals(updatedAt, orders.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, user, total, status, shippingAddress, createdAt, updatedAt);
    }

    public void addOrderItem(UUID productId, OrderItem item) {
        this.items.put(productId, item);
    }
}
