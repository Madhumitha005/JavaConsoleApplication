package com.ecommerce.service;

import java.util.Collection;
import java.util.Collections;

import com.ecommerce.model.CartItem;
import com.ecommerce.repository.CartItemRepository;

public class CartItemService {

    private final CartItemRepository memoryRepository;
    private final CartItemRepository jdbcRepository;

    // Constructor Injection
    public CartItemService(CartItemRepository memoryRepository,
                           CartItemRepository jdbcRepository) {

        this.memoryRepository = memoryRepository;
        this.jdbcRepository = jdbcRepository;
    }

    // Add Cart Item
    public boolean addCartItem(CartItem cartItem) {

        if (cartItem == null) {
            return false;
        }

        if (cartItem.getCartId() <= 0 ||
                cartItem.getProductId() <= 0 ||
                cartItem.getQuantity() <= 0) {

            return false;
        }

        boolean memorySaved = memoryRepository.addCartItem(cartItem);
        boolean jdbcSaved = jdbcRepository.addCartItem(cartItem);

        return memorySaved && jdbcSaved;
    }

    // Update Cart Item
    public boolean updateCartItem(CartItem cartItem) {

        if (cartItem == null) {
            return false;
        }

        if (cartItem.getCartItemId() <= 0 ||
                cartItem.getQuantity() <= 0) {

            return false;
        }

        boolean memoryUpdated = memoryRepository.updateCartItem(cartItem);
        boolean jdbcUpdated = jdbcRepository.updateCartItem(cartItem);

        return memoryUpdated && jdbcUpdated;
    }

    // Delete Cart Item
    public boolean deleteCartItem(int cartItemId) {

        if (cartItemId <= 0) {
            return false;
        }

        boolean memoryDeleted = memoryRepository.deleteCartItem(cartItemId);
        boolean jdbcDeleted = jdbcRepository.deleteCartItem(cartItemId);

        return memoryDeleted && jdbcDeleted;
    }

    // Get Cart Item By ID
    public CartItem getCartItemById(int cartItemId) {

        if (cartItemId <= 0) {
            return null;
        }

        CartItem item = memoryRepository.findById(cartItemId);

        if (item == null) {
            item = jdbcRepository.findById(cartItemId);
        }

        return item;
    }

    // Get Cart Items By Cart ID
    public Collection<CartItem> getCartItemsByCartId(int cartId) {

        if (cartId <= 0) {
            return Collections.emptyList();
        }

        Collection<CartItem> items =
                memoryRepository.findByCartId(cartId);

        if (items == null || items.isEmpty()) {
            items = jdbcRepository.findByCartId(cartId);
        }

        return items == null ? Collections.emptyList() : items;
    }

    // View All Cart Items
    public Collection<CartItem> getAllCartItems() {

        Collection<CartItem> items =
                memoryRepository.findAll();

        if (items == null || items.isEmpty()) {
            items = jdbcRepository.findAll();
        }

        return items == null ? Collections.emptyList() : items;
    }

    public boolean deleteCartItemsByCartId(int cartId){

        boolean memoryDeleted =
                memoryRepository.deleteByCartId(cartId);

        boolean jdbcDeleted =
                jdbcRepository.deleteByCartId(cartId);

        return memoryDeleted && jdbcDeleted;
    }
}