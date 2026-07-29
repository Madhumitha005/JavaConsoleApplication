/*
 * ProductRepository.java
 *
 * Version 1.2
 *
 * July 27, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.repository;

import com.ecommerce.model.Product;
import java.util.Collection;

/**
 * Repository interface for managing Product entities.
 *
 * Defines CRUD operations and product search functionality
 * based on product identifier and product name.
 *
 * Implementations may store product data using different storage
 * mechanisms such as database or in-memory collections.
 */
public interface ProductRepository {

    boolean save(final Product product);

    boolean update(final Product product);

    boolean delete(final int productId);

    Product findById(final int productId);

    Product findByName(final String productName);

    Collection<Product> findAll();

    boolean existsByName(final String productName);
}