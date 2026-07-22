package com.ecommerce.controller;

import java.util.Collection;
import java.util.Objects;

import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;

public class ProductController {

    private final ProductService productService;

    // Constructor Injection
    public ProductController(ProductService productService) {

        this.productService = Objects.requireNonNull(productService,"ProductService cannot be null"
        );
    }

    // Add Product
    public boolean addProduct(Product product) {

        return productService.addProduct(product);
    }

    // Update Product
    public boolean updateProduct(Product product) {

        return productService.updateProduct(product);
    }

    // Delete Product
    public boolean deleteProduct(int productId) {

        return productService.deleteProduct(productId);
    }

    // View All Products
    public Collection<Product> viewProducts() {

        return productService.getAllProducts();
    }

    // Search Product By Name
    public Product searchProductByName(String productName) {

        return productService.searchByName(productName);
    }

    // Get Product By Id
    public Product getProductById(int productId) {

        return productService.getProductById(productId);
    }
}