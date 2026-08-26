/*
 * SubCategoryService.java
 *
 * Version 1.1
 *
 * August 21, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */

package com.ecommerce.subcategory.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.subcategory.entity.SubCategory;
import com.ecommerce.subcategory.repository.SubCategoryRepository;

// Provides business operations for subcategories
@Service
public class SubCategoryService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(SubCategoryService.class);

    private final SubCategoryRepository repository;

    // Creates a SubCategoryService
    public SubCategoryService(
            final SubCategoryRepository repository) {

        this.repository = Objects.requireNonNull(
                repository,
                "SubCategoryRepository cannot be null.");
    }

    // Adds a new subcategory
    @Transactional
    @CacheEvict(
            cacheNames = {
                    "subCategories",
                    "subCategoriesByCategory",
                    "allSubCategories"
            },
            allEntries = true
    )
    public boolean addSubCategory(
            final SubCategory subCategory) {

        Objects.requireNonNull(
                subCategory,
                "SubCategory cannot be null.");

        LocalDateTime now = LocalDateTime.now();

        subCategory.setCreatedAt(now);
        subCategory.setUpdatedAt(now);

        boolean saved = repository.save(subCategory);

        if (saved) {

            LOGGER.info(
                    "Subcategory added successfully: {}",
                    subCategory.getSubCategoryName());
        }

        return saved;
    }

    // Returns a subcategory by ID
    @Transactional(readOnly = true)
    @Cacheable(
            value = "subCategories",
            key = "#subCategoryId"
    )
    public SubCategory getSubCategoryById(
            final Integer subCategoryId) {

        Objects.requireNonNull(
                subCategoryId,
                "Subcategory ID cannot be null.");

        return repository.findById(subCategoryId);
    }

    // Returns all subcategories
    @Transactional(readOnly = true)
    @Cacheable("allSubCategories")
    public Collection<SubCategory> getAllSubCategories() {

        return repository.findAll();
    }

    /**
     * Returns all subcategories belonging
     * to a particular category.
     */
    @Transactional(readOnly = true)
    @Cacheable(
            value = "subCategoriesByCategory",
            key = "#categoryId"
    )
    public Collection<SubCategory> getSubCategoriesByCategoryId(
            final Integer categoryId) {

        Objects.requireNonNull(
                categoryId,
                "Category ID cannot be null.");

        return repository.findByCategoryId(categoryId);
    }

    // Updates an existing subcategory
    @Transactional
    @CacheEvict(
            cacheNames = {
                    "subCategories",
                    "subCategoriesByCategory",
                    "allSubCategories"
            },
            allEntries = true
    )
    public boolean updateSubCategory(
            final SubCategory subCategory) {

        Objects.requireNonNull(
                subCategory,
                "SubCategory cannot be null.");

        Objects.requireNonNull(
                subCategory.getSubCategoryId(),
                "Subcategory ID cannot be null.");

        subCategory.setUpdatedAt(LocalDateTime.now());

        boolean updated = repository.update(subCategory);

        if (updated) {

            LOGGER.info(
                    "Subcategory updated successfully: {}",
                    subCategory.getSubCategoryId());
        }

        return updated;
    }

    // Deletes a subcategory
    @Transactional
    @CacheEvict(
            cacheNames = {
                    "subCategories",
                    "subCategoriesByCategory",
                    "allSubCategories"
            },
            allEntries = true
    )
    public boolean deleteSubCategory(
            final Integer subCategoryId) {

        Objects.requireNonNull(
                subCategoryId,
                "Subcategory ID cannot be null.");

        boolean deleted =
                repository.delete(subCategoryId);

        if (deleted) {

            LOGGER.info(
                    "Subcategory deleted successfully: {}",
                    subCategoryId);
        }

        return deleted;
    }
}