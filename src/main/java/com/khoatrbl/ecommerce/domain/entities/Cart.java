package com.khoatrbl.ecommerce.domain.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@RedisHash("cart")
public class Cart implements Serializable {
    // Use userId as the cartId
    @Id
    private UUID userId;

    // Map<ProductID, CartItem>
    private Map<UUID, CartItem> items = new HashMap<>();

    @TimeToLive(unit = TimeUnit.HOURS)
    private Long timeToLive = 24L;

    // Set at Service
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
