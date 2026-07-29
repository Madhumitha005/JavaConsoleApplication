package com.ecommerce.view.admin;

import java.util.Objects;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.ecommerce.common.util.InputUtil;

@Component
public class AdminDashboardView {

    private final AdminCategoryView adminCategoryView;
    private final AdminSubCategoryView adminSubCategoryView;
    private final AdminProductView adminProductView;
    private final AdminOrderView adminOrderView;
    private final Scanner scanner;

    public AdminDashboardView(
            final AdminCategoryView adminCategoryView,
            final AdminSubCategoryView adminSubCategoryView,
            final AdminProductView adminProductView,
            final AdminOrderView adminOrderView) {

        this.adminCategoryView = Objects.requireNonNull(adminCategoryView, "AdminCategoryView cannot be null.");
        this.adminSubCategoryView = Objects.requireNonNull(adminSubCategoryView, "AdminSubCategoryView cannot be null.");
        this.adminProductView = Objects.requireNonNull(adminProductView, "AdminProductView cannot be null.");
        this.adminOrderView = Objects.requireNonNull(adminOrderView, "AdminOrderView cannot be null.");
        this.scanner = InputUtil.getInstance().getScanner();
    }

    public void show() {

        while (true) {

            System.out.println("\n========== ADMIN DASHBOARD ==========");
            System.out.println("1. Category Management");
            System.out.println("2. Sub Category Management");
            System.out.println("3. Product Management");
            System.out.println("4. View Orders");
            System.out.println("5. Logout");
            System.out.println("---------------------------------------");
            System.out.print("Enter Choice : ");

            try {

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1 -> {
                        adminCategoryView.show();
                    }
                    case 2 -> {
                        adminSubCategoryView.show();
                    }
                    case 3 -> {
                        adminProductView.show();
                    }
                    case 4 -> {
                        adminOrderView.show();
                    }
                    case 5 -> {
                        System.out.println("Logout Successful.");

                        return;
                    }
                    default -> {
                        System.out.println("Invalid Choice.");
                    }
                }

            } catch (NumberFormatException exception) {

                System.out.println("Please Enter a Valid Number.");
            }
        }
    }
}