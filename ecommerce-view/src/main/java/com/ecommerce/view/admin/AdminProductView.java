package com.ecommerce.view.admin;

import java.util.Collection;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.ecommerce.common.enums.ProductStatus;
import com.ecommerce.common.util.InputUtil;
import com.ecommerce.controller.CategoryController;
import com.ecommerce.controller.ProductController;
import com.ecommerce.controller.SubCategoryController;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.model.SubCategory;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@Component
public class AdminProductView {

    private final ProductController productController;
    private final CategoryController categoryController;
    private final SubCategoryController subCategoryController;
    private final Scanner scanner;

    public AdminProductView(
            final ProductController productController,
            final CategoryController categoryController,
            final SubCategoryController subCategoryController) {

        this.productController = Objects.requireNonNull(productController, "Product Controller cannot be null.");
        this.categoryController = Objects.requireNonNull(categoryController, "CategoryController cannot be null.");
        this.subCategoryController = Objects.requireNonNull(subCategoryController, "SubcategoryController cannot be null");
        this.scanner = InputUtil.getInstance().getScanner();
    }

    public void show() {

        while (true) {

            System.out.println("\n========== PRODUCT MANAGEMENT ==========");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Back");
            System.out.print("Enter Choice : ");

            try {

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> addProduct();
                    case 2 -> viewProducts();
                    case 3 -> updateProduct();
                    case 4 -> deleteProduct();
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

    private void addProduct() {

        try {

            Product product = new Product();

            System.out.println("========================");

            System.out.print("Product Name : ");

            product.setProductName(scanner.nextLine());

            System.out.print("Price : ");

            product.setPrice(Double.parseDouble(scanner.nextLine()));

            System.out.print("Quantity : ");

            product.setQuantity(Integer.parseInt(scanner.nextLine()));

            System.out.println("Discount Percentage" + "(Enter 0 or No Discount : )");

            product.setDiscountPercentage(Double.parseDouble(scanner.nextLine()));

            Category category = selectCategory();

            if (category == null) {

                System.out.println("Invalid Category.");

                return;
            }

            product.setCategory(category);
            SubCategory subCategory = selectSubCategory(category.getCategoryId());

            if (subCategory == null) {

                System.out.println("Invalid Sub Category.");

                return;
            }

            product.setSubCategory(subCategory);
            ProductStatus productStatus = selectProductStatus();

            if (productStatus == null) {

                System.out.println("Invalid Product Status.");

                return;
            }

            product.setProductStatus(productStatus);

            if (!validateProduct(product)) {

                return;
            }

            if (productController.save(product)) {

                System.out.println("Product Added Successfully.");

            } else {

                System.out.println("Failed To Add Product.");
            }

        } catch (NumberFormatException exception) {

            System.out.println("Invalid Number.");

        } catch (Exception exception) {

            System.out.println("Error : " + exception.getMessage());
        }
    }

    private void viewProducts() {

        try {

            Collection<Product> products = productController.findAll();

            System.out.println("\n========== PRODUCT LIST ==========");

            if (products == null || products.isEmpty()) {

                System.out.println("No Products Available.");

                return;
            }

            for (Product product : products) {

                if (product == null) {
                    continue;
                }

                System.out.println("Product ID       : " + product.getProductId());
                System.out.println("Product Name     : " + product.getProductName());
                System.out.println("Price            : " + product.getPrice());

                if (product.getDiscountPercentage() > 0) {

                    System.out.println("Discount           : " + product.getDiscountPercentage() + "%");
                } else {

                    System.out.println("Discount      : No Discount");
                }

                System.out.println("Quantity          : " + product.getQuantity());

                if (product.getCategory() != null) {

                    System.out.println("Category ID      : " + product.getCategory().getCategoryId());
                    System.out.println("Category         : " + product.getCategory().getCategoryName());
                }

                if (product.getSubCategory() != null) {

                    System.out.println("Sub Category ID  : " + product.getSubCategory().getSubCategoryId());
                    System.out.println("Sub Category     : " + product.getSubCategory().getSubCategoryName()
                    );
                }

                System.out.println("Status           : " + product.getProductStatus());
            }

        } catch (Exception exception) {

            System.out.println("Error : " + exception.getMessage());
        }
    }

    private void updateProduct() {

        try {

            System.out.print("Product ID : ");

            int productId = Integer.parseInt(scanner.nextLine());

            Product existingProduct = productController.findById(productId);

            if (existingProduct == null) {

                System.out.println("Product Not Found.");

                return;
            }

            Product product = new Product();
            product.setProductId(productId);

            System.out.print("Product Name : ");

            product.setProductName(scanner.nextLine());

            System.out.print("Price : ");

            product.setPrice(Double.parseDouble(scanner.nextLine()));

            System.out.print("Quantity : ");

            product.setQuantity(Integer.parseInt(scanner.nextLine()));

            System.out.println("Discount Percentage " + "(Enter 0 for No Discount) :");

            product.setDiscountPercentage(Double.parseDouble(scanner.nextLine()));

            Category category = selectCategory();

            if (category == null) {

                System.out.println("Invalid Category.");

                return;
            }
            product.setCategory(category);
            SubCategory subCategory = selectSubCategory(category.getCategoryId());

            if (subCategory == null) {

                System.out.println("Invalid Sub Category.");

                return;
            }
            product.setSubCategory(subCategory);
            ProductStatus productStatus = selectProductStatus();

            if (productStatus == null) {

                System.out.println("Invalid Product Status.");

                return;
            }

            product.setProductStatus(productStatus);

            if (!validateProduct(product)) {

                return;
            }

            if (productController.update(product)) {

                System.out.println("Product Updated Successfully.");

            } else {

                System.out.println("Product Update Failed.");
            }

        } catch (NumberFormatException exception) {

            System.out.println("Invalid Number.");

        } catch (Exception exception) {

            System.out.println("Error : " + exception.getMessage());
        }
    }

    private void deleteProduct() {

        try {

            System.out.print("Enter Product ID : ");

            int productId = Integer.parseInt(scanner.nextLine());

            if (productController.delete(productId)) {

                System.out.println("Product Deleted Successfully.");

            } else {

                System.out.println("Product Delete Failed.");
            }

        } catch (NumberFormatException exception) {

            System.out.println("Invalid Product ID.");

        } catch (Exception exception) {

            System.out.println("Error : " + exception.getMessage());
        }
    }

    private Category selectCategory() {

        Collection<Category> categories = categoryController.viewCategories();

        if (categories == null || categories.isEmpty()) {

            System.out.println("No Categories Available.");

            return null;
        }

        System.out.println("\n========== AVAILABLE CATEGORIES ==========");

        for (Category category : categories) {

            System.out.println(category.getCategoryId() + " - " + category.getCategoryName());
        }

        try {

            System.out.print("Enter Category ID : ");

            int categoryId = Integer.parseInt(scanner.nextLine());
            Category category = categoryController.getCategoryById(categoryId);

            if (category == null) {

                System.out.println("Category Not Found.");

                return null;
            }

            return category;

        } catch (NumberFormatException exception) {

            System.out.println("Invalid Category ID.");

            return null;
        }
    }

    private SubCategory selectSubCategory(final int categoryId) {

        Collection<SubCategory> subCategories = subCategoryController.viewSubCategoriesByCategoryId(categoryId);

        if (subCategories == null || subCategories.isEmpty()) {

            System.out.println("No Sub Categories Available " + "for this Category.");

            return null;
        }

        System.out.println("\n========== AVAILABLE SUB CATEGORIES ==========");

        for (SubCategory subCategory : subCategories) {

            System.out.println(subCategory.getSubCategoryId() + " - " + subCategory.getSubCategoryName());
        }

        try {

            System.out.print("Enter Sub Category ID : ");

            int subCategoryId = Integer.parseInt(scanner.nextLine());
            SubCategory subCategory = subCategoryController.getSubCategoryById(subCategoryId);

            if (subCategory == null) {

                System.out.println("Sub Category Not Found.");

                return null;
            }

            if (subCategory.getCategory() == null) {

                System.out.println("Sub Category has no Category.");

                return null;
            }

            if (subCategory.getCategory().getCategoryId() != categoryId) {

                System.out.println("Sub Category does not belong " + "to selected Category.");

                return null;
            }

            return subCategory;

        } catch (NumberFormatException exception) {

            System.out.println("Invalid Sub Category ID.");

            return null;
        }
    }

    private ProductStatus selectProductStatus() {

        System.out.println("\n========== PRODUCT STATUS ==========");

        ProductStatus[] statuses = ProductStatus.values();

        for (int index = 0; index < statuses.length; index++) {

            System.out.println((index + 1) + ". " + statuses[index]);
        }

        System.out.print("Enter Status : ");

        try {

            int choice = Integer.parseInt(scanner.nextLine());

            if (choice < 1 || choice > statuses.length) {

                System.out.println("Invalid Product Status.");

                return null;
            }

            return statuses[choice - 1];

        } catch (NumberFormatException exception) {

            System.out.println("Invalid Product Status.");

            return null;
        }
    }

    private boolean validateProduct(final Product product) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

        try {

            Validator validator = factory.getValidator();
            Set<ConstraintViolation<Product>> violations = validator.validate(product);

            if (violations.isEmpty()) {

                return true;
            }

            for (ConstraintViolation<Product> violation : violations) {

                System.out.println(violation.getMessage());
            }
            return false;

        } finally {
            factory.close();
        }
    }
}