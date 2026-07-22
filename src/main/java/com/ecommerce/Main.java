package com.ecommerce;

import java.util.Scanner;

import com.ecommerce.controller.AuthController;
import com.ecommerce.controller.CartController;
import com.ecommerce.controller.CartItemController;
import com.ecommerce.controller.CategoryController;
import com.ecommerce.controller.OrderController;
import com.ecommerce.controller.OrderItemController;
import com.ecommerce.controller.ProductController;
import com.ecommerce.factory.ServiceFactory;
import com.ecommerce.service.CartItemService;
import com.ecommerce.service.CartService;
import com.ecommerce.service.CategoryService;
import com.ecommerce.service.OrderItemService;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.ProductService;
import com.ecommerce.util.InputUtil;
import com.ecommerce.view.LoginView;
import com.ecommerce.view.SignupView;
import com.ecommerce.view.admin.AdminCategoryView;
import com.ecommerce.view.admin.AdminDashboardView;
import com.ecommerce.view.admin.AdminOrderView;
import com.ecommerce.view.admin.AdminProductView;
import com.ecommerce.view.customer.CartView;
import com.ecommerce.view.customer.CustomerDashboardView;
import com.ecommerce.view.customer.OrderView;
import com.ecommerce.view.customer.ProductView;

public class Main {

    public static void main(String[] args) {

        //Singleton Pttern to avoid tight coupling
        Scanner scanner = InputUtil.getInstance().getScanner();

        // Services
        ProductService productService =
                ServiceFactory.getProductService();
        CategoryService categoryService =
                ServiceFactory.getCategoryService();
        CartService cartService =
                ServiceFactory.getCartService();
        CartItemService cartItemService =
                ServiceFactory.getCartItemService();
        OrderService orderService =
                ServiceFactory.getOrderService();
        OrderItemService orderItemService =
                ServiceFactory.getOrderItemService();

        // Controllers and it send all request to service from view
        AuthController authController =
                new AuthController();
        ProductController productController =
                new ProductController(productService);
        CategoryController categoryController =
                new CategoryController(categoryService);
        CartController cartController =
                new CartController(cartService);
        CartItemController cartItemController =
                new CartItemController(cartItemService);
        OrderController orderController =
                new OrderController(orderService);
        OrderItemController orderItemController =
                new OrderItemController(orderItemService);

        // Admin Views
        AdminCategoryView adminCategoryView =
                new AdminCategoryView(categoryController);
        AdminProductView adminProductView =
                new AdminProductView(
                        productController,
                        categoryController
                );
        AdminOrderView adminOrderView =
                new AdminOrderView(
                        orderController,
                        orderItemController
                );
        AdminDashboardView adminDashboardView =
                new AdminDashboardView(
                        adminCategoryView,
                        adminProductView,
                        adminOrderView
                );
  
        // Customer Views
        ProductView productView =
                new ProductView(productController);
        CartView cartView =
                new CartView(
                        cartController,
                        cartItemController);
        OrderView orderView =
                new OrderView(
                        orderController,
                        orderItemController,
                        cartController,
                        cartItemController
                );
        CustomerDashboardView customerDashboardView =
                new CustomerDashboardView(
                        productView,
                        cartView,
                        orderView
                );

        // Authentication Views
        SignupView signupView =
                new SignupView(authController);
        LoginView loginView =
                new LoginView(
                        authController,
                        adminDashboardView,
                        customerDashboardView
                );

        // Main menu
        while (true) {

            System.out.println("\n========== E-COMMERCE SYSTEM ==========");
            System.out.println("1. Signup");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter Choice : ");

            try {

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1 -> signupView.show();

                    case 2 -> loginView.show();

                    case 3 -> {
                        System.out.println("Thank You. Visit Again.");
                        scanner.close();
                        return;
                    }

                    default -> System.out.println("Invalid Choice.");
                }

            } catch (NumberFormatException e) {

                System.out.println("Please Enter a Valid Number.");
            }
        }
    }
}