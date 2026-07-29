package com.ecommerce.view.customer;

import java.util.Collection;
import java.util.Objects;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ecommerce.common.util.InputUtil;
import com.ecommerce.controller.SubCategoryController;
import com.ecommerce.model.SubCategory;

@Component
public class CustomerSubCategoryView {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerSubCategoryView.class);

    private final SubCategoryController subCategoryController;
    private final Scanner scanner;

    public CustomerSubCategoryView(final SubCategoryController subCategoryController) {

        this.subCategoryController = Objects.requireNonNull(subCategoryController, "SubCategoryController cannot be null.");
        this.scanner = InputUtil.getInstance().getScanner();
    }

    public void show() {

        while (true) {

            System.out.println("\n========== SUB CATEGORY ==========");
            System.out.println("1. View All Sub Categories");
            System.out.println("2. Search Sub Category");
            System.out.println("3. Back");
            System.out.print("Enter Choice : ");

            try {

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1 -> {
                        viewSubCategories();
                    }
                    case 2 -> {
                        searchSubCategory();
                    }
                    case 3 -> {
                        LOGGER.info("Customer exited SubCategory View.");
                        return;
                    }

                    default -> {
                        System.out.println("Invalid Choice.");
                    }
                }

            } catch (NumberFormatException exception) {

                LOGGER.error("Invalid Menu Choice.", exception);

                System.out.println("Please Enter a Valid Number.");
            }
        }
    }

    private void viewSubCategories() {

        try {

            LOGGER.info("Loading Sub Categories...");

            Collection<SubCategory> subCategories = subCategoryController.viewSubCategories();

            System.out.println("\n========== SUB CATEGORY LIST ==========");

            if (subCategories == null || subCategories.isEmpty()) {

                System.out.println("No Sub Categories Available.");

                return;
            }

            for (SubCategory subCategory : subCategories) {

                if (subCategory != null) {

                    displaySubCategory(subCategory);
                }
            }

            LOGGER.info("Sub Categories Loaded Successfully.");

        } catch (Exception exception) {

            LOGGER.error("Unable To Load Sub Categories.", exception);

            System.out.println("Error : " + exception.getMessage());
        }
    }

    private void searchSubCategory() {

        try {

            System.out.print("Enter Sub Category ID : ");

            int subCategoryId = Integer.parseInt(scanner.nextLine());

            SubCategory subCategory = subCategoryController.getSubCategoryById(subCategoryId);

            if (subCategory == null) {

                System.out.println("Sub Category Not Found.");

                return;
            }

            System.out.println("\n========== SUB CATEGORY DETAILS ==========");

            displaySubCategory(subCategory);

        } catch (NumberFormatException exception) {

            LOGGER.error("Invalid Sub Category ID.", exception);

            System.out.println("Invalid Sub Category ID.");

        } catch (Exception exception) {

            LOGGER.error("Unable To Search Sub Category.", exception);

            System.out.println("Error : " + exception.getMessage());
        }
    }

    private void displaySubCategory(final SubCategory subCategory) {

        if (subCategory == null) {
            return;
        }

        System.out.println("Sub Category ID : " + subCategory.getSubCategoryId());
        System.out.println("Sub Category Name : " + subCategory.getSubCategoryName());

        if (subCategory.getCategory() != null) {

            System.out.println("Category : " + subCategory.getCategory().getCategoryName());
        }
    }
}