/*
 * CartService.java
 *
 * Version 1.6
 *
 * July 28, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.service;

import com.ecommerce.model.Cart;
import com.ecommerce.repository.CartRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

/**
 * Service class responsible for cart management operations.
 *
 * Handles cart creation, retrieval, updating, and deletion.
 *
 * This service maintains data consistency between in-memory and
 * JDBC repository implementations.
 */
@Service
public class CartService {

    private final CartRepository memoryRepository;
    private final CartRepository jdbcRepository;

    /**
     * Creates CartService with required repository dependencies.
     *
     * @param memoryRepository in-memory cart repository
     * @param jdbcRepository JDBC cart repository
     */
    public CartService(

            @Qualifier("inMemoryCartRepository")
            final CartRepository memoryRepository,

            @Qualifier("jdbcCartRepository")
            final CartRepository jdbcRepository) {

        this.memoryRepository = memoryRepository;
        this.jdbcRepository = jdbcRepository;
    }


    /**
     * Saves a new cart.
     *
     * Stores cart information in both memory and JDBC repositories.
     *
     * @param cart cart object to be saved
     * @return true if cart is saved successfully in both repositories,
     * otherwise false
     */
    public boolean save(final Cart cart) {

        if (cart == null) {

            return false;
        }

        boolean memorySaved = memoryRepository.save(cart);
        boolean jdbcSaved = jdbcRepository.save(cart);

        return memorySaved && jdbcSaved;
    }


    /**
     * Finds a cart using cart id.
     *
     * Searches memory repository first and JDBC repository
     * if cart is not available.
     *
     * @param cartId unique identifier of cart
     * @return matching Cart object, otherwise null
     */
    public Cart findById(final int cartId) {

        if (cartId <= 0) {

            return null;
        }

        Cart cart = memoryRepository.findById(cartId);

        if (cart == null) {
            cart = jdbcRepository.findById(cartId);
        }

        return cart;
    }

    /**
     * Finds a cart associated with a user.
     *
     * Searches memory repository first and JDBC repository
     * if cart is not available.
     *
     * @param userId unique identifier of user
     * @return user's Cart object, otherwise null
     */
    public Cart findByUserId(final int userId) {

        if(userId <= 0) {

            return null;
        }

        Cart cart = memoryRepository.findByUserId(userId);

        if (cart == null) {
            cart = jdbcRepository.findByUserId(userId);
        }

        return cart;
    }

    /**
     * Retrieves all carts belonging to a specific user.
     *
     * Returns empty collection when invalid user id is provided.
     *
     * @param userId unique identifier of user
     * @return collection of user carts
     */
    public Collection<Cart> findByUserIdList(final int userId) {

        if(userId <= 0) {

            return Collections.emptyList();
        }


        Collection<Cart> carts = memoryRepository.findByUserIdList(userId);

        if (carts == null || carts.isEmpty()) {
            carts = jdbcRepository.findByUserIdList(userId);
        }

        return carts;
    }

    /**
     * Retrieves all carts.
     *
     * Searches memory repository first and JDBC repository
     * when memory collection is empty.
     *
     * @return collection containing all carts
     */
    public Collection<Cart> findAll() {

        Collection<Cart> carts = memoryRepository.findAll();

        if (carts == null || carts.isEmpty()) {
            carts = jdbcRepository.findAll();
        }

        return carts;
    }

    /**
     * Updates an existing cart.
     *
     * Updates cart information in both repositories.
     *
     * @param cart updated cart object
     * @return true if cart is updated successfully in both repositories,
     * otherwise false
     */
    public boolean update(final Cart cart) {

        if(cart == null) {

            return false;
        }

        boolean memoryUpdated = memoryRepository.update(cart);
        boolean jdbcUpdated = jdbcRepository.update(cart);

        return memoryUpdated && jdbcUpdated;
    }

    /**
     * Deletes a cart using cart id.
     *
     * Deletes cart information from both repositories.
     *
     * @param cartId unique identifier of cart
     * @return true if cart is deleted successfully in both repositories,
     * otherwise false
     */
    public boolean delete(final int cartId) {

        if(cartId <= 0) {

            return false;
        }

        boolean memoryDeleted = memoryRepository.delete(cartId);
        boolean jdbcDeleted = jdbcRepository.delete(cartId);

        return memoryDeleted && jdbcDeleted;
    }
}