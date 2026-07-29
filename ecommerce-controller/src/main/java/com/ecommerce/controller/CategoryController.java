/*
 * CategoryController.java
 *
 * Version 1.3
 *
 * July 26, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.controller;

import java.util.Collection;
import java.util.Objects;

import org.springframework.stereotype.Controller;

import com.ecommerce.model.Category;
import com.ecommerce.service.CategoryService;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    // Constructor Injection
    public CategoryController(final CategoryService categoryService) {

        this.categoryService = Objects.requireNonNull(categoryService, "CategoryService cannot be null");
    }

    // Add Category
    public boolean addCategory(final Category category) {

        Objects.requireNonNull(category, "Category cannot be null");

        return categoryService.addCategory(category);
    }

    // View All Categories
    public Collection<Category> viewCategories() {

        return categoryService.getAllCategories();
    }

    // Find Category By Id
    public Category getCategoryById(final int categoryId) {

        return categoryService.getCategoryById(categoryId);
    }

    // Find Category By Name
    public Category getCategoryByName(final String categoryName) {

        Objects.requireNonNull(categoryName, "Category name cannot be null");

        return categoryService.getCategoryByName(categoryName);
    }

    // Update Category
    public boolean updateCategory(final Category category) {

        Objects.requireNonNull(category, "Category cannot be null");

        return categoryService.updateCategory(category);
    }

    // Delete Category
    public boolean deleteCategory(final int categoryId) {

        return categoryService.deleteCategory(categoryId);
    }
}