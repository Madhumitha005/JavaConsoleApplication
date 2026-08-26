 /*
  * CategoryRepository.java
  *
  * Version 1.1
  *
  * August 21, 2026
  *
  * Copyright (c) 2026.
  * All Rights Reserved.
  */
 package com.ecommerce.category.repository;

 import java.util.Collection;
 import com.ecommerce.category.entity.Category;

 // Repository interface for category operations.
 public interface CategoryRepository {

     // Saves a category
     boolean save(Category category);

     // Updates an existing category
     boolean update(Category category);

     // Deletes a category
     boolean delete(Integer categoryId);

     // Finds a category by ID
     Category findById(Integer categoryId);

     // Finds a category by name
     Category findByName(String categoryName);

     // Returns all categories
     Collection<Category> findAll();

     // Checks whether a category with the given name exists
     boolean existsByName(String categoryName);
 }