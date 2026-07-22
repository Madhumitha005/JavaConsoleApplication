package com.ecommerce.view.admin;

import java.util.Collection;
import java.util.Scanner;

import com.ecommerce.controller.CategoryController;
import com.ecommerce.exception.ValidationException;
import com.ecommerce.model.Category;
import com.ecommerce.util.InputUtil;
import com.ecommerce.validator.CategoryValidator;

public class AdminCategoryView {

    private final CategoryController categoryController;
    private final Scanner scanner;

    // Constructor Injection
    public AdminCategoryView(CategoryController categoryController) {

        this.categoryController = categoryController;
        this.scanner = InputUtil.getInstance().getScanner();
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

            int choice;

            try {

                choice = Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {

                System.out.println("Invalid Choice.");
                continue;
            }

            switch (choice) {

                case 1 -> addCategory();

                case 2 -> viewCategories();

                case 3 -> updateCategory();

                case 4 -> deleteCategory();

                case 5 -> {
                    return;
                }

                default -> System.out.println("Invalid Choice.");
            }
        }
    }

    // Add Category
    private void addCategory() {

        try {

            Category category = new Category();

            while (true) {

                System.out.print("Category Name : ");

                String name = scanner.nextLine();

                try {

                    CategoryValidator.validateCategoryName(name);
                    category.setCategoryName(name);
                    break;

                } catch (ValidationException e) {

                    System.out.println(e.getMessage());
                }
            }

            if (categoryController.addCategory(category)) {

                System.out.println("Category Added Successfully.");

            } else {

                System.out.println("Failed To Add Category.");
            }

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }
    }

    // View Categories
    private void viewCategories() {

        Collection<Category> categories =categoryController.viewCategories();

        System.out.println("\n========== CATEGORY LIST ==========");

        if (categories.isEmpty()) {

            System.out.println("No Categories Found.");
            return;
        }

        for (Category category : categories) {

            System.out.println("--------------------------------");
            System.out.println("Category ID : " + category.getCategoryId());
            System.out.println("Category Name : " + category.getCategoryName());
        }
    }

    // Update Category
    private void updateCategory() {

        try {

            System.out.print("Enter Category ID : ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter New Category Name : ");
            String name = scanner.nextLine();

            CategoryValidator.validateCategoryName(name);

            Category category = new Category();
            category.setCategoryId(id);
            category.setCategoryName(name);

            if (categoryController.updateCategory(category)) {

                System.out.println("Category Updated Successfully.");

            } else {

                System.out.println("Category Update Failed.");
            }

        } catch (NumberFormatException e) {

            System.out.println("Error : " + e.getMessage());
        }
    }

    // Delete Category
    private void deleteCategory() {

        try {

            System.out.print("Enter Category ID : ");
            int id = Integer.parseInt(scanner.nextLine());

            if (categoryController.deleteCategory(id)) {

                System.out.println("Category Deleted Successfully.");

            } else {

                System.out.println("Category Delete Failed.");
            }

        } catch (NumberFormatException e) {

            System.out.println("Error : " + e.getMessage());
        }
    }
}