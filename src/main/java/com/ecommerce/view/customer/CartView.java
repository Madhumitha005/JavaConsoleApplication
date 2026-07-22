package com.ecommerce.view.customer;

import java.util.Collection;

import com.ecommerce.controller.CartController;
import com.ecommerce.controller.CartItemController;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;

public class CartView {

    private final CartController cartController;
    private final CartItemController cartItemController;

    // Constructor Injection
    public CartView(CartController cartController,
                    CartItemController cartItemController) {

        this.cartController = cartController;
        this.cartItemController = cartItemController;
    }

    // Add To Cart
    // Add To Cart
public void addToCart(Cart cart, CartItem cartItem) {

    if (cart == null || cartItem == null) {

        System.out.println("Invalid Cart.");
        return;
    }

    try {

        // Check Existing Cart
        Cart existingCart = cartController.findByUserId(cart.getUserId());

        if (existingCart == null) {

            boolean cartResult = cartController.addToCart(cart);

            if (!cartResult) {

                System.out.println("Unable To Create Cart.");
                return;
            }

            existingCart = cartController.findByUserId(cart.getUserId());

            if (existingCart == null) {

                System.out.println("Cart Not Found.");
                return;
            }
        }

        // Use Existing Cart
        cartItem.setCartId(existingCart.getCartId());

        boolean itemResult = cartItemController.addCartItem(cartItem);

        if (itemResult) {

            System.out.println("Product Added To Cart Successfully.");

        } else {

            System.out.println("Unable To Add Product To Cart.");
        }

    } catch (Exception e) {

        System.out.println("Error : " + e.getMessage());
    }
}

    // View Cart
    public void showCart(int userId) {

        try {

            Collection<Cart> carts = cartController.viewCart(userId);

            System.out.println("\n========== MY CART ==========");

            if (carts == null || carts.isEmpty()) {

                System.out.println("Cart Is Empty.");
                return;
            }

            for (Cart cart : carts) {

                System.out.println("--------------------------------");
                System.out.println("Cart ID : " + cart.getCartId());
                System.out.println("User ID : " + cart.getUserId());

                Collection<CartItem> items =
                        cartItemController.getCartItemsByCartId(cart.getCartId());

                if (items == null || items.isEmpty()) {

                    System.out.println("No Products In Cart.");
                    continue;
                }

                for (CartItem item : items) {

                    System.out.println("Product ID : " + item.getProductId());
                    System.out.println("Quantity   : " + item.getQuantity());
                    System.out.println("--------------------------------");
                }
            }

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }
    }

    // Clear Cart
    public void clearCart(int userId) {

        try {

            boolean result = cartController.clearCart(userId);

            if (result) {

                System.out.println("Cart Cleared Successfully.");

            } else {

                System.out.println("Unable To Clear Cart.");
            }

        } catch (Exception e) {

            System.out.println("Error : " + e.getMessage());
        }
    }
}