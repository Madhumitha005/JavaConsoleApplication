/*
 * CategoryService.java
 *
 * Version 1.0
 *
 * July 30, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.category.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ecommerce.category.entity.Category;
import com.ecommerce.category.repository.CategoryRepository;

@Service
public class CategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository memoryRepository;
    private final CategoryRepository jdbcRepository;

    public CategoryService(

            @Qualifier("inMemoryCategoryRepository")
            final CategoryRepository memoryRepository,
            @Qualifier("jdbcCategoryRepository")
            final CategoryRepository jdbcRepository) {

        this.memoryRepository = Objects.requireNonNull(memoryRepository, "Memory repository cannot be null.");
        this.jdbcRepository = Objects.requireNonNull(jdbcRepository, "JDBC repository cannot be null.");
    }

    public boolean addCategory(final Category category) {

        Objects.requireNonNull(category, "Category cannot be null.");
        String categoryName = category.getCategoryName();

        if (memoryRepository.existsByName(categoryName)
                || jdbcRepository.existsByName(categoryName)) {

            LOGGER.warn("Category already exists.");

            return false;
        }

        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());

        boolean memorySaved = memoryRepository.save(category);
        boolean jdbcSaved = jdbcRepository.save(category);

        LOGGER.info("Category added successfully.");

        return memorySaved && jdbcSaved;
    }

    public Collection<Category> getAllCategories() {

        return jdbcRepository.findAll();
    }

    public Category getCategoryById(final Integer categoryId) {

        Objects.requireNonNull(categoryId, "Category ID cannot be null.");

        Category category = memoryRepository.findById(categoryId);

        if (category == null) {

            category = jdbcRepository.findById(categoryId);
        }
        return category;
    }

    public Category getCategoryByName(final String categoryName) {

        Objects.requireNonNull(categoryName, "Category name cannot be null.");

        Category category = memoryRepository.findByName(categoryName);

        if (category == null) {

            category = jdbcRepository.findByName(categoryName);
        }
        return category;
    }

    public boolean updateCategory(final Category category) {

        Objects.requireNonNull(category, "Category cannot be null.");

        category.setUpdatedAt(LocalDateTime.now());

        boolean memoryUpdated = memoryRepository.update(category);
        boolean jdbcUpdated = jdbcRepository.update(category);

        LOGGER.info("Category updated successfully.");

        return memoryUpdated || jdbcUpdated;
    }

    public boolean deleteCategory(final Integer categoryId) {

        Objects.requireNonNull(categoryId, "Category ID cannot be null.");

        boolean memoryDeleted = memoryRepository.delete(categoryId);
        boolean jdbcDeleted = jdbcRepository.delete(categoryId);

        LOGGER.info("Category deleted successfully.");

        return memoryDeleted && jdbcDeleted;
    }
}