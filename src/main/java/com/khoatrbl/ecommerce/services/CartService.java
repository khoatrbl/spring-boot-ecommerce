package com.khoatrbl.ecommerce.services;

import com.khoatrbl.ecommerce.domain.dtos.AddCartItemRequest;
import com.khoatrbl.ecommerce.domain.dtos.UpdateCartItemRequest;
import com.khoatrbl.ecommerce.domain.entities.Cart;

import java.util.UUID;

public interface CartService {
    Cart getCart(UUID userId);
    Cart addItem(UUID userId, AddCartItemRequest request);
    Cart updateQuantityOfItem(UUID userId, UUID productId, UpdateCartItemRequest request);
    void deleteItem(UUID userId, UUID productId);

}
