package com.ecommerce.view.admin;

import com.ecommerce.controller.CategoryController;
import com.ecommerce.controller.SubCategoryController;
import com.ecommerce.common.util.InputUtil;
import com.ecommerce.model.Category;
import com.ecommerce.model.SubCategory;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Collection;
import java.util.Scanner;
import java.util.Set;

@Component
public class AdminSubCategoryView {

    private final SubCategoryController subCategoryController;
    private final CategoryController categoryController;
    private final Scanner scanner;

    // Constructor Injection
    public AdminSubCategoryView(
            final SubCategoryController subCategoryController,
            final CategoryController categoryController) {

        this.subCategoryController = subCategoryController;
        this.categoryController = categoryController;
        this.scanner = InputUtil.getInstance().getScanner();
    }

    // Menu
    public void show() {

        while (true) {

            System.out.println("\n========== SUB CATEGORY MANAGEMENT ==========");
            System.out.println("1. Add Sub Category");
            System.out.println("2. View Sub Categories");
            System.out.println("3. Update Sub Category");
            System.out.println("4. Delete Sub Category");
            System.out.println("5. Back");
            System.out.print("Enter Choice : ");

            try {

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1 -> addSubCategory();
                    case 2 -> viewSubCategories();
                    case 3 -> updateSubCategory();
                    case 4 -> deleteSubCategory();
                    case 5 -> {
                        return;
                    }
                    default -> System.out.println("Invalid Choice.");
                }

            } catch (NumberFormatException exception) {

                System.out.println("Please Enter a Valid Number.");
            }
        }
    }

    // Add
    private void addSubCategory() {

        try {

            SubCategory subCategory = new SubCategory();

            System.out.print("Sub Category Name : ");
            subCategory.setSubCategoryName(scanner.nextLine());

            Category category = selectCategory();

            if (category == null) {

                System.out.println("Invalid Category.");
                return;
            }

            subCategory.setCategory(category);

            if (!validateSubCategory(subCategory)) {
                return;
            }

            if (subCategoryController.addSubCategory(subCategory)) {

                System.out.println("Sub Category Added Successfully.");

            } else {

                System.out.println("Failed To Add Sub Category.");
            }

        } catch (Exception exception) {

            System.out.println("Error : " + exception.getMessage());
        }
    }

    // View
    private void viewSubCategories() {

        Collection<SubCategory> subCategories =
                subCategoryController.viewSubCategories();

        System.out.println("\n========== SUB CATEGORY LIST ==========");

        if (subCategories == null || subCategories.isEmpty()) {

            System.out.println("No Sub Categories Found.");
            return;
        }

        for (SubCategory subCategory : subCategories) {

            System.out.println("--------------------------------");
            System.out.println("Sub Category ID : "
                    + subCategory.getSubCategoryId());

            System.out.println("Sub Category : "
                    + subCategory.getSubCategoryName());

            if (subCategory.getCategory() != null) {

                System.out.println("Category ID : "
                        + subCategory.getCategory().getCategoryId());

                System.out.println("Category : "
                        + subCategory.getCategory().getCategoryName());
            }
        }
    }

    // Update
    private void updateSubCategory() {

        try {

            SubCategory subCategory = new SubCategory();

            System.out.print("Sub Category ID : ");
            subCategory.setSubCategoryId(
                    Integer.parseInt(scanner.nextLine()));

            System.out.print("New Sub Category Name : ");
            subCategory.setSubCategoryName(scanner.nextLine());

            Category category = selectCategory();

            if (category == null) {

                System.out.println("Invalid Category.");
                return;
            }

            subCategory.setCategory(category);

            if (!validateSubCategory(subCategory)) {
                return;
            }

            if (subCategoryController.updateSubCategory(subCategory)) {

                System.out.println("Sub Category Updated Successfully.");

            } else {

                System.out.println("Update Failed.");
            }

        } catch (Exception exception) {

            System.out.println("Error : " + exception.getMessage());
        }
    }

    // Delete
    private void deleteSubCategory() {

        try {

            System.out.print("Enter Sub Category ID : ");

            int id = Integer.parseInt(scanner.nextLine());

            if (subCategoryController.deleteSubCategory(id)) {

                System.out.println("Sub Category Deleted Successfully.");

            } else {

                System.out.println("Delete Failed.");
            }

        } catch (Exception exception) {

            System.out.println("Error : " + exception.getMessage());
        }
    }

    // Select Category
    private Category selectCategory() {

        Collection<Category> categories =
                categoryController.viewCategories();

        if (categories == null || categories.isEmpty()) {

            System.out.println("No Categories Available.");
            return null;
        }

        System.out.println("\n========== AVAILABLE CATEGORIES ==========");

        for (Category category : categories) {

            System.out.println(
                    category.getCategoryId()
                            + " - "
                            + category.getCategoryName()
            );
        }

        try {

            System.out.print("Enter Category ID : ");

            int categoryId = Integer.parseInt(scanner.nextLine());

            return categoryController.getCategoryById(categoryId);

        } catch (Exception exception) {

            return null;
        }
    }

    // Hibernate Validator
    private boolean validateSubCategory(
            final SubCategory subCategory) {

        ValidatorFactory factory =
                Validation.buildDefaultValidatorFactory();

        Validator validator = factory.getValidator();

        Set<ConstraintViolation<SubCategory>> violations =
                validator.validate(subCategory);

        if (violations.isEmpty()) {

            factory.close();
            return true;
        }

        for (ConstraintViolation<SubCategory> violation : violations) {

            System.out.println(
                    violation.getMessage());
        }

        factory.close();
        return false;
    }
}