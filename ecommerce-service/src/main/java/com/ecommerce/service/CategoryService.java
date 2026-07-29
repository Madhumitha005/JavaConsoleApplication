/*
 * CategoryService.java
 *
 * Version 1.6
 *
 * July 28, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.service;

import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Service class responsible for category management operations.
 *
 * Handles category creation, retrieval, updating, and deletion.
 *
 * This service maintains category data consistency between
 * in-memory and JDBC repository implementations.
 */
@Service
public class CategoryService {

    private final CategoryRepository memoryRepository;
    private final CategoryRepository jdbcRepository;

    /**
     * Creates CategoryService with required repository dependencies.
     *
     * @param memoryRepository in-memory category repository
     * @param jdbcRepository JDBC category repository
     */
    public CategoryService(

            @Qualifier("inMemoryCategoryRepository")
            final CategoryRepository memoryRepository,

            @Qualifier("jdbcCategoryRepository")
            final CategoryRepository jdbcRepository) {

        this.memoryRepository = memoryRepository;
        this.jdbcRepository = jdbcRepository;
    }

    /**
     * Adds a new category.
     *
     * Saves category information into both memory and JDBC repositories.
     *
     * @param category category object to be saved
     * @return true if category is saved successfully in both repositories,
     * otherwise false
     */
    public boolean addCategory(final Category category) {

        if (category == null) {
            return false;
        }

        boolean memorySaved = memoryRepository.save(category);
        boolean jdbcSaved = jdbcRepository.save(category);

        return memorySaved && jdbcSaved;
    }

    /**
     * Retrieves all categories.
     *
     * @return collection containing all categories
     */
    public Collection<Category> getAllCategories() {

        return jdbcRepository.findAll();
    }

    /**
     * Finds a category using category id.
     *
     * @param categoryId unique identifier of category
     * @return matching Category object, otherwise null
     */
    public Category getCategoryById(final int categoryId) {

        return jdbcRepository.findById(categoryId);
    }

    /**
     * Finds a category using category name.
     *
     * @param categoryName name of category
     * @return matching Category object, otherwise null
     */
    public Category getCategoryByName(final String categoryName) {

        return jdbcRepository.findByName(categoryName);
    }

    /**
     * Updates an existing category.
     *
     * Updates category information in both repositories.
     *
     * @param category updated category object
     * @return true if category is updated successfully in any repository,
     * otherwise false
     */
    public boolean updateCategory(final Category category) {

        if (category == null) {
            return false;
        }

        boolean memoryUpdated = memoryRepository.update(category);
        boolean jdbcUpdated = jdbcRepository.update(category);

        return memoryUpdated || jdbcUpdated;
    }

    /**
     * Deletes a category using category id.
     *
     * Deletes category information from both repositories.
     *
     * @param categoryId unique identifier of category
     * @return true if category is deleted successfully in both repositories,
     * otherwise false
     */
    public boolean deleteCategory(final int categoryId) {

        boolean memoryDeleted = memoryRepository.delete(categoryId);
        boolean jdbcDeleted = jdbcRepository.delete(categoryId);

        return memoryDeleted && jdbcDeleted;
    }
}