package com.ecommerce.controller;

import java.util.Collection;

import com.ecommerce.model.Cart;
import com.ecommerce.service.CartService;

public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    public boolean addToCart(Cart cart) {
        return cartService.addToCart(cart);
    }

    public Cart findByUserId(int userId) {
    return cartService.findByUserId(userId);
}

    public Collection<Cart> viewCart(int userId) {
        return cartService.viewCart(userId);
    }

    public boolean clearCart(int userId) {
        return cartService.clearCart(userId);
    }
}