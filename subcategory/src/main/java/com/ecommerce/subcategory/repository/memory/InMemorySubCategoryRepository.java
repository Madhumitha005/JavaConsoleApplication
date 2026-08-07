/*
 * InMemorySubCategoryRepository.java
 *
 * Version 1.0
 *
 * July 31, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.subcategory.repository.memory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Repository;

import com.ecommerce.common.util.IdGenerator;
import com.ecommerce.subcategory.entity.SubCategory;
import com.ecommerce.subcategory.repository.SubCategoryRepository;

/**
 * This repository stores subcategories
 * temporarily inside the application.
 */
@Repository("inMemorySubCategoryRepository")
public class InMemorySubCategoryRepository implements SubCategoryRepository {

    // Stores all subcategories
    private final List<SubCategory> subCategories;

    // Creates an InMemorySubCategoryRepository object
    public InMemorySubCategoryRepository() {

        this.subCategories = new ArrayList<>();
    }

    // Saves a subcategory
    @Override
    public boolean save(final SubCategory subCategory) {

        Objects.requireNonNull(subCategory, "Subcategory cannot be null.");

        if (existsByName(subCategory.getSubCategoryName())) {

            return false;
        }

        subCategory.setSubCategoryId(IdGenerator.getInstance().nextSubCategoryId());

        return subCategories.add(subCategory);
    }

    // Updates a subcategory
    @Override
    public boolean update(final SubCategory updatedSubCategory) {

        Objects.requireNonNull(updatedSubCategory, "Subcategory cannot be null.");

        for (SubCategory subCategory : subCategories) {

            if (subCategory.getSubCategoryId()
                    .equals(updatedSubCategory.getSubCategoryId())) {

                subCategory.setSubCategoryName(updatedSubCategory.getSubCategoryName());
                subCategory.setCategory(updatedSubCategory.getCategory());

                return true;
            }
        }
        return false;
    }

    // Deletes a subcategory
    @Override
    public boolean delete(final Integer subCategoryId) {

        Objects.requireNonNull(subCategoryId, "Subcategory ID cannot be null.");

        return subCategories.removeIf(subCategory ->
                        subCategory.getSubCategoryId().equals(subCategoryId));
    }

    // Finds a subcategory using its id
    @Override
    public SubCategory findById(final Integer subCategoryId) {

        Objects.requireNonNull(subCategoryId, "Subcategory ID cannot be null.");

        for (SubCategory subCategory : subCategories) {

            if (subCategory.getSubCategoryId().equals(subCategoryId)) {

                return subCategory;
            }
        }
        return null;
    }

    // Finds a subcategory using its name
    @Override
    public SubCategory findByName(final String subCategoryName) {

        Objects.requireNonNull(subCategoryName, "Subcategory name cannot be null.");

        for (SubCategory subCategory : subCategories) {

            if (subCategory.getSubCategoryName()
                    .equalsIgnoreCase(subCategoryName)) {

                return subCategory;
            }
        }

        return null;
    }

    // Returns all subcategories belonging to a category
    @Override
    public Collection<SubCategory> findByCategoryId(final Integer categoryId) {

        Objects.requireNonNull(categoryId, "Category ID cannot be null.");

        Collection<SubCategory> result = new ArrayList<>();

        for (SubCategory subCategory : subCategories) {

            if (subCategory.getCategory() != null
                    && subCategory.getCategory()
                    .getCategoryId()
                    .equals(categoryId)) {

                result.add(subCategory);
            }
        }
        return result;
    }

    // Returns all subcategories
    @Override
    public Collection<SubCategory> findAll() {

        return new ArrayList<>(subCategories);
    }

    // Checks whether a subcategory exists
    @Override
    public boolean existsByName(final String subCategoryName) {

        return findByName(subCategoryName) != null;
    }
}