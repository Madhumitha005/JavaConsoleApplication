package com.ecommerce.view.admin;

import com.ecommerce.util.InputUtil;
import java.util.Scanner;

public class AdminDashboardView {

    private final AdminCategoryView adminCategoryView;
    private final AdminProductView adminProductView;
    private final AdminOrderView adminOrderView;
    private final Scanner scanner;

    // Constructor Injection
    public AdminDashboardView(AdminCategoryView adminCategoryView,
                              AdminProductView adminProductView,
                              AdminOrderView adminOrderView) {

        this.adminCategoryView = adminCategoryView;
        this.adminProductView = adminProductView;
        this.adminOrderView = adminOrderView;
        this.scanner = InputUtil.getInstance().getScanner();
    }

    // Admin Dashboard
    public void show() {

        while (true) {

            System.out.println("\n========== ADMIN DASHBOARD ==========");
            System.out.println("1. Category Management");
            System.out.println("2. Product Management");
            System.out.println("3. View Orders");
            System.out.println("4. Logout");
            System.out.print("Enter Choice : ");

            int choice;

            try {

                choice = Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {

                System.out.println("Invalid Choice.");
                continue;
            }

            switch (choice) {

                case 1 -> adminCategoryView.show();

                case 2 -> adminProductView.show();

                case 3 -> adminOrderView.show();

                case 4 -> {
                    System.out.println("Logout Successful.");
                    return;
                }

                default -> System.out.println("Invalid Choice.");
            }
        }
    }
}