package com.ecommerce.controller;

import java.util.Collection;
import java.util.Objects;

import com.ecommerce.model.Category;
import com.ecommerce.service.CategoryService;

public class CategoryController {

    private final CategoryService categoryService;

    // Constructor Injection
    public CategoryController(CategoryService categoryService) {

        this.categoryService = categoryService;
    }

    // Add Category
    public boolean addCategory(Category category) {

        Objects.requireNonNull(category, "Category cannot be null");
        return categoryService.addCategory(category);
    }

    // View All Categories
    public Collection<Category> viewCategories() {

        return categoryService.getAllCategories();
    }

    // Search Category By ID
    public Category searchCategoryById(int categoryId) {

        return categoryService.getCategoryById(categoryId);
    }

    // Search Category By Name
    public Category searchCategoryByName(String categoryName) {

        Objects.requireNonNull(categoryName, "Category name cannot be null");
        return categoryService.getCategoryByName(categoryName);
    }

    // Update Category
    public boolean updateCategory(Category category) {

        Objects.requireNonNull(category, "Category cannot be null");
        return categoryService.updateCategory(category);
    }

    // Delete Category
    public boolean deleteCategory(int categoryId) {

        return categoryService.deleteCategory(categoryId);
    }
}