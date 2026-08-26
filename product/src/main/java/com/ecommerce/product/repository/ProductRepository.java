package com.ecommerce.product.repository;

import java.util.Collection;

import com.ecommerce.product.entity.Product;

public interface ProductRepository {

    boolean save(final Product product);

    boolean update(final Product product);

    boolean delete(final Integer productId);

    Product findById(final Integer productId);

    Product findByName(final String productName);

    Collection<Product> findAll();

    boolean existsByName(
            final String productName,
            final Integer sellerId);

    Collection<Product> findBySellerId(
            final Integer sellerId);
}