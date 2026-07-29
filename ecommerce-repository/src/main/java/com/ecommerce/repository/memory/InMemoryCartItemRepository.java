/*
 * InMemoryCartItemRepository.java
 *
 * Version 1.6
 *
 * July 28, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.repository.memory;

import com.ecommerce.model.CartItem;
import com.ecommerce.repository.CartItemRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * In-memory implementation of CartItemRepository.
 *
 * This repository stores cart item data temporarily using
 * an ArrayList collection instead of a database.
 *
 * It provides CRUD operations for CartItem objects.
 */
@Repository("inMemoryCartItemRepository")
public class InMemoryCartItemRepository
        implements CartItemRepository {

    // Stores cart items in memory during application execution
    private final List<CartItem> cartItems = new ArrayList<>();

    /**
     * Saves a new cart item into the in-memory collection.
     *
     * @param cartItem cart item object to be saved
     * @return true if item is successfully added, otherwise false
     */
    @Override
    public boolean save(final CartItem cartItem) {

        if (cartItem == null) {
            return false;
        }

        cartItem.setCartItemId(cartItems.size() + 1);
        return cartItems.add(cartItem);
    }

    /**
     * Updates an existing cart item based on cart item id.
     *
     * @param cartItem updated cart item object
     * @return true if item is updated, otherwise false
     */
    @Override
    public boolean update(final CartItem cartItem) {

        if (cartItem == null) {
            return false;
        }

        for (int i = 0; i < cartItems.size(); i++) {

            if (cartItems.get(i).getCartItemId() == cartItem.getCartItemId()) {

                cartItems.set(i, cartItem);
                return true;
            }
        }

        return false;
    }

    /**
     * Deletes a cart item using cart item id.
     *
     * @param cartItemId unique identifier of cart item
     * @return true if item is removed, otherwise false
     */
    @Override
    public boolean delete(final int cartItemId) {

        return cartItems.removeIf(
                item -> item.getCartItemId() == cartItemId
        );
    }

    /**
     * Deletes all cart items associated with a cart id.
     *
     * @param cartId unique identifier of cart
     * @return true if matching items are removed, otherwise false
     */
    @Override
    public boolean deleteByCartId(final int cartId) {

        return cartItems.removeIf(
                item -> item.getCartId() == cartId
        );
    }

    /**
     * Finds a cart item using cart item id.
     *
     * @param cartItemId unique identifier of cart item
     * @return matching CartItem object, otherwise null
     */
    @Override
    public CartItem findById(final int cartItemId) {

        for (final CartItem item : cartItems) {

            if (item.getCartItemId() == cartItemId) {
                return item;
            }
        }

        return null;
    }

    /**
     * Retrieves all cart items belonging to a specific cart.
     *
     * @param cartId unique identifier of cart
     * @return collection of cart items
     */
    @Override
    public Collection<CartItem> findByCartId(final int cartId) {

        final Collection<CartItem> items = new ArrayList<>();

        for (final CartItem item : cartItems) {

            if (item.getCartId() == cartId) {
                items.add(item);
            }
        }

        return items;
    }

    /**
     * Retrieves all cart items stored in memory.
     *
     * @return collection containing all cart items
     */
    @Override
    public Collection<CartItem> findAll() {

        return new ArrayList<>(cartItems);
    }
}