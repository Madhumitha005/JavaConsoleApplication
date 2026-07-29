package com.ecommerce.controller;

import java.util.Collection;
import java.util.Objects;

import org.springframework.stereotype.Controller;

import com.ecommerce.model.CartItem;
import com.ecommerce.service.CartItemService;

@Controller
public class CartItemController {

    private final CartItemService cartItemService;

    // Constructor Injection
    public CartItemController(final CartItemService cartItemService) {

        this.cartItemService = Objects.requireNonNull(cartItemService, "CartItemService cannot be null");
    }

    // Save Cart Item
    public boolean save(final CartItem cartItem) {

        Objects.requireNonNull(cartItem, "CartItem cannot be null");

        return cartItemService.save(cartItem);
    }

    // Update Cart Item
    public boolean update(final CartItem cartItem) {

        Objects.requireNonNull(cartItem, "CartItem cannot be null");

        return cartItemService.update(cartItem);
    }

    // Delete Cart Item
    public boolean delete(final int cartItemId) {

        return cartItemService.delete(cartItemId);
    }

    // Find By Id
    public CartItem findById(final int cartItemId) {

        return cartItemService.findById(cartItemId);
    }

    // Find By Cart Id
    public Collection<CartItem> findByCartId(final int cartId) {

        return cartItemService.findByCartId(cartId);
    }

    // Find All
    public Collection<CartItem> findAll() {

        return cartItemService.findAll();
    }

    // Delete By Cart Id
    public boolean deleteByCartId(final int cartId) {

        return cartItemService.deleteByCartId(cartId);
    }
}