package com.ecommerce.view.admin;

import java.util.Collection;
import java.util.Objects;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ecommerce.common.enums.OrderStatus;
import com.ecommerce.common.util.InputUtil;
import com.ecommerce.controller.OrderController;
import com.ecommerce.controller.OrderItemController;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;

@Component
public class AdminOrderView {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminOrderView.class);

    private final OrderController orderController;
    private final OrderItemController orderItemController;
    private final Scanner scanner;

    // Constructor Injection
    public AdminOrderView(
            final OrderController orderController,
            final OrderItemController orderItemController) {

        this.orderController = Objects.requireNonNull(orderController, "OrderController cannot be null");
        this.orderItemController = Objects.requireNonNull(orderItemController, "OrderItemController cannot be null");
        this.scanner = InputUtil.getInstance().getScanner();
    }

    // Admin Order Menu
    public void show() {

        while (true) {

            System.out.println("\n========== ORDER MANAGEMENT ==========");
            System.out.println("1. View All Orders");
            System.out.println("2. Update Order Status");
            System.out.println("3. Back");
            System.out.print("Enter Choice : ");

            try {

                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {

                    case 1 -> viewAllOrders();
                    case 2 -> updateOrderStatus();
                    case 3 -> {
                        return;
                    }
                    default ->
                            System.out.println("Invalid Choice.");
                }
            } catch (NumberFormatException exception) {

                System.out.println("Please Enter a Valid Number.");
            }
        }
    }

    // View All Orders
    private void viewAllOrders() {

        try {

            Collection<Order> orders = orderController.viewAllOrders();

            System.out.println("\n========== ALL ORDERS ==========");

            if (orders == null || orders.isEmpty()) {

                System.out.println("No Orders Found.");

                return;
            }

            for (Order order : orders) {

                if (order == null) {

                    continue;
                }

                displayOrder(order);

                Collection<OrderItem> items = orderItemController.getOrderItemsByOrderId(order.getOrderId());

                if (items != null && !items.isEmpty()) {

                    System.out.println("\nItems:");

                    for (OrderItem item : items) {

                        if (item == null) {

                            continue;
                        }

                        displayOrderItem(item);
                    }
                } else {

                    System.out.println("\nNo Items Found.");
                }
            }

            LOGGER.info("Orders loaded successfully.");

        } catch (Exception exception) {

            LOGGER.error("Error loading orders.", exception);

            System.out.println("Error : " + exception.getMessage());
        }
    }

    // Update Order Status
    private void updateOrderStatus() {

        try {

            System.out.println("\n========== UPDATE ORDER STATUS ==========");
            System.out.print("Enter Order ID : ");

            int orderId = Integer.parseInt(scanner.nextLine());

            if (orderId <= 0) {

                System.out.println("Order ID must be greater than 0.");

                return;
            }

            Order order = orderController.findOrderById(orderId);

            if (order == null) {

                System.out.println("Order Not Found.");

                return;
            }

            System.out.println("\nCurrent Order Status : " + order.getOrderStatus());

            displayOrderStatusMenu();

            int statusId;

            while (true) {

                System.out.print("Select New Status : ");

                try {

                    statusId = Integer.parseInt(scanner.nextLine());

                    if (statusId >= 1 && statusId <= 13) {

                        break;
                    }

                    System.out.println("Please Select a Status " + "From 1 to 13."
                    );

                } catch (NumberFormatException exception) {

                    System.out.println("Please Enter a Valid Number.");
                }
            }

            OrderStatus newStatus = OrderStatus.fromId(statusId);
            order.setOrderStatus(newStatus);

            boolean updated = orderController.updateOrder(order);

            if (updated) {

                System.out.println("\nOrder Status Updated " + "Successfully.");
                System.out.println("New Status : " + newStatus);

                LOGGER.info("Order status updated. " + "Order ID: {}, " + "Status: {}", orderId, newStatus);

            } else {

                System.out.println("\nOrder Status " + "Update Failed.");
            }

        } catch (NumberFormatException exception) {

            System.out.println("Please Enter a Valid " + "Order ID.");

        } catch (Exception exception) {

            LOGGER.error("Error updating order status.", exception);

            System.out.println("Error : " + exception.getMessage());
        }
    }

    // Display Order Status Menu
    private void displayOrderStatusMenu() {

        System.out.println("\nAvailable Order Status:");
        System.out.println("1. PENDING");
        System.out.println("2. CONFIRMED");
        System.out.println("3. PACKED");
        System.out.println("4. SHIPPED");
        System.out.println("5. OUT FOR DELIVERY");
        System.out.println("6. DELIVERED");
        System.out.println("7.  RETURN REQUESTED");
        System.out.println("8.  RETURN APPROVED");
        System.out.println("9.  RETURN REJECTED");
        System.out.println("10. RETURN PICKED");
        System.out.println("11. RETURN COMPLETED");
        System.out.println("12. REFUNDED");
        System.out.println("13. CANCELLED");
    }

    // Display Order
    private void displayOrder(final Order order) {

        System.out.println("\nOrder ID      : " + order.getOrderId());
        System.out.println("User ID         : " + order.getUserId());
        System.out.println("Customer Name   : " + order.getCustomerName());
        System.out.println("Phone           : " + order.getPhone());
        System.out.println("Address         : " + order.getAddress());
        System.out.println("Total Amount    : $" + order.getTotalAmount());
        System.out.println("Order Status    : " + order.getOrderStatus());
    }

    // Display Order Item
    private void displayOrderItem(final OrderItem item) {

        System.out.println("Product ID : " + item.getProductId());
        System.out.println("Quantity   : " + item.getQuantity());
        System.out.println("Price      : $"+ item.getPrice());
    }
}
