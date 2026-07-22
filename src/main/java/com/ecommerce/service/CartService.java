package com.ecommerce.service;

import java.util.Collection;
import java.util.Collections;

import com.ecommerce.exception.CartException;
import com.ecommerce.model.Cart;
import com.ecommerce.repository.CartRepository;

public class CartService {

    private final CartRepository memoryRepository;
    private final CartRepository jdbcRepository;

    // Constructor Injection
    public CartService(CartRepository memoryRepository,
                       CartRepository jdbcRepository) {

        this.memoryRepository = memoryRepository;
        this.jdbcRepository = jdbcRepository;
    }

    // Find Cart By User
public Cart findByUserId(int userId) {

    if (userId <= 0) {
        throw new CartException("Invalid User.");
    }

    Cart cart = memoryRepository.findByUserId(userId);

    if (cart == null) {
        cart = jdbcRepository.findByUserId(userId);
    }

    return cart;
}
    // Add To Cart
    public boolean addToCart(Cart cart) {

        if (cart == null) {
            throw new NullPointerException("Cart cannot be null.");
        }

        if (cart.getUserId() <= 0) {
            throw new CartException("Invalid User.");
        }

        boolean memorySaved = memoryRepository.addToCart(cart);
        boolean jdbcSaved = jdbcRepository.addToCart(cart);

        return memorySaved && jdbcSaved;
    }

    // View Cart
    public Collection<Cart> viewCart(int userId) {

        if (userId <= 0) {
            throw new CartException("Invalid User.");
        }

        Collection<Cart> carts = memoryRepository.getCartByUser(userId);

        if (carts == null || carts.isEmpty()) {
            carts = jdbcRepository.getCartByUser(userId);
        }

        return carts == null ? Collections.emptyList() : carts;
    }

    // Clear Cart
    public boolean clearCart(int userId) {

        Cart cart = findByUserId(userId);

        if(cart == null) {
            return false;
        }

        boolean memoryCleared = memoryRepository.clearCart(userId);
        boolean jdbcCleared = jdbcRepository.clearCart(userId);

        return memoryCleared && jdbcCleared;
    }
}