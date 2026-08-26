/*
 * CategoryService.java
 *
 * Version 1.1
 *
 * August 21, 2026
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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ecommerce.category.entity.Category;
import com.ecommerce.category.repository.CategoryRepository;

@Service
public class CategoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository repository;

    public CategoryService(final CategoryRepository repository) {

        this.repository = Objects.requireNonNull(repository, "Category repository cannot be null.");
    }

    @Transactional
    @CacheEvict(
            value = "categories",
            allEntries = true
    )
    public boolean addCategory(final Category category) {

        Objects.requireNonNull(category, "Category cannot be null.");
        String categoryName = category.getCategoryName();

        if (repository.existsByName(categoryName)) {

            LOGGER.warn("Category already exists: {}", categoryName);
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        category.setCreatedAt(now);
        category.setUpdatedAt(now);
        boolean saved = repository.save(category);

        if (saved) {

            LOGGER.info("Category added successfully: {}", categoryName);
        }
        return saved;
    }

    @Transactional(readOnly = true)
    @Cacheable(
            value = "categories",
            key = "'all'"
    )
    public Collection<Category> getAllCategories() {

        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Cacheable(
            value = "categoryById",
            key = "#categoryId"
    )
    public Category getCategoryById(final Integer categoryId) {

        Objects.requireNonNull(categoryId, "Category ID cannot be null.");
        return repository.findById(categoryId);
    }

    @Transactional(readOnly = true)
    @Cacheable(
            value = "categoryByName",
            key = "#categoryName"
    )
    public Category getCategoryByName(final String categoryName) {

        Objects.requireNonNull(categoryName, "Category name cannot be null.");
        return repository.findByName(categoryName);
    }

    @Transactional
    @Caching(evict = {

            @CacheEvict(
                    value = "categories",
                    allEntries = true
            ),
            @CacheEvict(
                    value = "categoryById",
                    key = "#categoryId"
            ),
            @CacheEvict(
                    value = "categoryByName",
                    allEntries = true
            )
    })
    public boolean updateCategory(
            final Integer categoryId,
            final String categoryName) {

        Objects.requireNonNull(categoryId, "Category ID cannot be null.");
        Objects.requireNonNull(categoryName, "Category name cannot be null.");
        Category existingCategory = repository.findById(categoryId);

        if (existingCategory == null) {

            LOGGER.warn("Category not found: {}", categoryId);
            return false;
        }
        Category existingByName = repository.findByName(categoryName);

        if (existingByName != null
                && !categoryId.equals(
                existingByName.getCategoryId())) {

            LOGGER.warn("Category name already exists: {}", categoryName);
            return false;
        }
        existingCategory.setCategoryName(categoryName);
        existingCategory.setUpdatedAt(LocalDateTime.now());

        boolean updated = repository.update(existingCategory);

        if (updated) {

            LOGGER.info("Category updated successfully: {}", categoryId);
        }
        return updated;
    }

    @Transactional
    @Caching(evict = {

            @CacheEvict(
                    value = "categories",
                    allEntries = true
            ),
            @CacheEvict(
                    value = "categoryById",
                    key = "#categoryId"
            ),
            @CacheEvict(
                    value = "categoryByName",
                    allEntries = true
            )
    })
    public boolean deleteCategory(final Integer categoryId) {

        Objects.requireNonNull(categoryId, "Category ID cannot be null.");

        boolean deleted = repository.delete(categoryId);

        if (deleted) {

            LOGGER.info("Category deleted successfully: {}", categoryId);
        }
        return deleted;
    }
}