package com.ecommerce.view.admin;

import java.util.Collection;
import java.util.Scanner;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.ecommerce.common.util.InputUtil;
import com.ecommerce.controller.CategoryController;
import com.ecommerce.model.Category;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@Component
public class AdminCategoryView {

    private final CategoryController categoryController;
    private final Scanner scanner;

    // Constructor Injection
    public AdminCategoryView(final CategoryController categoryController) {

        this.categoryController = categoryController;
        this.scanner = InputUtil.getInstance().getScanner();
    }

    // Validate Category
    private boolean validateCategory(final Category category) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        if (violations.isEmpty()) {

            factory.close();
            return true;
        }

        for (ConstraintViolation<Category> violation : violations) {

            System.out.println(violation.getMessage());
        }

        factory.close();
        return false;
    }

    // Category Menu
    public void show() {

        while (true) {

            System.out.println("\n========== CATEGORY MANAGEMENT ==========");
            System.out.println("1. Add Category");
            System.out.println("2. View Categories");
            System.out.println("3. Update Category");
            System.out.println("4. Delete Category");
            System.out.println("5. Back");
            System.out.print("Enter Choice : ");

            try {

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1 -> addCategory();
                    case 2 -> viewCategories();
                    case 3 -> updateCategory();
                    case 4 -> deleteCategory();
                    case 5 -> {
                        return;
                    }
                    default ->
                            System.out.println("Invalid Choice.");
                }

            } catch (NumberFormatException exception) {

                System.out.println("Enter valid number.");
            }
        }
    }

    // Add Category
    private void addCategory() {

        Category category = new Category();

        while (true) {

            System.out.print("Category Name : ");

            String categoryName = scanner.nextLine();

            // Correct
            category.setCategoryName(categoryName);

            if (validateCategory(category)) {

                break;
            }
        }

        boolean result = categoryController.addCategory(category);

        if (result) {

            System.out.println("Category Added Successfully.");

        } else {

            System.out.println("Category Add Failed.");
        }
    }

    // View Category
    private void viewCategories() {

        Collection<Category> categories = categoryController.viewCategories();

        System.out.println("\n========== CATEGORY LIST ==========");

        if (categories == null || categories.isEmpty()) {

            System.out.println("No Categories Found.");

            return;
        }

        for (Category category : categories) {

            System.out.println("ID   : " + category.getCategoryId());
            System.out.println("Name : " + category.getCategoryName());
        }
    }

    //Update category
    private void updateCategory() {

        try {

            System.out.print("Category ID : ");
            int id = Integer.parseInt(scanner.nextLine());

            Category existingCategory = categoryController.getCategoryById(id);

            if (existingCategory == null) {

                System.out.println("Category Not Found.");

                return;
            }

            System.out.println("Current Name : " + existingCategory.getCategoryName());
            System.out.print("New Category Name : ");

            String newName = scanner.nextLine();
            existingCategory.setCategoryName(newName);

            if (!validateCategory(existingCategory)) {

                return;
            }

            boolean result = categoryController.updateCategory(existingCategory);

            if (result) {

                System.out.println("Category Updated Successfully.");

            } else {

                System.out.println("Category Update Failed.");
            }

        } catch (NumberFormatException exception) {

            System.out.println("Invalid Category ID.");

        } catch (Exception exception) {

            System.out.println("Error : " + exception.getMessage());
        }
    }

    // Delete Category
    private void deleteCategory() {

        try {

            System.out.print("Category ID : ");

            int id = Integer.parseInt(scanner.nextLine());

            Category category = categoryController.getCategoryById(id);

            if (category == null) {

                System.out.println("Category Not Found.");

                return;
            }

            boolean result = categoryController.deleteCategory(id);

            if (result) {

                System.out.println("Category Deleted Successfully.");

            } else {

                System.out.println("Category Delete Failed.");
            }

        } catch (NumberFormatException exception) {

            System.out.println("Invalid Category ID.");

        } catch (Exception exception) {

            System.out.println("Error : " + exception.getMessage());
        }
    }
}