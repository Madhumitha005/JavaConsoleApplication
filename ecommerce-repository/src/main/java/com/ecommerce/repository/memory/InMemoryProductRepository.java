/*
 * InMemoryProductRepository.java
 *
 * Version 1.6
 *
 * July 28, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.repository.memory;

import com.ecommerce.common.util.IdGenerator;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

/**
 * In-memory implementation of ProductRepository.
 *
 * This repository manages Product objects using an in-memory collection.
 * The data is temporarily stored during application execution without
 * connecting to an external database.
 *
 * Provides product CRUD operations and product search functionality.
 */
@Repository("inMemoryProductRepository")
public class InMemoryProductRepository implements ProductRepository {

    // Stores product objects in memory
    private final Collection<Product> products;

    // Initializes an empty product collection
    public InMemoryProductRepository() {
        this.products = new ArrayList<>();
    }

    /**
     * Saves a new product into the in-memory collection.
     *
     * @param product product object to be saved
     * @return true if product is saved successfully, otherwise false
     */
    @Override
    public boolean save(final Product product) {

        if (product == null) {
            return false;
        }

        product.setProductId(IdGenerator.getInstance().nextProductId());

        return products.add(product);
    }

    /**
     * Updates an existing product using product id.
     *
     * @param product updated product object
     * @return true if product is updated successfully, otherwise false
     */
    @Override
    public boolean update(final Product product) {

        if (product == null) {
            return false;
        }

        for (Product existingProduct : products) {

            if (existingProduct.getProductId() == product.getProductId()) {

                existingProduct.setProductName(product.getProductName());
                existingProduct.setPrice(product.getPrice());
                existingProduct.setDiscountPercentage(product.getDiscountPercentage());
                existingProduct.setQuantity(product.getQuantity());
                existingProduct.setCategory(product.getCategory());
                existingProduct.setSubCategory(product.getSubCategory());
                existingProduct.setProductStatus(product.getProductStatus());

                return true;
            }
        }
        return false;
    }

    /**
     * Deletes a product using product id.
     *
     * @param productId unique identifier of product
     * @return true if product is deleted successfully, otherwise false
     */
    @Override
    public boolean delete(final int productId) {

        return products.removeIf(product -> product.getProductId() == productId
        );
    }

    /**
     * Finds a product using product id.
     *
     * @param productId unique identifier of product
     * @return matching Product object, otherwise null
     */
    @Override
    public Product findById(final int productId) {

        for (Product product : products) {

            if (product.getProductId() == productId) {

                return product;
            }
        }

        return null;
    }

    /**
     * Finds a product using product name.
     *
     * @param productName name of product
     * @return matching Product object, otherwise null
     */
    @Override
    public Product findByName(final String productName) {

        if (productName == null) {

            return null;
        }

        for (Product product : products) {

            if (product.getProductName().equalsIgnoreCase(productName)) {

                return product;
            }
        }
        return null;
    }

    /**
     * Retrieves all products stored in memory.
     *
     * @return collection containing all products
     */
    @Override
    public Collection<Product> findAll() {

        return new ArrayList<>(products);
    }
}