/*
 * CartItemService.java
 *
 * Version 1.6
 *
 * July 26, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.service;

import com.ecommerce.model.CartItem;
import com.ecommerce.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Service class responsible for cart item management operations.
 *
 * Handles saving, updating, deleting, and retrieving cart items.
 *
 * This service maintains data consistency between in-memory and JDBC
 * repository implementations.
 */
@Service
public class CartItemService {

    private final CartItemRepository memoryRepository;
    private final CartItemRepository jdbcRepository;

    /**
     * Creates CartItemService with required repository dependencies.
     *
     * @param memoryRepository in-memory cart item repository
     * @param jdbcRepository JDBC cart item repository
     */
    public CartItemService(

            @Qualifier("inMemoryCartItemRepository")
            final CartItemRepository memoryRepository,

            @Qualifier("jdbcCartItemRepository")
            final CartItemRepository jdbcRepository) {

        this.memoryRepository = memoryRepository;
        this.jdbcRepository = jdbcRepository;
    }

    /**
     * Saves a new cart item.
     *
     * Validates cart item details and stores the item in both
     * memory and JDBC repositories.
     *
     * @param cartItem cart item object to be saved
     * @return true if cart item is saved successfully in both repositories,
     * otherwise false
     */
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

    /**
     * Updates an existing cart item.
     *
     * @param cartItem updated cart item object
     * @return true if cart item is updated successfully in both repositories,
     * otherwise false
     */
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

    /**
     * Deletes a cart item using cart item id.
     *
     * @param cartItemId unique identifier of cart item
     * @return true if cart item is deleted successfully in both repositories,
     * otherwise false
     */
    public boolean delete(final int cartItemId) {

        if (cartItemId <= 0) {
            return false;
        }

        boolean memoryDeleted = memoryRepository.delete(cartItemId);
        boolean jdbcDeleted = jdbcRepository.delete(cartItemId);

        return memoryDeleted && jdbcDeleted;
    }

    /**
     * Deletes all cart items associated with a cart id.
     *
     * @param cartId unique identifier of cart
     * @return true if cart items are deleted successfully in both repositories,
     * otherwise false
     */
    public boolean deleteByCartId(final int cartId) {

        if (cartId <= 0) {
            return false;
        }

        boolean memoryDeleted = memoryRepository.deleteByCartId(cartId);
        boolean jdbcDeleted = jdbcRepository.deleteByCartId(cartId);

        return memoryDeleted && jdbcDeleted;
    }

    /**
     * Finds a cart item using cart item id.
     *
     * Searches memory repository first and JDBC repository if not found.
     *
     * @param cartItemId unique identifier of cart item
     * @return matching CartItem object, otherwise null
     */
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

    /**
     * Retrieves cart items using cart id.
     *
     * Combines results from memory and JDBC repositories
     * while avoiding duplicate cart items.
     *
     * @param cartId unique identifier of cart
     * @return collection of cart items
     */
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

    /**
     * Retrieves all cart items.
     *
     * Combines cart items from memory and JDBC repositories.
     *
     * @return collection containing all cart items
     */
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