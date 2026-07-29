package com.ecommerce.repository;

import com.ecommerce.model.Product;
import java.util.Collection;

public interface ProductRepository {

    boolean save(final Product product);

    boolean update(final Product product);

    boolean delete(final int productId);

    Product findById(final int productId);

    Product findByName(final String productName);

    Collection<Product> findAll();

    boolean existsByName(final String productName);
}