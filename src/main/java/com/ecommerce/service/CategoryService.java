package com.ecommerce.service;

import java.util.Collection;
import java.util.Collections;

import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;

public class CategoryService {

    private final CategoryRepository memoryRepository;
    private final CategoryRepository jdbcRepository;

    // Constructor Injection
    public CategoryService(CategoryRepository memoryRepository,
                           CategoryRepository jdbcRepository) {

        this.memoryRepository = memoryRepository;
        this.jdbcRepository = jdbcRepository;
    }

    // Add Category
    public boolean addCategory(Category category) {

        if (category == null) {
            return false;
        }

        boolean memorySaved = memoryRepository.save(category);
        boolean jdbcSaved = jdbcRepository.save(category);

        return memorySaved && jdbcSaved;
    }

    // View All Categories
    public Collection<Category> getAllCategories() {

        Collection<Category> categories = memoryRepository.findAll();

        if (categories == null || categories.isEmpty()) {
            categories = jdbcRepository.findAll();
        }

        return categories == null ? Collections.emptyList() : categories;
    }

    // Find Category By Id
    public Category getCategoryById(int categoryId) {

        Category category = memoryRepository.findById(categoryId);

        if (category == null) {
            category = jdbcRepository.findById(categoryId);
        }

        return category;
    }

    // Find Category By Name
    public Category getCategoryByName(String categoryName) {

        Category category = memoryRepository.findByName(categoryName);

        if (category == null) {
            category = jdbcRepository.findByName(categoryName);
        }

        return category;
    }

    // Update Category
    public boolean updateCategory(Category category) {

        if (category == null) {
            return false;
        }

        boolean memoryUpdated = memoryRepository.update(category);
        boolean jdbcUpdated = jdbcRepository.update(category);

        return memoryUpdated && jdbcUpdated;
    }

    // Delete Category
    public boolean deleteCategory(int categoryId) {

        boolean memoryDeleted = memoryRepository.delete(categoryId);
        boolean jdbcDeleted = jdbcRepository.delete(categoryId);

        return memoryDeleted && jdbcDeleted;
    }
}