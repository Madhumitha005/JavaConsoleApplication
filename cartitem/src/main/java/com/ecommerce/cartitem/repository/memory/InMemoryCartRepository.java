/*
 * InMemoryCartRepository.java
 *
 * Version 1.6
 *
 * July 28, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.

package com.ecommerce.cartitem.repository.memory;

import com.ecommerce.cartitem.entity.Cart;
import com.ecommerce.cartitem.repository.CartRepository;
import com.ecommerce.common.util.IdGenerator;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * In-memory implementation of CartRepository.
 *
 * This repository manages Cart objects using an ArrayList collection.
 * The data is stored temporarily in application memory without using
 * any external database.
 *
 * Provides CRUD operations and user-based cart retrieval operations.

@Repository("inMemoryCartRepository")
public class InMemoryCartRepository implements CartRepository {

    // Stores cart objects in memory during application execution
    private final List<Cart> carts = new ArrayList<>();

    /**
     * Saves a new cart into the in-memory collection.
     *
     * @param cart cart object to be saved
     * @return true if cart is successfully saved, otherwise false

    @Override
    public boolean save(final Cart cart) {

        if (cart == null) {
            return false;
        }

        cart.setCartId(
                IdGenerator.getInstance().nextCartId()
        );

        return carts.add(cart);
    }

    /**
     * Updates an existing cart using cart id.
     *
     * @param cart updated cart object
     * @return true if cart is updated successfully, otherwise false

    @Override
    public boolean update(final Cart cart) {

        if (cart == null) {
            return false;
        }

        for (int i = 0; i < carts.size(); i++) {

            if (carts.get(i).getCartId() == cart.getCartId()) {

                carts.set(i, cart);

                return true;
            }
        }

        return false;
    }

    /**
     * Deletes a cart using cart id.
     *
     * @param cartId unique identifier of cart
     * @return true if cart is deleted, otherwise false

    @Override
    public boolean delete(final int cartId) {

        return carts.removeIf(
                cart -> cart.getCartId() == cartId
        );
    }

    /**
     * Finds a cart using cart id.
     *
     * @param cartId unique identifier of cart
     * @return matching Cart object, otherwise null

    @Override
    public Cart findById(final int cartId) {

        for (final Cart cart : carts) {

            if (cart.getCartId() == cartId) {

                return cart;
            }
        }

        return null;
    }

    /**
     * Finds a cart associated with a user.
     *
     * @param userId unique identifier of user
     * @return user's cart object, otherwise null

    @Override
    public Cart findByUserId(final int userId) {

        for (final Cart cart : carts) {

            if (cart.getUserId() == userId) {

                return cart;
            }
        }

        return null;
    }

    /**
     * Retrieves all carts stored in memory.
     *
     * @return collection containing all cart objects

    @Override
    public Collection<Cart> findAll() {

        return new ArrayList<>(carts);
    }

    /**
     * Retrieves all carts belonging to a specific user.
     *
     * @param userId unique identifier of user
     * @return collection of carts associated with user

    @Override
    public Collection<Cart> findByUserIdList(final int userId) {

        Collection<Cart> result = new ArrayList<>();

        for (final Cart cart : carts) {

            if (cart.getUserId() == userId) {

                result.add(cart);
            }
        }

        return result;
    }
}
 */
