/*
 * SubCategoryRepository.java
 *
 * Version 1.0
 *
 * August 21, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */

package com.ecommerce.subcategory.repository;

import java.util.Collection;

import com.ecommerce.subcategory.entity.SubCategory;

/**
 * Repository interface for subcategory operations.
 */
public interface SubCategoryRepository {

    // Saves a subcategory
    boolean save(final SubCategory subCategory);

    // Updates a subcategory
    boolean update(final SubCategory subCategory);

    // Deletes a subcategory
    boolean delete(final Integer subCategoryId);

    // Finds a subcategory using its id
    SubCategory findById(final Integer subCategoryId);

    // Finds a subcategory using its name
    SubCategory findByName(final String subCategoryName);

    // Returns subcategories belonging to a category
    Collection<SubCategory> findByCategoryId(
            final Integer categoryId);

    // Returns all subcategories
    Collection<SubCategory> findAll();

    // Checks whether a subcategory already exists
    boolean existsByName(final String subCategoryName);
}