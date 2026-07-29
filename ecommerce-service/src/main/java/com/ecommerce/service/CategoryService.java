package com.ecommerce.service;

import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CategoryService {

    private final CategoryRepository memoryRepository;
    private final CategoryRepository jdbcRepository;

    public CategoryService(

            @Qualifier("inMemoryCategoryRepository")
            final CategoryRepository memoryRepository,

            @Qualifier("jdbcCategoryRepository")
            final CategoryRepository jdbcRepository) {

        this.memoryRepository = memoryRepository;
        this.jdbcRepository = jdbcRepository;
    }

    // Add Category
    public boolean addCategory(final Category category) {

        if (category == null) {
            return false;
        }

        boolean memorySaved = memoryRepository.save(category);
        boolean jdbcSaved = jdbcRepository.save(category);

        return memorySaved && jdbcSaved;
    }

    // View Categories
    public Collection<Category> getAllCategories() {

        return jdbcRepository.findAll();
    }

    // Find By ID
    public Category getCategoryById(final int categoryId) {

        return jdbcRepository.findById(categoryId);
    }

    // Find By Name
    public Category getCategoryByName(final String categoryName) {

        return jdbcRepository.findByName(categoryName);
    }

    // Update
    public boolean updateCategory(final Category category) {

        if (category == null) {
            return false;
        }

        boolean memoryUpdated = memoryRepository.update(category);
        boolean jdbcUpdated = jdbcRepository.update(category);

        return memoryUpdated || jdbcUpdated;
    }

    // Delete
    public boolean deleteCategory(final int categoryId) {

        boolean memoryDeleted = memoryRepository.delete(categoryId);
        boolean jdbcDeleted = jdbcRepository.delete(categoryId);

        return memoryDeleted && jdbcDeleted;
    }
}