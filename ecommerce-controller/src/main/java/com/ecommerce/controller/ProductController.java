/*
 * OrderController.java
 *
 * Version 1.5
 *
 * July 26, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.controller;

import java.util.Collection;
import java.util.Objects;

import org.springframework.stereotype.Controller;

import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;

@Controller
public class ProductController {

    private final ProductService productService;

    // Constructor Injection
    public ProductController(final ProductService productService) {

        this.productService = Objects.requireNonNull(productService, "ProductService cannot be null");
    }

    // Save Product
    public boolean save(final Product product) {

        Objects.requireNonNull(product, "Product cannot be null");

        return productService.save(product);
    }

    // Update Product
    public boolean update(final Product product) {

        Objects.requireNonNull(product, "Product cannot be null");

        return productService.update(product);
    }

    // Delete Product
    public boolean delete(final int productId) {

        return productService.delete(productId);
    }

    // View All Products
    public Collection<Product> findAll() {

        return productService.findAll();
    }

    // Find Product By Name
    public Product findByName(final String productName) {

        Objects.requireNonNull(productName, "Product name cannot be null");

        return productService.findByName(productName);
    }

    // Find Product By Id
    public Product findById(final int productId) {

        return productService.findById(productId);
    }

    // Check Stock
    public boolean isStockAvailable(final int productId, final int quantity) {

        return productService.isStockAvailable(productId, quantity);
    }

    // Reduce Stock
    public boolean reduceStock(final int productId, final int quantity) {

        return productService.reduceStock(productId, quantity);
    }
}