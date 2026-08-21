package com.ecommerce.product.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.product.entity.Product;
import com.ecommerce.product.repository.ProductRepository;

@Service
public class ProductService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository repository;

    public ProductService(
            final ProductRepository repository) {

        this.repository = Objects.requireNonNull(
                repository,
                "ProductRepository cannot be null.");
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(
                    value = "products",
                    allEntries = true),
            @CacheEvict(
                    value = "productsById",
                    allEntries = true),
            @CacheEvict(
                    value = "productsByName",
                    allEntries = true),
            @CacheEvict(
                    value = "productsBySeller",
                    allEntries = true)
    })
    public boolean save(final Product product) {

        Objects.requireNonNull(
                product,
                "Product cannot be null.");

        if (product.getDiscount() != null
                && (product.getDiscount() < 0
                || product.getDiscount() > 100)) {

            return false;
        }

        if (product.getTax() != null
                && (product.getTax() < 0
                || product.getTax() > 100)) {

            return false;
        }

        LocalDateTime now = LocalDateTime.now();

        product.setCreatedAt(now);
        product.setUpdatedAt(now);

        String productName = product.getName();

        Integer sellerId =
                product.getSeller().getId();

        if (repository.existsByName(
                productName,
                sellerId)) {

            LOGGER.warn(
                    "Product already exists: {}",
                    productName);

            return false;
        }

        boolean saved =
                repository.save(product);

        if (saved) {

            LOGGER.info(
                    "Product added successfully: {}",
                    productName);
        }

        return saved;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(
                    value = "products",
                    allEntries = true),
            @CacheEvict(
                    value = "productsById",
                    allEntries = true),
            @CacheEvict(
                    value = "productsByName",
                    allEntries = true),
            @CacheEvict(
                    value = "productsBySeller",
                    allEntries = true)
    })
    public boolean update(
            final Product product) {

        Objects.requireNonNull(
                product,
                "Product cannot be null.");

        Objects.requireNonNull(
                product.getProductId(),
                "Product ID cannot be null.");

        if (product.getDiscount() != null
                && (product.getDiscount() < 0
                || product.getDiscount() > 100)) {

            return false;
        }

        if (product.getTax() != null
                && (product.getTax() < 0
                || product.getTax() > 100)) {

            return false;
        }

        product.setUpdatedAt(
                LocalDateTime.now());

        boolean updated =
                repository.update(product);

        if (updated) {

            LOGGER.info(
                    "Product updated successfully: {}",
                    product.getProductId());
        }

        return updated;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(
                    value = "products",
                    allEntries = true),
            @CacheEvict(
                    value = "productsById",
                    allEntries = true),
            @CacheEvict(
                    value = "productsByName",
                    allEntries = true),
            @CacheEvict(
                    value = "productsBySeller",
                    allEntries = true)
    })
    public boolean delete(
            final Integer productId) {

        Objects.requireNonNull(
                productId,
                "Product ID cannot be null.");

        boolean deleted =
                repository.delete(productId);

        if (deleted) {

            LOGGER.info(
                    "Product deleted successfully: {}",
                    productId);
        }

        return deleted;
    }

    @Transactional(readOnly = true)
    @Cacheable(
            value = "productsById",
            key = "#productId")
    public Product findById(
            final Integer productId) {

        Objects.requireNonNull(
                productId,
                "Product ID cannot be null.");

        return repository.findById(productId);
    }

    @Transactional(readOnly = true)
    @Cacheable(
            value = "productsByName",
            key = "#productName")
    public Product findByName(
            final String productName) {

        Objects.requireNonNull(
                productName,
                "Product name cannot be null.");

        return repository.findByName(productName);
    }

    @Transactional(readOnly = true)
    @Cacheable(
            value = "products",
            key = "'all'")
    public Collection<Product> findAll() {

        // IMPORTANT:
        // Return a real ArrayList instead of
        // LinkedHashMap$LinkedValues.
        return new ArrayList<>(
                repository.findAll());
    }

    @Transactional(readOnly = true)
    @Cacheable(
            value = "productsBySeller",
            key = "#sellerId")
    public Collection<Product> findBySellerId(
            final Integer sellerId) {

        Objects.requireNonNull(
                sellerId,
                "Seller ID cannot be null.");

        return new ArrayList<>(
                repository.findBySellerId(sellerId));
    }

    @Transactional(readOnly = true)
    public boolean isStockAvailable(
            final Integer productId,
            final Integer quantity) {

        if (productId == null
                || quantity == null
                || quantity <= 0) {

            return false;
        }

        Product product =
                findById(productId);

        return product != null
                && product.getQuantity() >= quantity;
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(
                    value = "products",
                    allEntries = true),
            @CacheEvict(
                    value = "productsById",
                    allEntries = true),
            @CacheEvict(
                    value = "productsByName",
                    allEntries = true),
            @CacheEvict(
                    value = "productsBySeller",
                    allEntries = true)
    })
    public boolean reduceStock(
            final Integer productId,
            final Integer quantity) {

        if (productId == null
                || quantity == null
                || quantity <= 0) {

            return false;
        }

        Product product =
                repository.findById(productId);

        if (product == null) {
            return false;
        }

        if (product.getQuantity() < quantity) {
            return false;
        }

        product.setQuantity(
                product.getQuantity() - quantity);

        product.setUpdatedAt(
                LocalDateTime.now());

        return repository.update(product);
    }
}