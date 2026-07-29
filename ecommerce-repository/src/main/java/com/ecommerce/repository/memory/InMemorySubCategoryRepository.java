/*
 * InMemorySubCategoryRepository.java
 *
 * Version 1.6
 *
 * July 28, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.repository.memory;

import com.ecommerce.model.SubCategory;
import com.ecommerce.repository.SubCategoryRepository;
import com.ecommerce.common.util.IdGenerator;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

/**
 * In-memory implementation of SubCategoryRepository.
 *
 * This repository manages SubCategory objects using an in-memory collection.
 * The data is temporarily stored during application execution without
 * connecting to an external database.
 *
 * Provides sub-category CRUD operations, search functionality,
 * and category-based sub-category retrieval.
 */
@Repository("inMemorySubCategoryRepository")
public class InMemorySubCategoryRepository implements SubCategoryRepository {

    // Stores sub-category objects in memory
    private final Collection<SubCategory> subCategories;

    // Initializes an empty sub-category collection.
    public InMemorySubCategoryRepository() {
        this.subCategories = new ArrayList<>();
    }

    /**
     * Saves a new sub-category into the in-memory collection.
     *
     * @param subCategory sub-category object to be saved
     * @return true if sub-category is saved successfully, otherwise false
     */
    @Override
    public boolean save(final SubCategory subCategory) {

        if (subCategory == null || existsByName(subCategory.getSubCategoryName())) {
            return false;
        }

        subCategory.setSubCategoryId(IdGenerator.getInstance().nextSubCategoryId());

        return subCategories.add(subCategory);
    }

    /**
     * Updates an existing sub-category using sub-category id.
     *
     * @param updatedSubCategory updated sub-category object
     * @return true if sub-category is updated successfully, otherwise false
     */
    @Override
    public boolean update(final SubCategory updatedSubCategory) {

        if (updatedSubCategory == null) {
            return false;
        }

        for (SubCategory subCategory : subCategories) {

            if (subCategory.getSubCategoryId() == updatedSubCategory.getSubCategoryId()) {

                subCategory.setSubCategoryName(updatedSubCategory.getSubCategoryName());
                subCategory.setCategory(updatedSubCategory.getCategory());

                return true;
            }
        }
        return false;
    }

    /**
     * Deletes a sub-category using sub-category id.
     *
     * @param subCategoryId unique identifier of sub-category
     * @return true if sub-category is deleted successfully, otherwise false
     */
    @Override
    public boolean delete(final int subCategoryId) {

        return subCategories.removeIf(subCategory -> subCategory.getSubCategoryId() == subCategoryId);
    }

    /**
     * Finds a sub-category using sub-category id.
     *
     * @param subCategoryId unique identifier of sub-category
     * @return matching SubCategory object, otherwise null
     */
    @Override
    public SubCategory findById(final int subCategoryId) {

        for (SubCategory subCategory : subCategories) {

            if (subCategory.getSubCategoryId() == subCategoryId) {
                return subCategory;
            }
        }

        return null;
    }

    /**
     * Finds a sub-category using sub-category name.
     *
     * @param subCategoryName name of sub-category
     * @return matching SubCategory object, otherwise null
     */
    @Override
    public SubCategory findByName(final String subCategoryName) {

        if (subCategoryName == null) {
            return null;
        }

        for (SubCategory subCategory : subCategories) {

            if (subCategory.getSubCategoryName().equalsIgnoreCase(subCategoryName)) {

                return subCategory;
            }
        }

        return null;
    }

    /**
     * Retrieves all sub-categories belonging to a category.
     *
     * @param categoryId unique identifier of category
     * @return collection of sub-categories under the category
     */
    @Override
    public Collection<SubCategory> findByCategoryId(final int categoryId) {

        Collection<SubCategory> result = new ArrayList<>();

        for (SubCategory subCategory : subCategories) {

            if (subCategory.getCategory() != null && subCategory.getCategory().getCategoryId() == categoryId) {

                result.add(subCategory);
            }
        }

        return result;
    }

    /**
     * Retrieves all sub-categories stored in memory.
     *
     * @return collection containing all sub-categories
     */
    @Override
    public Collection<SubCategory> findAll() {

        return new ArrayList<>(subCategories);
    }

    /**
     * Checks whether a sub-category exists with the given name.
     *
     * @param subCategoryName name of sub-category
     * @return true if sub-category exists, otherwise false
     */
    @Override
    public boolean existsByName(final String subCategoryName) {

        return findByName(subCategoryName) != null;
    }
}