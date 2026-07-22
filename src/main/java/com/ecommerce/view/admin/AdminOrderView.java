package com.ecommerce.view.admin;

import java.util.Collection;

import com.ecommerce.controller.OrderController;
import com.ecommerce.controller.OrderItemController;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;

public class AdminOrderView {

    private final OrderController orderController;
    private final OrderItemController orderItemController;

    // Constructor Injection
    public AdminOrderView(OrderController orderController,
                          OrderItemController orderItemController) {

        this.orderController = orderController;
        this.orderItemController = orderItemController;
    }

    // View All Orders
    public void show() {

        try {

            System.out.println("\n========== ALL ORDERS ==========");

            Collection<Order> orders =
                    orderController.viewAllOrders();

            if (orders == null || orders.isEmpty()) {

                System.out.println("No Orders Found.");
                return;
            }

            for (Order order : orders) {

                displayOrder(order);

                Collection<OrderItem> orderItems =
                        orderItemController.getOrderItemsByOrderId(
                                order.getOrderId());

                if (orderItems != null && !orderItems.isEmpty()) {

                    System.out.println("Items:");

                    for (OrderItem item : orderItems) {

                        System.out.println(
                                "  Product ID : " + item.getProductId()
                                + " | Quantity : " + item.getQuantity()
                                + " | Price : ₹" + item.getPrice()
                        );
                    }
                }

                System.out.println("--------------------------------");
            }

            System.out.println("Total Orders : " + orders.size());

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }
    }

    private void displayOrder(Order order) {

        System.out.println("--------------------------------");
        System.out.println("Order ID      : " + order.getOrderId());
        System.out.println("User ID       : " + order.getUserId());
        System.out.println("Customer Name : " + order.getCustomerName());
        System.out.println("Phone         : " + order.getPhone());
        System.out.println("Address       : " + order.getAddress());
        System.out.println("Total Amount  : ₹" + order.getTotalAmount());
    }
}