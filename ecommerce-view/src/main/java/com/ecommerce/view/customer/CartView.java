package com.ecommerce.view.customer;

import java.util.Collection;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ecommerce.controller.CartController;
import com.ecommerce.controller.CartItemController;
import com.ecommerce.controller.ProductController;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Product;

@Component
public class CartView {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartView.class);

    private final CartController cartController;
    private final CartItemController cartItemController;
    private final ProductController productController;

    public CartView(
            final CartController cartController,
            final CartItemController cartItemController,
            final ProductController productController) {

        this.cartController = Objects.requireNonNull(cartController, "CartController cannot be null");
        this.cartItemController = Objects.requireNonNull(cartItemController, "CartItemController cannot be null");
        this.productController = Objects.requireNonNull(productController, "ProductController cannot be null");
    }

    // Add Product To Cart
    public void addToCart(final Cart cart, final CartItem cartItem) {

        if (cart == null || cartItem == null) {

            System.out.println("Invalid Cart.");
            return;
        }

        try {

            Cart existingCart = cartController.findByUserId(cart.getUserId());

            if (existingCart == null) {

                boolean created = cartController.save(cart);

                if (!created) {

                    System.out.println("Unable To Create Cart.");
                    return;
                }

                existingCart = cartController.findByUserId(cart.getUserId());

                if (existingCart == null) {

                    System.out.println("Cart Not Found.");
                    return;
                }
            }

            cartItem.setCartId(existingCart.getCartId());

            boolean saved = cartItemController.save(cartItem);

            if (saved) {

                LOGGER.info("Product Added To Cart.");

                System.out.println("Product Added To Cart Successfully.");

            } else {

                System.out.println("Unable To Add Product.");
            }

        } catch (Exception exception) {

            LOGGER.error("Add Cart Error", exception);

            System.out.println("Error : " + exception.getMessage());
        }
    }

    // View Cart
    public void showCart(final int userId) {

        try {

            LOGGER.info("Loading cart for userId : {}", userId);

            Collection<Cart> carts = cartController.findByUserIdList(userId);

            System.out.println("\n========== MY CART ==========");

            if (carts == null || carts.isEmpty()) {

                System.out.println("Cart Is Empty.");
                return;
            }

            for (Cart cart : carts) {

                displayCart(cart);
            }

        } catch (Exception exception) {

            LOGGER.error("Cart Error", exception);

            System.out.println("Error : " + exception.getMessage());
        }
    }

    // Display Cart
    private void displayCart(final Cart cart) {

        System.out.println("\nCart ID : " + cart.getCartId());
        System.out.println("User ID : " + cart.getUserId());

        Collection<CartItem> items = cartItemController.findByCartId(cart.getCartId());

        if (items == null || items.isEmpty()) {

            System.out.println("No Products In Cart.");
            return;
        }

        double cartTotal = 0;

        System.out.println("\nItems :");

        for (CartItem item : items) {

            Product product = productController.findById(item.getProductId());

            if (product == null) {

                continue;
            }

            double discountPrice = product.getPrice()
                            * (1 - product.getDiscountPercentage() / 100.0);

            double itemTotal = discountPrice * item.getQuantity();
            cartTotal += itemTotal;

            System.out.println("--------------------------------");
            System.out.println("Product ID      : " + product.getProductId());
            System.out.println("Product Name    : " + product.getProductName());
            System.out.println("Quantity        : " + item.getQuantity());
            System.out.println("Original Price  : $" + product.getPrice());
            System.out.println("Discount        : " + product.getDiscountPercentage() + "%");
            System.out.println("Discount Price  : $" + discountPrice);
            System.out.println("Item Total      : $" + itemTotal);
        }
        System.out.println("--------------------------------");
        System.out.println("Cart Total : $" + cartTotal);
    }
}