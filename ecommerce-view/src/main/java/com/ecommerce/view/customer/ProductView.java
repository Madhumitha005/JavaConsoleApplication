package com.ecommerce.view.customer;

import java.util.Collection;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ecommerce.controller.ProductController;
import com.ecommerce.model.Product;

@Component
public class ProductView {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductView.class);
    private final ProductController productController;

// Constructor injection
    public ProductView(final ProductController productController) {

        this.productController = Objects.requireNonNull(productController, "ProductController cannot be null");
    }

// View All product
    public void showProducts() {

        LOGGER.info("Customer requested to view all products.");

        try {

            System.out.println("\n========== AVAILABLE PRODUCTS ==========");

            // ProductController actual method:
            // findAll()
            Collection<Product> products = productController.findAll();

            if (products == null || products.isEmpty()) {

                LOGGER.info("No products available.");

                System.out.println("No Products Available.");

                return;
            }

            // Display Products
            for (Product product : products) {

                displayProduct(product);
            }

            LOGGER.info("Products loaded successfully. Total products: {}", products.size());

        } catch (Exception exception) {

            LOGGER.error("Error while loading products.", exception);

            System.out.println("Error : " + exception.getMessage());
        }
    }

// Search product By name
    public void searchProduct(final String productName) {

        LOGGER.info("Customer searching product by name.");

        try {

            // Basic Input Validation
            if (productName == null || productName.isBlank()) {

                LOGGER.warn("Product search attempted with empty name.");

                System.out.println("Product Name Cannot Be Empty.");
                return;
            }

            String searchName = productName.trim();
            Product product = productController.findByName(searchName);

            if (product == null) {

                LOGGER.info("Product not found: {}", searchName);

                System.out.println("Product Not Found.");

                return;
            }

            // Display Search Result
            System.out.println("\n========== SEARCH RESULT ==========");

            displayProduct(product);

            LOGGER.info("Product found successfully: {}", searchName);

        } catch (Exception exception) {

            LOGGER.error("Error while searching product.", exception);

            System.out.println("Error : " + exception.getMessage());
        }
    }

// Display Product
    private void displayProduct(final Product product) {

        if (product == null) {

            return;
        }

        double discountedPrice = product.getPrice()
                * (1 - product.getDiscountPercentage() / 100.0);

        System.out.println("=========================================");
        System.out.println("Product ID   : " + product.getProductId());
        System.out.println("Product Name : " + product.getProductName());
        System.out.println("Price        : $" + product.getPrice());
        System.out.println("Discount     : " + product.getDiscountPercentage()+"%");
        System.out.println("Discounted Price      $" + discountedPrice);
        System.out.println("Quantity     : " + product.getQuantity());

        // Display Category
        if (product.getCategory() != null) {

            System.out.println("Category     : " + product.getCategory().getCategoryName());

        } else {

            System.out.println("Category     : N/A");
        }
    }
}
