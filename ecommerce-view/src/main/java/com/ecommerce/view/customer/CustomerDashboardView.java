package com.ecommerce.view.customer;

import java.util.Objects;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ecommerce.common.util.InputUtil;
import com.ecommerce.controller.ProductController;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;

@Component
public class CustomerDashboardView {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDashboardView.class);

    private final ProductView productView;
    private final CartView cartView;
    private final OrderView orderView;
    private final ProductController productController;
    private final Scanner scanner;

    public CustomerDashboardView(
            final ProductView productView,
            final CartView cartView,
            final OrderView orderView,
            final ProductController productController) {

        this.productView = Objects.requireNonNull(productView, "ProductView cannot be null.");
        this.cartView = Objects.requireNonNull(cartView, "CartView cannot be null.");
        this.orderView = Objects.requireNonNull(orderView, "OrderView cannot be null.");
        this.productController = Objects.requireNonNull(productController, "ProductController cannot be null.");
        this.scanner = InputUtil.getInstance().getScanner();
    }

    public void show(final User user) {

        if (user == null) {

            System.out.println("Invalid User.");
            return;
        }

        LOGGER.info("Customer {} Logged In.", user.getEmail());

        while (true) {

            System.out.println("\n========== CUSTOMER DASHBOARD ==========");

            System.out.println("Welcome : " + user.getName());
            System.out.println("1. View Products");
            System.out.println("2. Search Product");
            System.out.println("3. Add To Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Place Order");
            System.out.println("6. View Orders");
            System.out.println("7. Return Product");
            System.out.println("8. Logout");
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
                    case 7 -> orderView.returnProduct(user.getId());
                    case 8 -> {

                        LOGGER.info("Customer {} Logged Out.", user.getEmail());

                        System.out.println("Logout Successful.");

                        return;
                    }
                    default -> System.out.println("Invalid Choice.");
                }

            } catch (NumberFormatException exception) {

                LOGGER.error("Invalid Menu Choice.", exception);

                System.out.println("Please Enter a Valid Number.");

            } catch (Exception exception) {

                LOGGER.error("Dashboard Error.", exception);

                System.out.println("Error : " + exception.getMessage());
            }
        }
    }

    private void searchProduct() {

        System.out.print("Enter Product Name : ");

        String productName = scanner.nextLine();
        productView.searchProduct(productName);
    }

    private void addToCart(final User user) {

        try {

            System.out.print("Enter Product ID : ");

            int productId = Integer.parseInt(scanner.nextLine());

            if (productId <= 0) {

                System.out.println("Product ID must be greater than 0.");
                return;
            }

            Product product = productController.findById(productId);

            if (product == null) {

                LOGGER.warn("Product not found for productId: {}", productId);

                System.out.println("Product Not Found.");

                return;
            }

            System.out.print("Enter Quantity : ");

            int quantity = Integer.parseInt(scanner.nextLine());

            if (quantity <= 0) {

                System.out.println("Quantity must be greater than 0.");
                return;
            }

            Cart cart = new Cart();

            cart.setUserId(user.getId());
            CartItem cartItem = new CartItem();
            cartItem.setProductId(productId);
            cartItem.setQuantity(quantity);

            cartView.addToCart(cart, cartItem);

            LOGGER.info("Product {} added to cart by user {}.", productId, user.getEmail());

        } catch (NumberFormatException exception) {

            LOGGER.error("Invalid Product Input.", exception);

            System.out.println("Please Enter Valid Numbers.");

        } catch (Exception exception) {

            LOGGER.error("Unable To Add Product To Cart.", exception);

            System.out.println("Error : " + exception.getMessage());
        }
    }
}