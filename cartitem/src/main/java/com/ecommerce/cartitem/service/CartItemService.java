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

import com.ecommerce.cartitem.entity.CartItem;
import com.ecommerce.cartitem.repository.CartItemRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class CartItemService {

    private final CartItemRepository memoryRepository;
    private final CartItemRepository jdbcRepository;

    public CartItemService(

            @Qualifier("inMemoryCartItemRepository")
            final CartItemRepository memoryRepository,

            @Qualifier("jdbcCartItemRepository")
            final CartItemRepository jdbcRepository) {

        this.memoryRepository = memoryRepository;
        this.jdbcRepository = jdbcRepository;
    }

    @Caching(evict = {
            @CacheEvict(value = "allCartItems", allEntries = true),
            @CacheEvict(value = "userCartItems",
                    key = "#cartItem.userId")
    })
    public boolean save(final CartItem cartItem) {

        if (cartItem == null
                || cartItem.getUserId() <= 0
                || cartItem.getProductId() <= 0
                || cartItem.getQuantity() <= 0) {

            return false;
        }

        boolean memorySaved = memoryRepository.save(cartItem);
        boolean jdbcSaved = jdbcRepository.save(cartItem);

        return memorySaved && jdbcSaved;
    }

    @Caching(evict = {
            @CacheEvict(value = "cartItems",
                    key = "#cartItem.cartItemId"),
            @CacheEvict(value = "userCartItems",
                    key = "#cartItem.userId"),
            @CacheEvict(value = "allCartItems",
                    allEntries = true)
    })
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

    @Caching(evict = {
            @CacheEvict(value = "cartItems",
                    key = "#cartItemId"),
            @CacheEvict(value = "allCartItems",
                    allEntries = true),
            @CacheEvict(value = "userCartItems",
                    allEntries = true)
    })
    public boolean delete(final int cartItemId) {

        if (cartItemId <= 0) {
            return false;
        }

        boolean memoryDeleted = memoryRepository.delete(cartItemId);
        boolean jdbcDeleted = jdbcRepository.delete(cartItemId);

        return memoryDeleted && jdbcDeleted;
    }

    @Caching(evict = {
            @CacheEvict(value = "userCartItems",
                    key = "#userId"),
            @CacheEvict(value = "allCartItems",
                    allEntries = true)
    })
    public boolean deleteByUserId(final int userId) {

        if (userId <= 0) {
            return false;
        }

        boolean memoryDeleted = memoryRepository.deleteByUserId(userId);
        boolean jdbcDeleted = jdbcRepository.deleteByUserId(userId);

        return memoryDeleted && jdbcDeleted;
    }

    @Cacheable(value = "cartItems", key = "#cartItemId")
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

    @Cacheable(value = "userCartItems", key = "#userId")
    public Collection<CartItem> findByUserId(final int userId) {

        if (userId <= 0) {
            return Collections.emptyList();
        }

        Map<Integer, CartItem> cartItems = new LinkedHashMap<>();

        Collection<CartItem> memoryItems = memoryRepository.findByUserId(userId);
        Collection<CartItem> jdbcItems = jdbcRepository.findByUserId(userId);

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

    @Cacheable(value = "allCartItems")
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