/*
 * InMemoryProductRepository.java
 *
 * Version 1.0
 *
 * August 03, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.product.repository.memory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.springframework.stereotype.Repository;

import com.ecommerce.common.util.IdGenerator;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.repository.ProductRepository;

@Repository("inMemoryProductRepository")
public class InMemoryProductRepository implements ProductRepository {

    private final Collection<Product> products;

    public InMemoryProductRepository() {

        this.products = new ArrayList<>();
    }

    @Override
    public boolean save(final Product product) {

        Objects.requireNonNull(product, "Product cannot be null.");
        product.setProductId(IdGenerator.getInstance().nextProductId());

        if (product.getDiscount() < 0 || product.getDiscount() > 100) {

            return false;
        }

        if (product.getTax() < 0 || product.getTax() > 100) {

            return false;
        }

        if (product.getSeller() == null || product.getSeller().getId() == null) {

            return false;
        }
        return products.add(product);
    }

    @Override
    public boolean update(final Product product) {

        Objects.requireNonNull(product, "Product cannot be null.");

        if (product.getDiscount() < 0 || product.getDiscount() > 100) {

            return false;
        }

        if (product.getTax() < 0 || product.getTax() > 100) {

            return false;
        }

        for (Product existingProduct : products) {

            if (existingProduct.getProductId().equals(product.getProductId())) {

                existingProduct.setSeller(product.getSeller());
                existingProduct.setName(product.getName());
                existingProduct.setPrice(product.getPrice());
                existingProduct.setQuantity(product.getQuantity());
                existingProduct.setDiscount(product.getDiscount());
                existingProduct.setTax(product.getTax());
                existingProduct.setSubCategory(product.getSubCategory());
                existingProduct.setStatusId(product.getStatusId());
                existingProduct.setUpdatedAt(product.getUpdatedAt());
                existingProduct.setCreatedAt(product.getCreatedAt());

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean delete(final Integer productId) {

        return products.removeIf(product -> product.getProductId().equals(productId));
    }

    @Override
    public Product findById(final Integer productId) {

        for (Product product : products) {

            if (product.getProductId().equals(productId)) {

                return product;
            }
        }

        return null;
    }

    @Override
    public Product findByName(final String productName) {

        for (Product product : products) {

            if (product.getName().equalsIgnoreCase(productName)) {

                return product;
            }
        }

        return null;
    }

    @Override
    public Collection<Product> findAll() {

        return new ArrayList<>(products);
    }

    @Override
    public boolean existsByName(
            final String productName,
            final Integer sellerId) {

        return products.stream().anyMatch(product ->
                        product.getName().equalsIgnoreCase(productName)
                                && product.getSeller().getId().equals(sellerId));
    }

    @Override
    public Collection<Product> findBySellerId(final Integer sellerId) {

        Collection<Product> sellerProducts = new ArrayList<>();

        for (Product product : products) {

            if (product.getSeller() != null && product.getSeller().getId().equals(sellerId)) {

                sellerProducts.add(product);
            }
        }
        return sellerProducts;
    }
}