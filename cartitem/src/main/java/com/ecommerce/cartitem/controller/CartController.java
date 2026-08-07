/*
 * CartController.java
 *
 * Version 1.5
 *
 * July 26, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.

package com.ecommerce.cartitem.controller;

import java.util.Collection;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.cartitem.entity.Cart;
import com.ecommerce.cartitem.service.CartService;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    // Constructor Injection
    public CartController(final CartService cartService) {

        this.cartService = Objects.requireNonNull(cartService, "CartService cannot be null");
    }

    // Save Cart
    @PostMapping
    public ResponseEntity<Boolean> save(@RequestBody final Cart cart) {

        Objects.requireNonNull(cart, "Cart cannot be null");

        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.save(cart));
    }

    // Find Cart By Id
    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> findById(@PathVariable final int cartId) {

        return ResponseEntity.ok(cartService.findById(cartId));
    }

    // Find Cart By User
    @GetMapping("/user/{userId}")
    public ResponseEntity<Cart> findByUserId(@PathVariable final int userId) {

        return ResponseEntity.ok(cartService.findByUserId(userId));
    }

    // View User Cart List
    @GetMapping("/user/{userId}/list")
    public ResponseEntity<Collection<Cart>> findByUserIdList(@PathVariable final int userId) {

        return ResponseEntity.ok(cartService.findByUserIdList(userId));
    }

    // View All Carts
    @GetMapping
    public ResponseEntity<Collection<Cart>> findAll() {

        return ResponseEntity.ok(cartService.findAll());
    }

    // Update Cart
    @PutMapping("/{cartId}")
    public ResponseEntity<Boolean> update(@PathVariable final int cartId, @RequestBody final Cart cart) {

        Objects.requireNonNull(cart, "Cart cannot be null");
        cart.setCartId(cartId);

        return ResponseEntity.ok(cartService.update(cart));
    }

    // Delete Cart
    @DeleteMapping("/{cartId}")
    public ResponseEntity<Boolean> delete(@PathVariable final int cartId) {

        return ResponseEntity.ok(cartService.delete(cartId));
    }
}
 */
