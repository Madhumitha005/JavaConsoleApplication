package com.ecommerce.repository;

import com.ecommerce.model.Category;
import java.util.Collection;

public interface CategoryRepository {

    boolean save(final Category category);

    boolean update(final Category category);

    boolean delete(final int categoryId);

    Category findById(final int categoryId);

    Category findByName(final String categoryName);

    Collection<Category> findAll();

    boolean existsByName(final String categoryName);
}