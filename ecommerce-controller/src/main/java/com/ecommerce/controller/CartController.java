package com.ecommerce.controller;

import java.util.Collection;
import java.util.Objects;

import org.springframework.stereotype.Controller;

import com.ecommerce.model.Cart;
import com.ecommerce.service.CartService;

@Controller
public class CartController {

    private final CartService cartService;

    // Constructor Injection
    public CartController(final CartService cartService) {

        this.cartService = Objects.requireNonNull(cartService, "CartService cannot be null");
    }

    // Save Cart
    public boolean save(final Cart cart) {

        Objects.requireNonNull(cart, "Cart cannot be null");

        return cartService.save(cart);
    }

    // Find Cart By User
    public Cart findByUserId(final int userId) {

        return cartService.findByUserId(userId);
    }

    // View User Cart
    public Collection<Cart> findByUserIdList(final int userId) {

        return cartService.findByUserIdList(userId);
    }

    // Update Cart
    public boolean update(final Cart cart) {

        Objects.requireNonNull(cart, "Cart cannot be null");

        return cartService.update(cart);
    }

    // Delete Cart
    public boolean delete(final int cartId) {

        return cartService.delete(cartId);
    }
}