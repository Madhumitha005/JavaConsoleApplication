package com.ecommerce.view.customer;

import java.util.Collection;
import java.util.Scanner;

import com.ecommerce.controller.CartController;
import com.ecommerce.controller.CartItemController;
import com.ecommerce.controller.OrderController;
import com.ecommerce.controller.OrderItemController;
import com.ecommerce.exception.ValidationException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.util.InputUtil;
import com.ecommerce.validator.OrderValidator;

public class OrderView {

    private final OrderController orderController;
    private final OrderItemController orderItemController;
    private final CartController cartController;
    private final CartItemController cartItemController;
    private final Scanner scanner;
    private final OrderValidator validator;

    // Constructor Injection
    public OrderView(OrderController orderController,
                     OrderItemController orderItemController,
                     CartController cartController,
                     CartItemController cartItemController) {

        this.orderController = orderController;
        this.orderItemController = orderItemController;
        this.cartController = cartController;
        this.cartItemController = cartItemController;
        this.scanner = InputUtil.getInstance().getScanner();
        this.validator = OrderValidator.getInstance();
    }

    // Place Order
    public void placeOrder(int userId) {

        System.out.println("\n========== PLACE ORDER ==========");

        Order order = new Order();
        order.setUserId(userId);

        try {

            while (true) {

                System.out.print("Enter Name : ");
                String name = scanner.nextLine();

                try {

                    validator.validateCustomerName(name);
                    order.setCustomerName(name);
                    break;

                } catch (ValidationException e) {

                    System.out.println(e.getMessage());
                }
            }

            while (true) {

                System.out.print("Enter Phone : ");
                String phone = scanner.nextLine();

                try {

                    validator.validatePhone(phone);
                    order.setPhone(phone);
                    break;

                } catch (ValidationException e) {

                    System.out.println(e.getMessage());
                }
            }

            while (true) {

                System.out.print("Enter Address : ");
                String address = scanner.nextLine();

                try {

                    validator.validateAddress(address);
                    order.setAddress(address);
                    break;

                } catch (ValidationException e) {

                    System.out.println(e.getMessage());
                }
            }

            while (true) {

                System.out.print("Enter Total Amount : ");

                try {

                    double amount = Double.parseDouble(scanner.nextLine());

                    validator.validateTotalAmount(amount);

                    order.setTotalAmount(amount);
                    break;

                } catch (NumberFormatException e) {

                    System.out.println("Invalid Amount.");

                } catch (ValidationException e) {

                    System.out.println(e.getMessage());
                }
            }

            validator.validate(order);

            boolean result = orderController.placeOrder(order);

            if (result) {

                System.out.println("\nOrder Placed Successfully.");

                Cart cart = cartController.findByUserId(userId);

                if (cart!=null) {

                    Collection<CartItem> cartItems = 
                           cartItemController.getCartItemsByCartId(
                                    cart.getCartId()
                           );

                    for (CartItem item : cartItems) {

                        OrderItem orderItem = new OrderItem();

                        orderItem.setOrderId(order.getOrderId());
                        orderItem.setProductId(item.getProductId());
                        orderItem.setQuantity(item.getQuantity());

                        // temporary price
                        orderItem.setPrice(
                            order.getTotalAmount()
                        );

                        orderItemController.addOrderItem(orderItem);
                    }

                    cartItemController.deleteCartItemsByCartId(cart.getCartId());
                    cartController.clearCart(userId);
                }

            } else {

                System.out.println("Order Failed.");
            }

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }
    }

    // View Orders
    public void viewOrders(int userId) {

        try {

            System.out.println("\n========== YOUR ORDERS ==========");

            Collection<Order> orders = orderController.viewOrders(userId);

            if (orders == null || orders.isEmpty()) {

                System.out.println("No Orders Found.");
                return;
            }

            for (Order order : orders) {

                displayOrder(order);

                Collection<OrderItem> orderItems =
                        orderItemController.getOrderItemsByOrderId(order.getOrderId());

                if (orderItems != null && !orderItems.isEmpty()) {

                    System.out.println("Items:");

                    for (OrderItem item : orderItems) {

                        System.out.println(
                                "Product ID : " + item.getProductId()
                                        + " | Quantity : " + item.getQuantity()
                                        + " | Price : ₹" + item.getPrice());
                    }
                }

                System.out.println("--------------------------------");
            }

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }
    }

    private void displayOrder(Order order) {

        System.out.println("--------------------------------");
        System.out.println("Order ID      : " + order.getOrderId());
        System.out.println("Customer Name : " + order.getCustomerName());
        System.out.println("Phone         : " + order.getPhone());
        System.out.println("Address       : " + order.getAddress());
        System.out.println("Total Amount  : ₹" + order.getTotalAmount());
    }
}