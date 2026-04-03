package com.khoatrbl.ecommerce.services.impl;

import com.khoatrbl.ecommerce.domain.dtos.AddCartItemRequest;
import com.khoatrbl.ecommerce.domain.dtos.UpdateCartItemRequest;
import com.khoatrbl.ecommerce.domain.entities.Cart;
import com.khoatrbl.ecommerce.domain.entities.CartItem;
import com.khoatrbl.ecommerce.domain.entities.Product;
import com.khoatrbl.ecommerce.repositories.CartRepository;
import com.khoatrbl.ecommerce.repositories.ProductRepository;
import com.khoatrbl.ecommerce.services.CartService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Override
    public Cart getCart(UUID userId) {
        return cartRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Cart does not exist with id: " + userId));
    }

    @Override
    public Cart addItem(UUID userId, AddCartItemRequest request) {
        LocalDateTime now = LocalDateTime.now();

        UUID productToAddId = request.getProductId();
        int quantity = request.getQuantity();

        Product productToAdd = productRepository.findById(productToAddId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Product does not exist with id: " + productToAddId)
                );

        CartItem cartItem = CartItem.builder()
                .productId(productToAddId)
                .productName(productToAdd.getProductName())
                .price(productToAdd.getPrice())
                .quantity(quantity)
                .build();

        Cart existingCart = cartRepository.findById(userId)
                .orElseGet(() -> Cart.builder()
                        .userId(userId)
                        .items(new HashMap<>())
                        .createdAt(now)
                        .updatedAt(now)
                        .build());

        existingCart.addCartItem(productToAddId, cartItem);

        return cartRepository.save(existingCart);
    }

    @Override
    public Cart updateQuantityOfItem(UUID userId, UUID productId, UpdateCartItemRequest request) {
        LocalDateTime now = LocalDateTime.now();
        int quantity = request.getQuantity();

        Cart existingCart = cartRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Cart does not exist with id: " + userId));

        // if the update quantity = 0
        // this means the user want to delete the item from the cart
        if (quantity == 0) {
            deleteItem(userId, productId);
            return existingCart;
        }

        // Get the CartItem with the productId as requested
        // Update the quantity of that CartItem as requested
        existingCart.updateCartItem(productId, quantity);
        existingCart.setUpdatedAt(now);

        return cartRepository.save(existingCart);
    }

    @Override
    public void deleteItem(UUID userId, UUID productId) {
        LocalDateTime now = LocalDateTime.now();

        Cart existingCart = cartRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Cart does not exist with id: " + userId));

        Map<UUID, CartItem> currentItems = existingCart.getItems();
        currentItems.remove(productId);

        existingCart.setUpdatedAt(now);

        cartRepository.save(existingCart);
    }


}
