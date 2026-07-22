package com.ecommerce.view.customer;

import java.util.Collection;

import com.ecommerce.controller.ProductController;
import com.ecommerce.exception.ValidationException;
import com.ecommerce.model.Product;
import com.ecommerce.validator.ProductValidator;

public class ProductView {

    private final ProductController productController;

    // Constructor Injection
    public ProductView(ProductController productController) {
        this.productController = productController;
    }

    // View all products
    public void showProducts() {

        try {

            System.out.println("\n========== AVAILABLE PRODUCTS ==========");

            // Get product list using controller
            Collection<Product> products = productController.viewProducts();

            if (products == null || products.isEmpty()) {
                System.out.println("No Products Available.");
                return;
            }

            for (Product product : products) {
                displayProduct(product);
            }

        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

    // Search product
    public void searchProduct(String productName) {

        try {

            ProductValidator.validateProductName(productName);

            // Search product through controller
            Product product =productController.searchProductByName(productName);

            if (product == null) {
                System.out.println("Product Not Found.");
                return;
            }

            System.out.println("\n========== SEARCH RESULT ==========");
            displayProduct(product);

        } catch (ValidationException e) {

            System.out.println("Validation Error : " + e.getMessage());

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }
    }

    // Display product
    private void displayProduct(Product product) {

        if (product == null) {
            return;
        }

        System.out.println("----------------------------------------");
        System.out.println("Product ID   : " + product.getProductId());
        System.out.println("Product Name : " + product.getProductName());
        System.out.println("Price        : ₹" + product.getPrice());
        System.out.println("Quantity     : " + product.getQuantity());

        if (product.getCategory() != null) {
            System.out.println("Category     : "
                    + product.getCategory().getCategoryName());
        } else {
            System.out.println("Category     : N/A");
        }
    }
}