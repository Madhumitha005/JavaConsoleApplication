package com.ecommerce.service;

import com.ecommerce.model.CartItem;
import com.ecommerce.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class CartItemService {

    private final CartItemRepository memoryRepository;
    private final CartItemRepository jdbcRepository;

    // Constructor Injection
    public CartItemService(

            @Qualifier("inMemoryCartItemRepository")
            final CartItemRepository memoryRepository,

            @Qualifier("jdbcCartItemRepository")
            final CartItemRepository jdbcRepository) {

        this.memoryRepository = memoryRepository;
        this.jdbcRepository = jdbcRepository;
    }

    // Save Cart Item
    public boolean save(final CartItem cartItem) {

        if (cartItem == null
                || cartItem.getCartId() <= 0
                || cartItem.getProductId() <= 0
                || cartItem.getQuantity() <= 0) {

            return false;
        }

        boolean memorySaved = memoryRepository.save(cartItem);
        boolean jdbcSaved = jdbcRepository.save(cartItem);

        return memorySaved && jdbcSaved;
    }

    // Update Cart Item
    public boolean update(final CartItem cartItem) {

        if (cartItem == null
                || cartItem.getCartItemId() <= 0
                || cartItem.getQuantity() <= 0) {

            return false;
        }

        boolean memoryUpdated = memoryRepository.update(cartItem);
        boolean jdbcUpdated = jdbcRepository.update(cartItem);

        return memoryUpdated && jdbcUpdated;
    }

    // Delete Cart Item
    public boolean delete(final int cartItemId) {

        if (cartItemId <= 0) {
            return false;
        }

        boolean memoryDeleted = memoryRepository.delete(cartItemId);
        boolean jdbcDeleted = jdbcRepository.delete(cartItemId);

        return memoryDeleted && jdbcDeleted;
    }

    // Delete By Cart ID
    public boolean deleteByCartId(final int cartId) {

        if (cartId <= 0) {
            return false;
        }

        boolean memoryDeleted = memoryRepository.deleteByCartId(cartId);
        boolean jdbcDeleted = jdbcRepository.deleteByCartId(cartId);

        return memoryDeleted && jdbcDeleted;
    }

    // Find By ID
    public CartItem findById(final int cartItemId) {

        if (cartItemId <= 0) {
            return null;
        }

        CartItem cartItem = memoryRepository.findById(cartItemId);

        if (cartItem == null) {
            cartItem = jdbcRepository.findById(cartItemId);
        }

        return cartItem;
    }

    // Find By Cart ID
    public Collection<CartItem> findByCartId(final int cartId) {

        if (cartId <= 0) {
            return Collections.emptyList();
        }

        Map<Integer, CartItem> cartItems = new LinkedHashMap<>();

        Collection<CartItem> memoryItems = memoryRepository.findByCartId(cartId);
        Collection<CartItem> jdbcItems = jdbcRepository.findByCartId(cartId);

        if (memoryItems != null) {

            for (CartItem item : memoryItems) {
                cartItems.put(item.getProductId(), item);
            }
        }

        if (jdbcItems != null) {

            for (CartItem item : jdbcItems) {
                cartItems.put(item.getCartItemId(), item);
            }
        }

        return cartItems.values();
    }

    // Find All
    public Collection<CartItem> findAll() {

        Map<Integer, CartItem> cartItems = new LinkedHashMap<>();

        Collection<CartItem> memoryItems = memoryRepository.findAll();
        Collection<CartItem> jdbcItems = jdbcRepository.findAll();

        if (memoryItems != null) {

            for (CartItem item : memoryItems) {
                cartItems.put(item.getCartItemId(), item);
            }
        }

        if (jdbcItems != null) {

            for (CartItem item : jdbcItems) {
                cartItems.put(item.getCartItemId(), item);
            }
        }

        return cartItems.values();
    }
}