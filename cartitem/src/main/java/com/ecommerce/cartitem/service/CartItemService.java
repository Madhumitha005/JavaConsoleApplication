/*
 * CartItemService.java
 *
 * Version 1.6
 *
 * July 26, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.cartitem.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ecommerce.cartitem.entity.CartItem;
import com.ecommerce.cartitem.repository.CartItemRepository;

@Service
public class CartItemService {

    private final CartItemRepository repository;

    public CartItemService(final CartItemRepository repository) {

        this.repository = repository;
    }

    @CacheEvict(
            cacheNames = {
                    "cartItems",
                    "userCartItems",
                    "allCartItems"
            },
            allEntries = true
    )
    public boolean save(final CartItem cartItem) {

        if (cartItem == null
                || cartItem.getUserId() == null
                || cartItem.getUserId() <= 0
                || cartItem.getProductId() == null
                || cartItem.getProductId() <= 0
                || cartItem.getQuantity() == null
                || cartItem.getQuantity() <= 0) {

            return false;
        }
        return repository.save(cartItem);
    }

    @CacheEvict(
            cacheNames = {
                    "cartItems",
                    "userCartItems",
                    "allCartItems"
            },
            allEntries = true
    )
    public boolean update(final CartItem cartItem) {

        if (cartItem == null
                || cartItem.getCartItemId() == null
                || cartItem.getCartItemId() <= 0
                || cartItem.getQuantity() == null
                || cartItem.getQuantity() <= 0) {

            return false;
        }
        return repository.update(cartItem);
    }

    @CacheEvict(
            cacheNames = {
                    "cartItems",
                    "userCartItems",
                    "allCartItems"
            },
            allEntries = true
    )
    public boolean delete(final int cartItemId) {

        if (cartItemId <= 0) {
            return false;
        }
        return repository.delete(cartItemId);
    }

    @CacheEvict(
            cacheNames = {
                    "cartItems",
                    "userCartItems",
                    "allCartItems"
            },
            allEntries = true
    )
    public boolean deleteByUserId(final int userId) {

        if (userId <= 0) {
            return false;
        }
        return repository.deleteByUserId(userId);
    }

    @Cacheable(
            value = "cartItems",
            key = "#cartItemId"
    )
    public CartItem findById(final int cartItemId) {

        if (cartItemId <= 0) {
            return null;
        }
        return repository.findById(cartItemId);
    }

    @Cacheable(
            value = "userCartItems",
            key = "#userId"
    )
    public Collection<CartItem> findByUserId(final int userId) {

        if (userId <= 0) {
            return Collections.emptyList();
        }
        return repository.findByUserId(userId);
    }

    @Cacheable("allCartItems")
    public Collection<CartItem> findAll() {

        return repository.findAll();
    }
}