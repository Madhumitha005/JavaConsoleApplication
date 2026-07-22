package com.ecommerce.validator;

import com.ecommerce.exception.ValidationException;

public class CategoryValidator {

    private CategoryValidator() {
    }

    // Validate Category Name
    public static void validateCategoryName(String categoryName) {

        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new ValidationException("Category Name Cannot Be Empty.");
        }

        if (categoryName.length() < 3 || categoryName.length() > 50) {
            throw new ValidationException("Category Name Must Be Between 3 And 50 Characters.");
        }

        if (!categoryName.matches("[A-Za-z ]+")) {
            throw new ValidationException("Category Name Must Contain Only Letters.");
        }
    }
}