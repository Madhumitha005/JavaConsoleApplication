package com.ecommerce.service;

import java.util.Collection;
import java.util.Collections;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.util.LoggerUtil;
import com.ecommerce.util.StringUtil;

public class ProductService {

    private final ProductRepository memoryRepository;
    private final ProductRepository jdbcRepository;
    private final StringUtil stringUtil;
    private final LoggerUtil logger;

    // Constructor Injection
    public ProductService(ProductRepository memoryRepository,
                          ProductRepository jdbcRepository) {

        this.memoryRepository = memoryRepository;
        this.jdbcRepository = jdbcRepository;
        this.stringUtil = StringUtil.getInstance();
        this.logger = LoggerUtil.getInstance();
    }

    // Add Product
    public boolean addProduct(Product product) {

        if (product == null) {
            return false;
        }

        if (product.getProductName() == null ||
                product.getProductName().isBlank()) {
            return false;
        }

        if (product.getCategory() == null) {
            return false;
        }

        if (product.getPrice() <= 0) {
            return false;
        }

        if (product.getQuantity() <= 0) {
            return false;
        }

        product.setProductName(stringUtil.clean(product.getProductName()));

        boolean memorySaved = memoryRepository.addProduct(product);
        boolean jdbcSaved = jdbcRepository.addProduct(product);

        if (jdbcSaved) {
            logger.info("Product Added : " + product.getProductName());
        } else {
            logger.error("Product Add Failed : " + product.getProductName());
        }

        return memorySaved && jdbcSaved;
    }

    // Update Product
    public boolean updateProduct(Product product) {

        if (product == null) {
            return false;
        }

        Product existingProduct = jdbcRepository.findById(product.getProductId());

        if (existingProduct == null) {
            return false;
        }

        product.setProductName(stringUtil.clean(product.getProductName()));

        boolean memoryUpdated = memoryRepository.updateProduct(product);
        boolean jdbcUpdated = jdbcRepository.updateProduct(product);

        if (jdbcUpdated) {
            logger.info("Product Updated : " + product.getProductName());
        } else {
            logger.error("Product Update Failed : " + product.getProductName());
        }

        return memoryUpdated && jdbcUpdated;
    }

    // Delete Product
    public boolean deleteProduct(int productId) {

        boolean memoryDeleted = memoryRepository.deleteProduct(productId);
        boolean jdbcDeleted = jdbcRepository.deleteProduct(productId);

        if (jdbcDeleted) {
            logger.info("Product Deleted : " + productId);
        } else {
            logger.error("Product Delete Failed : " + productId);
        }

        return memoryDeleted && jdbcDeleted;
    }

    // View All Products
    public Collection<Product> getAllProducts() {

        Collection<Product> products = memoryRepository.getAllProducts();

        if (products == null || products.isEmpty()) {
            products = jdbcRepository.getAllProducts();
        }

        return products == null ? Collections.emptyList() : products;
    }

    // Search Product
    public Product searchByName(String productName) {

        if (productName == null || productName.isBlank()) {
            return null;
        }

        productName = stringUtil.clean(productName);

        Product product = memoryRepository.findByName(productName);

        if (product == null) {
            product = jdbcRepository.findByName(productName);
        }

        return product;
    }

    // Get Product By Id
    public Product getProductById(int productId) {

        Product product = memoryRepository.findById(productId);

        if (product == null) {
            product = jdbcRepository.findById(productId);
        }

        return product;
    }
}