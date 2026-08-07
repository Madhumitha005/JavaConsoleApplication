/*
 * SubCategoryService.java
 *
 * Version 1.1
 *
 * July 31, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */

package com.ecommerce.subcategory.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ecommerce.subcategory.entity.SubCategory;
import com.ecommerce.subcategory.repository.SubCategoryRepository;

// Provides services related to subcategory management
@Service
public class SubCategoryService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(SubCategoryService.class);

    private final SubCategoryRepository memoryRepository;
    private final SubCategoryRepository jdbcRepository;

    // Creates a SubCategoryService object
    public SubCategoryService(

            @Qualifier("inMemorySubCategoryRepository")
            final SubCategoryRepository memoryRepository,
            @Qualifier("jdbcSubCategoryRepository")
            final SubCategoryRepository jdbcRepository) {

        this.memoryRepository = Objects.requireNonNull(memoryRepository, "Memory repository cannot be null.");
        this.jdbcRepository = Objects.requireNonNull(jdbcRepository, "JDBC repository cannot be null.");
    }

    // Adds a subcategory
    public boolean addSubCategory(final SubCategory subCategory) {

        Objects.requireNonNull(subCategory, "SubCategory cannot be null.");

        subCategory.setCreatedAt(LocalDateTime.now());
        subCategory.setUpdatedAt(LocalDateTime.now());

        boolean memorySaved = memoryRepository.save(subCategory);
        boolean jdbcSaved = jdbcRepository.save(subCategory);

        LOGGER.info("Subcategory added successfully.");

        return memorySaved && jdbcSaved;
    }

    // Returns a subcategory using its id
    public SubCategory getSubCategoryById(final Integer subCategoryId) {

        Objects.requireNonNull(subCategoryId, "Subcategory ID cannot be null.");

        SubCategory subCategory = memoryRepository.findById(subCategoryId);

        if (subCategory == null) {

            subCategory = jdbcRepository.findById(subCategoryId);
        }

        return subCategory;
    }

    // Returns all subcategories
    public Collection<SubCategory> getAllSubCategories() {

        Map<Integer, SubCategory> subCategories = new LinkedHashMap<>();

        memoryRepository.findAll()
                .forEach(subCategory -> subCategories
                        .put(subCategory.getSubCategoryId(), subCategory));
        jdbcRepository.findAll()
                .forEach(subCategory -> subCategories
                        .put(subCategory.getSubCategoryId(), subCategory));

        return subCategories.values();
    }

    // Returns subcategories by category id
    public Collection<SubCategory> getSubCategoriesByCategoryId(final Integer categoryId) {

        Objects.requireNonNull(categoryId, "Category ID cannot be null.");

        Map<Integer, SubCategory> subCategories = new LinkedHashMap<>();

        memoryRepository.findByCategoryId(categoryId).forEach(
                subCategory -> subCategories.put(subCategory.getSubCategoryId(), subCategory));
        jdbcRepository.findByCategoryId(categoryId).forEach(
                subCategory -> subCategories.put(subCategory.getSubCategoryId(), subCategory));

        return subCategories.values();
    }

    // Updates a subcategory
    public boolean updateSubCategory(final SubCategory subCategory) {

        Objects.requireNonNull(subCategory, "SubCategory cannot be null.");

        subCategory.setUpdatedAt(LocalDateTime.now());

        boolean memoryUpdated = memoryRepository.update(subCategory);
        boolean jdbcUpdated = jdbcRepository.update(subCategory);

        LOGGER.info("Subcategory updated successfully.");

        return memoryUpdated || jdbcUpdated;
    }

    // Deletes a subcategory
    public boolean deleteSubCategory(final Integer subCategoryId) {

        Objects.requireNonNull(subCategoryId, "Subcategory ID cannot be null.");

        boolean memoryDeleted = memoryRepository.delete(subCategoryId);
        boolean jdbcDeleted = jdbcRepository.delete(subCategoryId);

        LOGGER.info("Subcategory deleted successfully.");

        return memoryDeleted || jdbcDeleted;
    }
}