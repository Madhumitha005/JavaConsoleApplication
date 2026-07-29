package com.ecommerce.service;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository memoryRepository;
    private final ProductRepository jdbcRepository;

    public ProductService(

            @Qualifier("inMemoryProductRepository")
            final ProductRepository memoryRepository,

            @Qualifier("jdbcProductRepository")
            final ProductRepository jdbcRepository) {

        this.memoryRepository = memoryRepository;
        this.jdbcRepository = jdbcRepository;
    }

    public boolean save(final Product product) {

        if (product == null) {

            return false;
        }

        // Save in Memory
        boolean memorySaved = memoryRepository.save(product);

        // Save in Database
        boolean jdbcSaved = jdbcRepository.save(product);

        return memorySaved && jdbcSaved;
    }

    public boolean update(final Product product) {

        if (product == null) {

            return false;
        }

        if (product.getProductId() <= 0) {

            return false;
        }

        // Update Memory
        boolean memoryUpdated = memoryRepository.update(product);

        // Update Database
        boolean jdbcUpdated = jdbcRepository.update(product);

        return memoryUpdated || jdbcUpdated;
    }

    public boolean delete(final int productId) {

        if (productId <= 0) {

            return false;
        }

        // Delete from Memory
        boolean memoryDeleted = memoryRepository.delete(productId);

        // Delete from Database
        boolean jdbcDeleted = jdbcRepository.delete(productId);

        return memoryDeleted || jdbcDeleted;
    }

    public Product findById(final int productId) {

        if (productId <= 0) {

            return null;
        }

        // First search in Memory
        Product product = memoryRepository.findById(productId);

        // If not found in Memory,
        // search in Database
        if (product == null) {

            product = jdbcRepository.findById(productId);
        }

        return product;
    }

    public Product findByName(final String productName) {

        if (productName == null || productName.isBlank()) {

            return null;
        }

        String trimmedProductName = productName.trim();

        // First search in Memory
        Product product = memoryRepository.findByName(trimmedProductName);

        // If not found in Memory,
        // search in Database

        if (product == null) {

            product = jdbcRepository.findByName(trimmedProductName);
        }

        return product;
    }

    public Collection<Product> findAll() {

        Map<Integer, Product> products = new LinkedHashMap<>();

        Collection<Product> memoryProducts = memoryRepository.findAll();

        if (memoryProducts != null) {

            for (Product product : memoryProducts) {

                if (product != null) {

                    products.put(product.getProductId(), product);
                }
            }
        }

        Collection<Product> jdbcProducts = jdbcRepository.findAll();

        if (jdbcProducts != null) {

            for (Product product : jdbcProducts) {

                if (product != null) {

                    products.put(product.getProductId(), product);
                }
            }
        }

        return products.values();
    }

    public boolean isStockAvailable(final int productId, final int quantity) {

        if (productId <= 0) {

            return false;
        }

        if (quantity <= 0) {

            return false;
        }

        Product product = findById(productId);

        if (product == null) {

            return false;
        }

        return product.getQuantity() >= quantity;
    }

    public boolean reduceStock(final int productId, final int quantity) {

        if (productId <= 0) {

            return false;
        }

        if (quantity <= 0) {

            return false;
        }

        // Find product
        Product product = findById(productId);

        if (product == null) {

            return false;
        }

        // Check available stock
        if (product.getQuantity() < quantity) {

            return false;
        }

        // Reduce stock
        int remainingQuantity = product.getQuantity() - quantity;
        product.setQuantity(remainingQuantity);

        // Update Memory
        boolean memoryUpdated = memoryRepository.update(product);

        // Update Database
        boolean jdbcUpdated = jdbcRepository.update(product);

        return memoryUpdated || jdbcUpdated;
    }
}