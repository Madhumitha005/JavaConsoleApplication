/*
 * CartItemRepository.java
 *
 * Version 1.2
 *
 * July 27, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.repository;

import com.ecommerce.model.CartItem;
import java.util.Collection;

/**
 * Repository interface for managing CartItem entities.
 *
 * Defines CRUD operations and cart-based retrieval operations
 * for cart item management.
 *
 * Implementations may store cart item data using different
 * storage mechanisms such as database or in-memory collections.
 */
public interface CartItemRepository {

    boolean save(final CartItem cartItem);

    boolean update(final CartItem cartItem);

    boolean delete(final int cartItemId);

    boolean deleteByCartId(final int cartId);

    CartItem findById(final int cartItemId);

    Collection<CartItem> findByCartId(final int cartId);

    Collection<CartItem> findAll();
}