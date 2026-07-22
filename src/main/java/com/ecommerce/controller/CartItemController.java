package com.ecommerce.controller;

import java.util.Collection;
import java.util.Objects;

import com.ecommerce.model.CartItem;
import com.ecommerce.service.CartItemService;

public class CartItemController {

    private final CartItemService cartItemService;

    // Constructor Injection
    public CartItemController(CartItemService cartItemService) {

        this.cartItemService = Objects.requireNonNull(
                cartItemService,
                "CartItemService cannot be null"
        );
    }

    // Add Cart Item
    public boolean addCartItem(CartItem cartItem) {

        return cartItemService.addCartItem(cartItem);
    }

    // Update Cart Item
    public boolean updateCartItem(CartItem cartItem) {

        return cartItemService.updateCartItem(cartItem);
    }

    // Delete Cart Item
    public boolean deleteCartItem(int cartItemId) {

        return cartItemService.deleteCartItem(cartItemId);
    }

    // Get Cart Item By ID
    public CartItem getCartItemById(int cartItemId) {

        return cartItemService.getCartItemById(cartItemId);
    }

    // Get Cart Items By Cart ID
    public Collection<CartItem> getCartItemsByCartId(int cartId) {

        return cartItemService.getCartItemsByCartId(cartId);
    }

    // View Cart Items (for CartView)
    public Collection<CartItem> viewCartItems(int cartId) {

        return cartItemService.getCartItemsByCartId(cartId);
    }

    // View All Cart Items
    public Collection<CartItem> viewAllCartItems() {

        return cartItemService.getAllCartItems();
    }

    // Delete Cart Item
    public boolean deleteCartItemsByCartId(int cartId){
         
        return cartItemService.deleteCartItemsByCartId(cartId);
    }
}