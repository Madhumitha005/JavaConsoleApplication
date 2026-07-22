package com.ecommerce.validator;

import com.ecommerce.exception.ValidationException;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;

public final class ProductValidator {

    private ProductValidator() {
    }

    public static void validate(Product product) {

        if(product == null) {

            throw new ValidationException("Product cannot be null.");
        }

        validateProductName(product.getProductName());
        validatePrice(product.getPrice());
        validateQuantity(product.getQuantity());
        validateCategory(product.getCategory());
    }

    public static void validateProductName(String name) {

        if(name == null || name.trim().isEmpty()) {

            throw new ValidationException("Product name cannot be empty.");
        }

        if(!name.matches("[A-Za-z ]{3,50}")) {

            throw new ValidationException("Invalid product name.");
        }
    }

    public static void validatePrice(double price) {

        if(price <= 0) {

            throw new ValidationException("Price must be greater than zero.");
        }
    }

    public static void validateQuantity(int quantity) {

        if(quantity < 0) {

            throw new ValidationException("Quantity cannot be negative.");
        }
    }

    public static void validateCategory(Category category) {

        if(category == null) {

            throw new ValidationException("Category cannot be null.");
        }

        if(category.getCategoryId() <= 0) {

            throw new ValidationException("Invalid category id.");
        }
    }
}