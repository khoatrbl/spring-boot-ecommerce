package com.khoatrbl.ecommerce.controllers;

import com.khoatrbl.ecommerce.domain.dtos.*;
import com.khoatrbl.ecommerce.domain.entities.Cart;
import com.khoatrbl.ecommerce.mappers.CartMapper;
import com.khoatrbl.ecommerce.services.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartMapper cartMapper;


    @GetMapping
    public ResponseEntity<CartResponse> getCart(@RequestAttribute("userId")UUID userId) {
        Cart cart = cartService.getCart(userId);

        CartResponse cartResponse = cartMapper.toCartResponse(cart);

        return ResponseEntity.ok(cartResponse);
    }

    @PostMapping
    public ResponseEntity<CartResponse> addProductToCart(
            @RequestAttribute("userId")UUID userId,
            @Valid @RequestBody AddCartItemRequestDto addCartItemRequestDto) {

        AddCartItemRequest request = cartMapper.toAddCartItemRequest(addCartItemRequestDto);
        Cart cart = cartService.addItem(userId, request);

        CartResponse cartResponse = cartMapper.toCartResponse(cart);

        return new ResponseEntity<>(cartResponse, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<CartResponse> updateCartItem(
            @RequestAttribute("userId") UUID userId,
            @PathVariable("id") UUID productId,
            @Valid @RequestBody UpdateCartItemRequestDto updateCartItemRequestDto) {

        UpdateCartItemRequest request = cartMapper.toUpdateCartItemRequest(updateCartItemRequestDto);

        Cart cart = cartService.updateQuantityOfItem(userId, productId, request);

        CartResponse cartResponse = cartMapper.toCartResponse(cart);

        return ResponseEntity.ok(cartResponse);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteCartItem(
            @RequestAttribute("userId") UUID userId,
            @PathVariable("id") UUID productId) {

        cartService.deleteItem(userId, productId);

        return ResponseEntity.noContent().build();
    }

}
