/*
 * ProductRepository.java
 *
 * Version 1.0
 *
 * August 03, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.product.repository;

import java.util.Collection;

import com.ecommerce.product.entity.Product;

/**
 * Repository interface used for
 * product management operations.
 */
public interface ProductRepository {

    // Saves a product
    boolean save(Product product);

    // Updates a product
    boolean update(Product product);

    // Deletes a product
    boolean delete(Integer productId);

    // Finds a product by id
    Product findById(Integer productId);

    // Finds a product by name
    Product findByName(String Name);

    // Returns all products
    Collection<Product> findAll();

    // Checks whether a product exists
    boolean existsByName(String ProductName,Integer sellerID);

    // Find related Product for seller
    Collection<Product> findBySellerId(Integer sellerId);}