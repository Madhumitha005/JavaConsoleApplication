package com.ecommerce.view.customer;

import java.util.Scanner;

import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.User;
import com.ecommerce.util.InputUtil;

public class CustomerDashboardView {

    private final ProductView productView;
    private final CartView cartView;
    private final OrderView orderView;
    private final Scanner scanner;

    // Constructor Injection
    public CustomerDashboardView(ProductView productView,
                                 CartView cartView,
                                 OrderView orderView) {

        this.productView = productView;
        this.cartView = cartView;
        this.orderView = orderView;
        this.scanner = InputUtil.getInstance().getScanner();
    }

    // Customer Dashboard
    public void show(User user) {

        if (user == null) {
            System.out.println("Invalid User.");
            return;
        }

        while (true) {

            System.out.println("\n========== CUSTOMER DASHBOARD ==========");
            System.out.println("Welcome : " + user.getName());
            System.out.println("1. View Products");
            System.out.println("2. Search Product");
            System.out.println("3. Add To Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Place Order");
            System.out.println("6. View Orders");
            System.out.println("7. Logout");
            System.out.print("Enter Choice : ");

            try {

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1 -> productView.showProducts();

                    case 2 -> searchProduct();

                    case 3 -> addToCart(user);

                    case 4 -> cartView.showCart(user.getId());

                    case 5 -> orderView.placeOrder(user.getId());

                    case 6 -> orderView.viewOrders(user.getId());

                    case 7 -> {
                        System.out.println("Logout Successful.");
                        return;
                    }

                    default -> System.out.println("Invalid Choice.");
                }

            } catch (NumberFormatException e) {

                System.out.println("Please Enter a Valid Number.");

            } catch (Exception e) {

                System.out.println("Error : " + e.getMessage());
            }
        }
    }

    // Search Product
    private void searchProduct() {

        System.out.print("Enter Product Name : ");
        String productName = scanner.nextLine();

        productView.searchProduct(productName);
    }

    // Add To Cart
    private void addToCart(User user) {

        try {

            System.out.print("Enter Product ID : ");
            int productId = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Quantity : ");
            int quantity = Integer.parseInt(scanner.nextLine());

            // Create Cart
            Cart cart = new Cart();
            System.out.println("User ID = " + user.getId());
            System.out.println("User Name = " + user.getName());
            
            cart.setUserId(user.getId());

            // Create Cart Item
            CartItem cartItem = new CartItem();
            cartItem.setProductId(productId);
            cartItem.setQuantity(quantity);

            // Send to Cart View
            cartView.addToCart(cart, cartItem);

        } catch (NumberFormatException e) {

            System.out.println("Invalid Input.");

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }
    }
}