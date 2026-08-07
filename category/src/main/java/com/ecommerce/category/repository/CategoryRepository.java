/*
 * CategoryRepository.java
 *
 * Version 1.0
 *
 * July 30, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.category.repository;

import java.util.Collection;

import com.ecommerce.category.entity.Category;

// Repository interface for category operation
public interface CategoryRepository {

    // Saves a category
    boolean save(Category category);

    // Updates a category
    boolean update(Category category);

    // Deletes a category
    boolean delete(Integer categoryId);

    // Finds a category using its id
    Category findById(Integer categoryId);

    // Finds a category using its name
    Category findByName(String categoryName);

    // Returns all categories
    Collection<Category> findAll();

    // Checks whether the category exists
    boolean existsByName(String categoryName);
}