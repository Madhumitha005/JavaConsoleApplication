package com.ecommerce.view.admin;

import java.util.Collection;
import java.util.Scanner;

import com.ecommerce.controller.CategoryController;
import com.ecommerce.controller.ProductController;
import com.ecommerce.exception.ValidationException;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.util.InputUtil;
import com.ecommerce.validator.ProductValidator;

public class AdminProductView {

    private final ProductController productController;
    private final CategoryController categoryController;
    private final Scanner scanner;

    // Constructor Injection
    public AdminProductView(ProductController productController,
                            CategoryController categoryController) {

        this.productController = productController;
        this.categoryController = categoryController;
        this.scanner = InputUtil.getInstance().getScanner();
    }

    // Product Menu
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

            } catch (NumberFormatException e) {

                System.out.println("Please Enter a Valid Number.");
            }
        }
    }

    // Add Product
    private void addProduct() {

        try {

            Product product = new Product();

            System.out.print("Product Name : ");
            String productName = scanner.nextLine();
            ProductValidator.validateProductName(productName);
            product.setProductName(productName);

            System.out.print("Price : ");
            double price = Double.parseDouble(scanner.nextLine());
            ProductValidator.validatePrice(price);
            product.setPrice(price);

            System.out.print("Quantity : ");
            int quantity = Integer.parseInt(scanner.nextLine());
            ProductValidator.validateQuantity(quantity);
            product.setQuantity(quantity);

            Category category = selectCategory();

            if (category == null) {
                System.out.println("Invalid Category.");
                return;
            }

            product.setCategory(category);

            if (productController.addProduct(product)) {

                System.out.println("Product Added Successfully.");

            } else {

                System.out.println("Failed To Add Product.");
            }

        } catch (ValidationException e) {

            System.out.println(e.getMessage());

        } catch (NumberFormatException e) {

            System.out.println("Invalid Number.");
        }
    }

    // View Products
    private void viewProducts() {

        Collection<Product> products = productController.viewProducts();

        System.out.println("\n========== PRODUCT LIST ==========");

        if (products == null || products.isEmpty()) {

            System.out.println("No Products Available.");
            return;
        }

        for (Product product : products) {

            System.out.println("--------------------------------");
            System.out.println("Product ID   : " + product.getProductId());
            System.out.println("Name         : " + product.getProductName());
            System.out.println("Price        : ₹" + product.getPrice());
            System.out.println("Quantity     : " + product.getQuantity());

            if (product.getCategory() != null) {

                System.out.println("Category ID  : "
                        + product.getCategory().getCategoryId());

                System.out.println("Category     : "
                        + product.getCategory().getCategoryName());
            }
        }
    }

    // Update Product
    private void updateProduct() {

        try {

            Product product = new Product();

            System.out.print("Product ID : ");
            product.setProductId(Integer.parseInt(scanner.nextLine()));

            System.out.print("Product Name : ");
            String productName = scanner.nextLine();
            ProductValidator.validateProductName(productName);
            product.setProductName(productName);

            System.out.print("Price : ");
            double price = Double.parseDouble(scanner.nextLine());
            ProductValidator.validatePrice(price);
            product.setPrice(price);

            System.out.print("Quantity : ");
            int quantity = Integer.parseInt(scanner.nextLine());
            ProductValidator.validateQuantity(quantity);
            product.setQuantity(quantity);

            Category category = selectCategory();

            if (category == null) {

                System.out.println("Invalid Category.");
                return;
            }

            product.setCategory(category);

            if (productController.updateProduct(product)) {

                System.out.println("Product Updated Successfully.");

            } else {

                System.out.println("Product Update Failed.");
            }

        } catch (ValidationException e) {

            System.out.println(e.getMessage());

        } catch (NumberFormatException e) {

            System.out.println("Invalid Number.");
        }
    }

    // Delete Product
    private void deleteProduct() {

        try {

            System.out.print("Enter Product ID : ");
            int productId = Integer.parseInt(scanner.nextLine());

            if (productController.deleteProduct(productId)) {

                System.out.println("Product Deleted Successfully.");

            } else {

                System.out.println("Product Not Found.");
            }

        } catch (NumberFormatException e) {

            System.out.println("Invalid Product ID.");
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

            return categoryController.searchCategoryById(categoryId);

        } catch (NumberFormatException e) {

            System.out.println("Invalid Category ID.");
            return null;
        }
    }
}