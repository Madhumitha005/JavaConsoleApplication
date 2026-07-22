package com.ecommerce.repository.memory;

import java.util.ArrayList;
import java.util.Collection;

import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;

public class InMemoryCategoryRepository implements CategoryRepository {

    private final Collection<Category> categories = new ArrayList<>();
    private int nextCategoryId = 1;

    @Override
    public boolean save(Category category) {

        if (category == null) {
            return false;
        }

        // Generate ID only if not already set
        if (category.getCategoryId() == 0) {
            category.setCategoryId(nextCategoryId++);
        }

        return categories.add(category);
    }

    @Override
    public Category findById(int categoryId) {

        for (Category category : categories) {

            if (category.getCategoryId() == categoryId) {
                return category;
            }
        }

        return null;
    }

    @Override
    public Category findByName(String categoryName) {

        for (Category category : categories) {

            if (category.getCategoryName().equalsIgnoreCase(categoryName)) {
                return category;
            }
        }

        return null;
    }

    @Override
    public Collection<Category> findAll() {
        return categories;
    }

    @Override
    public boolean update(Category category) {

        for (Category existing : categories) {

            if (existing.getCategoryId() == category.getCategoryId()) {

                existing.setCategoryName(category.getCategoryName());
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean delete(int categoryId) {

        return categories.removeIf(
                category -> category.getCategoryId() == categoryId
        );
    }
}