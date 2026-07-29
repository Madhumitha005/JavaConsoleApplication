package com.ecommerce.repository.memory;

import com.ecommerce.common.util.IdGenerator;
import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

@Repository("inMemoryCategoryRepository")
public class InMemoryCategoryRepository
        implements CategoryRepository {

    private final Collection<Category> categories;

    public InMemoryCategoryRepository() {

        this.categories = new ArrayList<>();
    }

    @Override
    public boolean save(final Category category) {

        if (category == null) {
            return false;
        }

        category.setCategoryId(
                IdGenerator.getInstance()
                        .nextCategoryId()
        );

        return categories.add(category);
    }

    @Override
    public boolean update(final Category category) {

        if (category == null) {
            return false;
        }

        for (final Category existing : categories) {

            if (existing.getCategoryId()
                    == category.getCategoryId()) {

                existing.setCategoryName(
                        category.getCategoryName()
                );

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean delete(final int categoryId) {

        return categories.removeIf(
                category ->
                        category.getCategoryId()
                                == categoryId
        );
    }

    @Override
    public Category findById(
            final int categoryId) {

        for (final Category category : categories) {

            if (category.getCategoryId()
                    == categoryId) {

                return category;
            }
        }

        return null;
    }

    @Override
    public Category findByName(
            final String categoryName) {

        for (final Category category : categories) {

            if (category.getCategoryName()
                    .equalsIgnoreCase(categoryName)) {

                return category;
            }
        }

        return null;
    }

    @Override
    public Collection<Category> findAll() {

        return new ArrayList<>(categories);
    }

    @Override
    public boolean existsByName(
            final String categoryName) {

        return findByName(categoryName) != null;
    }
}