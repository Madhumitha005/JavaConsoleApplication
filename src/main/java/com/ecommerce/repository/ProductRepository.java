package com.ecommerce.repository;

import com.ecommerce.model.Product;
import java.util.Collection;

public interface ProductRepository {

    boolean addProduct(Product product);

    boolean updateProduct(Product product);

    boolean deleteProduct(int productId);

    Product findById(int productId);

    Product findByName(String productName);

    Collection<Product> getAllProducts();
}