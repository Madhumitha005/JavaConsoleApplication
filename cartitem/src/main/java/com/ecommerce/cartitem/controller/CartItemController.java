/*
 * CartItemController.java
 *
 * Version 2.0
 *
 * August 04, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.cartitem.controller;

import java.util.Collection;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecommerce.cartitem.entity.CartItem;
import com.ecommerce.cartitem.service.CartItemService;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(final CartItemService cartItemService) {

        this.cartItemService = Objects.requireNonNull(cartItemService, "CartItemService cannot be null.");
    }

    @PostMapping
    public ResponseEntity<Boolean> save(
            @RequestBody final CartItem cartItem) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemService.save(cartItem));
    }

    @PutMapping
    public ResponseEntity<Boolean> update(
            @RequestBody final CartItem cartItem) {
        return ResponseEntity.ok(cartItemService.update(cartItem));
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Boolean> delete(
            @PathVariable final Integer cartItemId) {
        return ResponseEntity.ok(cartItemService.delete(cartItemId));
    }

    @GetMapping("/{cartItemId}")
    public ResponseEntity<CartItem> findById(
            @PathVariable final Integer cartItemId) {
        return ResponseEntity.ok(cartItemService.findById(cartItemId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Collection<CartItem>> findByUserId(
            @PathVariable final Integer userId) {
        return ResponseEntity.ok(cartItemService.findByUserId(userId));
    }

    @GetMapping
    public ResponseEntity<Collection<CartItem>> findAll() {
        return ResponseEntity.ok(cartItemService.findAll());
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Boolean> deleteByUserId(
            @PathVariable final Integer userId) {
        return ResponseEntity.ok(cartItemService.deleteByUserId(userId));
    }
}