package com.ecommerce.view.customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ecommerce.common.util.InputUtil;
import com.ecommerce.common.enums.OrderStatus;
import com.ecommerce.controller.CartController;
import com.ecommerce.controller.CartItemController;
import com.ecommerce.controller.OrderController;
import com.ecommerce.controller.OrderItemController;
import com.ecommerce.controller.ProductController;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.Product;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@Component
public class OrderView {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderView.class);

    private final OrderController orderController;
    private final OrderItemController orderItemController;
    private final CartController cartController;
    private final CartItemController cartItemController;
    private final ProductController productController;
    private final CustomerPaymentView customerPaymentView;
    private final Scanner scanner;
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public OrderView(
            final OrderController orderController,
            final OrderItemController orderItemController,
            final CartController cartController,
            final CartItemController cartItemController,
            final ProductController productController,
            final CustomerPaymentView customerPaymentView) {

        this.orderController = Objects.requireNonNull(orderController, "OrderController cannot be null");
        this.orderItemController = Objects.requireNonNull(orderItemController, "OrderItemController cannot be null");
        this.cartController = Objects.requireNonNull(cartController, "CartController cannot be null");
        this.cartItemController = Objects.requireNonNull(cartItemController, "CartItemController cannot be null");
        this.productController = Objects.requireNonNull(productController, "ProductController cannot be null");
        this.customerPaymentView = Objects.requireNonNull(customerPaymentView, "CustomerPaymentView cannot be null");
        this.scanner = InputUtil.getInstance().getScanner();
    }

    public void placeOrder(final int userId) {

        LOGGER.info("Customer started placing order for userId: {}", userId);

        System.out.println("\n========== PLACE ORDER ==========");

        try {

            Cart cart = cartController.findByUserId(userId);

            if (cart == null) {

                LOGGER.warn("Cart not found for userId: {}", userId);

                System.out.println("Cart Not Found.");

                return;
            }

            Collection<CartItem> cartItems = cartItemController.findByCartId(cart.getCartId());

            if (cartItems == null || cartItems.isEmpty()) {

                LOGGER.warn("Cart is empty for userId: {}", userId);

                System.out.println("Your Cart is Empty.");

                return;
            }

            Order order = new Order();
            order.setUserId(userId);

            System.out.print("Enter Name : ");

            String name = scanner.nextLine();
            order.setCustomerName(name);
            System.out.print("Enter Phone : ");

            String phone = scanner.nextLine();
            order.setPhone(phone);

            System.out.print("Enter Address : ");

            String address = scanner.nextLine();

            order.setAddress(address);

            // Caluculate Total
            double totalAmount = 0.0;

            List<OrderItem> orderItems = new ArrayList<>();

            for (CartItem cartItem : cartItems) {

                if (cartItem == null) {
                    continue;
                }

                Product product = productController.findById(cartItem.getProductId());

                if (product == null) {

                    LOGGER.warn("Product not found for productId: {}", cartItem.getProductId());

                    System.out.println("Product not found for Product ID: " + cartItem.getProductId());

                    return;
                }

                boolean stockAvailable = productController.isStockAvailable(cartItem.getProductId(), cartItem.getQuantity());

                if (!stockAvailable) {

                    LOGGER.warn("Insufficient stock for productId: {}", cartItem.getProductId());

                    System.out.println("Insufficient stock for Product: " + product.getProductName());

                    return;
                }

                //Item total
                double discountedPrice = product.getPrice()
                                   * (1 - product.getDiscountPercentage() / 100.0);
                double itemTotal = discountedPrice * cartItem.getQuantity();

                totalAmount += itemTotal;

                OrderItem orderItem = new OrderItem();

                orderItem.setProductId(cartItem.getProductId());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setPrice(discountedPrice);
                orderItems.add(orderItem);
            }

            order.setTotalAmount(totalAmount);

            Set<ConstraintViolation<Order>> violations = validator.validate(order);

            if (!violations.isEmpty()) {

                System.out.println("\nPlease correct the following errors:");

                for (ConstraintViolation<Order> violation : violations) {

                    System.out.println("- " + violation.getMessage());
                }

                return;
            }

            boolean orderPlaced = orderController.placeOrder(order, orderItems);

            if (!orderPlaced) {

                LOGGER.warn("Order placement failed for userId: {}", userId);

                System.out.println("\nOrder Failed.");

                return;
            }

            LOGGER.info("Order placed successfully. Order ID: {}", order.getOrderId());

            System.out.println("===========================");
            System.out.println("\nOrder Created Successfully.");
            System.out.println("Order ID : " + order.getOrderId());
            System.out.println("Total Amount : $" + order.getTotalAmount());

            System.out.println("\n========== PAYMENT REQUIRED ==========");
            System.out.println("Order ID : " + order.getOrderId());
            System.out.println("Amount   : $" + order.getTotalAmount());

            boolean paymentSuccess = customerPaymentView.processPayment(order.getOrderId(), order.getTotalAmount());

            if (!paymentSuccess) {

                LOGGER.warn("Payment failed for orderId: {}", order.getOrderId());

                System.out.println("==============================");
                System.out.println("\nPayment Failed.");
                System.out.println("Order ID : " + order.getOrderId());
                System.out.println("Please contact administrator.");

                return;
            }

            LOGGER.info("Payment successful for orderId: {}", order.getOrderId());

            System.out.println("\nPayment Successful.");

            for (CartItem cartItem : cartItems) {

                if (cartItem == null) {
                    continue;
                }

                boolean stockReduced = productController.reduceStock(cartItem.getProductId(), cartItem.getQuantity());

                if (!stockReduced) {

                    LOGGER.warn("Unable to reduce stock for productId: {}", cartItem.getProductId());

                    System.out.println("Unable to update stock for Product ID: " + cartItem.getProductId());
                }
            }


            boolean cartItemsDeleted = cartItemController.deleteByCartId(cart.getCartId());

            if (!cartItemsDeleted) {

                LOGGER.warn("Unable to clear cart items for cartId: {}", cart.getCartId());

                System.out.println("Unable to clear cart.");

            } else {

                LOGGER.info("Cart cleared successfully for cartId: {}", cart.getCartId());
            }

            System.out.println("-------------------------------------");
            System.out.println("ORDER PLACED SUCCESSFULLY");
            System.out.println("Order ID : " + order.getOrderId());
            System.out.println("Total Amount : $" + order.getTotalAmount());
            System.out.println("Payment : SUCCESS");
            System.out.println("========================================");

            LOGGER.info("Complete order process finished for userId: {}", userId);

        } catch (Exception exception) {

            LOGGER.error("Error while placing order.", exception);

            System.out.println("\nError : " + exception.getMessage());
        }
    }

    public void returnProduct(final int userId) {

        LOGGER.info("Customer requested product return. UserId: {}", userId);

        try {

            System.out.println("\n========== RETURN PRODUCT ==========");

            System.out.print("Enter Order ID : ");
            int orderId = Integer.parseInt(scanner.nextLine());

            Order order = orderController.findOrderById(orderId);

            if (order == null || order.getUserId() != userId) {

                System.out.println("Order Not Found.");
                return;
            }

            if (order.getOrderStatus() != OrderStatus.DELIVERED) {

                System.out.println("Return is allowed only for delivered orders.");
                return;
            }

            System.out.println("\nSelect Return Reason");
            System.out.println("1. Damaged Product");
            System.out.println("2. Wrong Product");
            System.out.println("3. Quality Issue");
            System.out.println("4. Other");
            System.out.print("Enter Choice : ");

            int choice = Integer.parseInt(scanner.nextLine());

            String reason;

            switch (choice) {

                case 1 -> reason = "Damaged Product";
                case 2 -> reason = "Wrong Product";
                case 3 -> reason = "Quality Issue";
                case 4 -> reason = "Other";
                default -> {
                    System.out.println("Invalid Choice.");
                    return;
                }
            }

            order.setOrderStatus(OrderStatus.RETURN_REQUESTED);

            boolean updated = orderController.updateOrder(order);

            if (updated) {

                System.out.println("-------------------------------------");
                System.out.println("\nReturn Request Submitted Successfully.");
                System.out.println("Reason : " + reason);
                System.out.println("Status : " + order.getOrderStatus());

            } else {

                System.out.println("Unable to submit return request.");
            }

        } catch (Exception exception) {

            LOGGER.error("Error while requesting return.", exception);
            System.out.println("Error : " + exception.getMessage());
        }
    }

    public void viewOrders(final int userId) {

        LOGGER.info("Loading customer orders for userId: {}", userId);

        try {

            Collection<Order> orders = orderController.viewOrders(userId);

            System.out.println("\n========== YOUR ORDERS ==========");

            if (orders == null || orders.isEmpty()) {

                System.out.println("No Orders Found.");

                return;
            }

            for (Order order : orders) {

                if (order == null) {
                    continue;
                }

                displayOrder(order);

                Collection<OrderItem> orderItems = orderItemController.getOrderItemsByOrderId(order.getOrderId());

                if (orderItems != null && !orderItems.isEmpty()) {

                    System.out.println("Items :");

                    for (OrderItem item : orderItems) {

                        if (item == null) {
                            continue;
                        }

                        System.out.println("====================================");
                        System.out.println("Product ID : " + item.getProductId());
                        System.out.println("Quantity   : " + item.getQuantity());
                        System.out.println("Price      : $" + item.getPrice());

                        System.out.println();
                    }

                } else {

                    System.out.println("No Items Found.");
                }
            }

        } catch (Exception exception) {

            LOGGER.error("Unable to load orders for userId: {}", userId, exception);

            System.out.println("Error : " + exception.getMessage());
        }
    }



    private void displayOrder(final Order order) {

        if (order == null) {
            return;
        }

        System.out.println("-------------------------------------");
        System.out.println("Order ID      : " + order.getOrderId());
        System.out.println("Customer Name : " + order.getCustomerName());
        System.out.println("Phone         : " + order.getPhone());
        System.out.println("Address       : " + order.getAddress());
        System.out.println("Total Amount  : $" + order.getTotalAmount());
        System.out.println("Order Status  : " + order.getOrderStatus());;
    }
}