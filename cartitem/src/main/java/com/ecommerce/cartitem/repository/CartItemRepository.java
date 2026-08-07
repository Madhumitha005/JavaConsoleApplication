/*
 * CartItemRepository.java
 *
 * Version 2.0
 *
 * August 03, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.cartitem.repository;

import java.util.Collection;

import com.ecommerce.cartitem.entity.CartItem;

public interface CartItemRepository {

    boolean save(final CartItem cartItem);

    boolean update(final CartItem cartItem);

    boolean delete(final int cartItemId);

    boolean deleteByUserId(final int userId);

    CartItem findById(final int cartItemId);

    Collection<CartItem> findByUserId(final int userId);

    Collection<CartItem> findAll();
}