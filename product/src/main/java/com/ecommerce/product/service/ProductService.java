package com.ecommerce.product.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ecommerce.product.entity.Product;
import com.ecommerce.product.repository.ProductRepository;

@Service
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository memoryRepository;
    private final ProductRepository jdbcRepository;

    public ProductService(

            @Qualifier("inMemoryProductRepository")
            final ProductRepository memoryRepository,
            @Qualifier("jdbcProductRepository")
            final ProductRepository jdbcRepository) {

        this.memoryRepository = Objects.requireNonNull(memoryRepository, "Memory repository cannot be null.");
        this.jdbcRepository = Objects.requireNonNull(jdbcRepository, "JDBC repository cannot be null.");
    }

    public boolean save(final Product product) {

        Objects.requireNonNull(product, "Product cannot be null.");

        if (product.getDiscount() < 0 || product.getDiscount() > 100) {
            return false;
        }

        if (product.getTax() < 0 || product.getTax() > 100) {
            return false;
        }

        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        String productName = product.getName();
        Integer sellerId = product.getSeller().getId();

        if (jdbcRepository.existsByName(productName, sellerId)) {

            LOGGER.warn("Product already exists.");

            return false;
        }

        // Save database first
        boolean jdbcSaved = jdbcRepository.save(product);

        if (!jdbcSaved) {

            LOGGER.error("Product database save failed.");

            return false;
        }

        // Save memory after database success
        memoryRepository.save(product);

        LOGGER.info("Product added successfully.");

        return true;
    }

    public boolean update(final Product product) {

        Objects.requireNonNull(product, "Product cannot be null.");

        if (product.getDiscount()!=null && (product.getDiscount() < 0 || product.getDiscount() > 100)) {

            return false;
        }

        if (product.getTax()!=null && (product.getTax() < 0 || product.getTax() > 100)) {

            return false;
        }

        product.setUpdatedAt(LocalDateTime.now());

        boolean memoryUpdated = memoryRepository.update(product);
        boolean jdbcUpdated = jdbcRepository.update(product);

        return memoryUpdated || jdbcUpdated;
    }

    public boolean delete(final Integer productId) {

        Objects.requireNonNull(productId, "Product ID cannot be null.");

        boolean memoryDeleted = memoryRepository.delete(productId);
        boolean jdbcDeleted =  jdbcRepository.delete(productId);

        return memoryDeleted || jdbcDeleted;
    }

    public Product findById(final Integer productId) {

        Objects.requireNonNull(productId, "Product ID cannot be null.");

        Product product = memoryRepository.findById(productId);

        if (product == null) {

            product = jdbcRepository.findById(productId);
        }

        return product;
    }

    public Product findByName(final String productName) {

        Objects.requireNonNull(productName, "Product name cannot be null.");

        Product product = memoryRepository.findByName(productName);

        if (product == null) {

            product = jdbcRepository.findByName(productName);
        }

        return product;
    }

    public Collection<Product> findAll() {

        Map<Integer, Product> products = new LinkedHashMap<>();

        memoryRepository.findAll().forEach(
                        product -> products.put(product.getProductId(), product));
        jdbcRepository.findAll().forEach(
                        product -> products.put(product.getProductId(), product));

        return products.values();
    }

    public boolean isStockAvailable(
            final Integer productId,
            final Integer quantity) {

        Product product = findById(productId);
        return product != null && product.getQuantity() >= quantity;
    }

    public boolean reduceStock(
            final Integer productId,
            final Integer quantity) {

        Product product = findById(productId);

        if (product == null) {

            return false;
        }

        if (product.getQuantity()
                < quantity) {

            return false;
        }

        product.setQuantity(product.getQuantity() - quantity);
        product.setUpdatedAt(LocalDateTime.now());

        return update(product);
    }

    public Collection<Product> findBySellerId(final Integer sellerId) {

        Collection<Product> products = memoryRepository.findBySellerId(sellerId);

        if (products == null || products.isEmpty()) {

            products = jdbcRepository.findBySellerId(sellerId);
        }
        return products;
    }
}