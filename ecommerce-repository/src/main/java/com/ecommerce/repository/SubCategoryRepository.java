/*
 * SubCategoryRepository.java
 *
 * Version 1.2
 *
 * July 27, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.repository;

import com.ecommerce.model.SubCategory;
import java.util.Collection;

/**
 * Repository interface for managing SubCategory entities.
 *
 * Defines CRUD operations, sub-category search functionality,
 * and category-based sub-category retrieval operations.
 *
 * Implementations may store sub-category data using different
 * storage mechanisms such as database or in-memory collections.
 */
public interface SubCategoryRepository {

    boolean save(final SubCategory subCategory);

    boolean update(final SubCategory subCategory);

    boolean delete(final int subCategoryId);

    SubCategory findById(final int subCategoryId);

    SubCategory findByName(final String subCategoryName);

    Collection<SubCategory> findByCategoryId(final int categoryId);

    Collection<SubCategory> findAll();

    boolean existsByName(final String subCategoryName);
}