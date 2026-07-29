package com.ecommerce.view.customer;

import java.util.Collection;
import java.util.Objects;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ecommerce.controller.CategoryController;
import com.ecommerce.model.Category;
import com.ecommerce.common.util.InputUtil;

@Component
public class CustomerCategoryView {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerCategoryView.class);

    private final CategoryController categoryController;
    private final Scanner scanner;

    // Constructor Injection
    public CustomerCategoryView(final CategoryController categoryController) {

        this.categoryController = Objects.requireNonNull(categoryController, "CategoryController cannot be null.");
        this.scanner = InputUtil.getInstance().getScanner();
    }

    // Category Menu
    public void show() {

        while (true) {

            System.out.println("\n========== CATEGORY ==========");
            System.out.println("1. View Categories");
            System.out.println("2. Search Category");
            System.out.println("3. Back");
            System.out.println("----------------------");
            System.out.print("Enter Choice : ");

            try {

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1 -> viewCategories();

                    case 2 -> searchCategory();

                    case 3 -> {

                        LOGGER.info("Customer exited Category View.");
                        return;
                    }
                    default ->
                            System.out.println("Invalid Choice.");
                }

            } catch (NumberFormatException exception) {

                LOGGER.error("Invalid Menu Choice.", exception);

                System.out.println("Please Enter a Valid Number.");
            }
        }
    }

    // View All Categories
    private void viewCategories() {

        try {

            LOGGER.info("Loading Categories...");

            Collection<Category> categories = categoryController.viewCategories();

            System.out.println("\n========== CATEGORY LIST ==========");

            if (categories == null || categories.isEmpty()) {

                System.out.println("No Categories Available.");
                return;
            }

            for (Category category : categories) {

                displayCategory(category);
            }

            LOGGER.info("Categories Loaded Successfully.");

        } catch (Exception exception) {

            LOGGER.error("Error Loading Categories.", exception);

            System.out.println("Error : " + exception.getMessage()
            );
        }
    }

    // Search Category
    private void searchCategory() {

        try {

            System.out.print("Enter Category ID : ");

            int categoryId = Integer.parseInt(scanner.nextLine());
            Category category = categoryController.getCategoryById(categoryId);

            if (category == null) {

                System.out.println("Category Not Found.");
                return;
            }

            System.out.println("\n========== CATEGORY DETAILS ==========");

            displayCategory(category);

        } catch (NumberFormatException exception) {

            System.out.println("Invalid Category ID.");

        } catch (Exception exception) {

            LOGGER.error("Error Searching Category.", exception);

            System.out.println("Error : " + exception.getMessage()
            );
        }
    }

    // Display Category
    private void displayCategory(final Category category) {

        System.out.println("Category ID   : " + category.getCategoryId());
        System.out.println("Category Name : " + category.getCategoryName());
    }
}