/*
 * CartRepository.java
 *
 * Version 1.2
 *
 * July 27, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.repository;

import com.ecommerce.model.Cart;
import java.util.Collection;

/**
 * Repository interface for managing Cart entities.
 *
 * Defines CRUD operations and user-based cart retrieval operations.
 *
 * Implementations may store cart data using different storage
 * mechanisms such as database or in-memory collections.
 */
public interface CartRepository {

    boolean save(final Cart cart);

    boolean update(final Cart cart);

    boolean delete(final int cartId);

    Cart findById(final int cartId);

    Cart findByUserId(final int userId);

    Collection<Cart> findAll();

    Collection<Cart> findByUserIdList(final int userId);
}