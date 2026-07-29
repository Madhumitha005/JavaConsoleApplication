/*
 * InMemoryCategoryRepository.java
 *
 * Version 1.6
 *
 * July 28, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.repository.memory;

import com.ecommerce.common.util.IdGenerator;
import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

/**
 * In-memory implementation of CategoryRepository.
 *
 * This repository manages Category objects using an in-memory collection.
 * The data is temporarily stored during application execution without
 * connecting to an external database.
 *
 * Provides category CRUD operations and category search functionality.
 */
@Repository("inMemoryCategoryRepository")
public class InMemoryCategoryRepository
        implements CategoryRepository {

    // Stores category objects in memory
    private final Collection<Category> categories;

    // Creates an empty category collection
    public InMemoryCategoryRepository() {

        this.categories = new ArrayList<>();
    }

    /**
     * Saves a new category into the in-memory collection.
     *
     * @param category category object to be saved
     * @return true if category is successfully saved, otherwise false
     */
    @Override
    public boolean save(final Category category) {

        if (category == null) {
            return false;
        }

        category.setCategoryId(
                IdGenerator.getInstance()
                        .nextCategoryId()
        );

        return categories.add(category);
    }

    /**
     * Updates an existing category using category id.
     *
     * @param category updated category object
     * @return true if category is updated successfully, otherwise false
     */
    @Override
    public boolean update(final Category category) {

        if (category == null) {
            return false;
        }

        for (final Category existing : categories) {

            if (existing.getCategoryId()
                    == category.getCategoryId()) {

                existing.setCategoryName(
                        category.getCategoryName()
                );

                return true;
            }
        }

        return false;
    }

    /**
     * Deletes a category using category id.
     *
     * @param categoryId unique identifier of category
     * @return true if category is deleted, otherwise false
     */
    @Override
    public boolean delete(final int categoryId) {

        return categories.removeIf(
                category ->
                        category.getCategoryId()
                                == categoryId
        );
    }

    /**
     * Finds a category using category id.
     *
     * @param categoryId unique identifier of category
     * @return matching Category object, otherwise null
     */
    @Override
    public Category findById(
            final int categoryId) {

        for (final Category category : categories) {

            if (category.getCategoryId()
                    == categoryId) {

                return category;
            }
        }

        return null;
    }

    /**
     * Finds a category using category name.
     *
     * @param categoryName name of category
     * @return matching Category object, otherwise null
     */
    @Override
    public Category findByName(
            final String categoryName) {

        for (final Category category : categories) {

            if (category.getCategoryName()
                    .equalsIgnoreCase(categoryName)) {

                return category;
            }
        }

        return null;
    }

    /**
     * Retrieves all categories stored in memory.
     *
     * @return collection containing all categories
     */
    @Override
    public Collection<Category> findAll() {

        return new ArrayList<>(categories);
    }
}