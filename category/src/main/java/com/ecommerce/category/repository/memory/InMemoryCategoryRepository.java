/*
 * InMemoryCategoryRepository.java
 *
 * Version 1.0
 *
 * July 30, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.category.repository.memory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Repository;

import com.ecommerce.category.entity.Category;
import com.ecommerce.category.repository.CategoryRepository;
import com.ecommerce.common.util.IdGenerator;

// In-memory implementation of CategoryRepository
@Repository("inMemoryCategoryRepository")
public class InMemoryCategoryRepository implements CategoryRepository {

    private final List<Category> categories;

    // Creates an InMemoryCategoryRepository object
    public InMemoryCategoryRepository() {

        this.categories = new ArrayList<>();
    }

    // Saves a category
    @Override
    public boolean save(final Category category) {

        Objects.requireNonNull(category, "Category cannot be null.");

        if (existsByName(category.getCategoryName())) {

            return false;
        }

        category.setCategoryId(IdGenerator.getInstance().nextCategoryId());
        return categories.add(category);
    }

    // Updates a category
    @Override
    public boolean update(final Category category) {

        Objects.requireNonNull(category, "Category cannot be null.");

        final Category existingCategory = findById(category.getCategoryId());

        if (existingCategory == null) {

            return false;
        }
        existingCategory.setCategoryName(category.getCategoryName());

        return true;
    }

    // Deletes a category
    @Override
    public boolean delete(final Integer categoryId) {

        Objects.requireNonNull(categoryId, "Category ID cannot be null.");

        return categories.removeIf(category -> category.getCategoryId().equals(categoryId));
    }

    // Finds a category using its id
    @Override
    public Category findById(final Integer categoryId) {

        Objects.requireNonNull(categoryId, "Category ID cannot be null.");

        for (final Category category : categories) {

            if (category.getCategoryId().equals(categoryId)) {

                return category;
            }
        }
        return null;
    }

    // Finds a category using its name
    @Override
    public Category findByName(final String categoryName) {

        Objects.requireNonNull(categoryName, "Category name cannot be null.");

        for (final Category category : categories) {

            if (category.getCategoryName().equalsIgnoreCase(categoryName)) {

                return category;
            }
        }

        return null;
    }

    // Returns all categories
    @Override
    public Collection<Category> findAll() {

        return new ArrayList<>(categories);
    }

    // Checks whether a category exists
    @Override
    public boolean existsByName(final String categoryName) {

        return findByName(categoryName) != null;
    }
}