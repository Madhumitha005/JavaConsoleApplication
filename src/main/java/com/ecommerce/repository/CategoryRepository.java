package com.ecommerce.repository;

import com.ecommerce.model.Category;
import java.util.Collection;

public interface CategoryRepository {

    boolean save(Category category);

    Category findById(int categoryId);

    Category findByName(String categoryName);

    Collection<Category> findAll();

    boolean update(Category category);

    boolean delete(int categoryId);
}