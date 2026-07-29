/*
 * CategoryRepository.java
 *
 * Version 1.2
 *
 * July 27, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.repository;

import com.ecommerce.model.Category;
import java.util.Collection;

/**
 * Repository interface for managing Category entities.
 *
 * Defines CRUD operations and category search functionality.
 *
 * Implementations may store category data using different storage
 * mechanisms such as database or in-memory collections.
 */
public interface CategoryRepository {

    boolean save(final Category category);

    boolean update(final Category category);

    boolean delete(final int categoryId);

    Category findById(final int categoryId);

    Category findByName(final String categoryName);

    Collection<Category> findAll();

    boolean existsByName(final String categoryName);
}