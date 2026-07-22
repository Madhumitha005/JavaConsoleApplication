package com.ecommerce.factory;

import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.OrderItemRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.AuthService;
import com.ecommerce.service.CartItemService;
import com.ecommerce.service.CartService;
import com.ecommerce.service.CategoryService;
import com.ecommerce.service.OrderItemService;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.ProductService;

public final class ServiceFactory {

    private static final RepositoryFactory repositoryFactory =
            RepositoryFactory.getInstance();

    private static AuthService authService;
    private static CategoryService categoryService;
    private static ProductService productService;
    private static CartService cartService;
    private static CartItemService cartItemService;
    private static OrderService orderService;
    private static OrderItemService orderItemService;

    private ServiceFactory() {
    }

    // Auth
    public static AuthService getAuthService() {

        if (authService == null) {

            UserRepository memoryRepository =
                    repositoryFactory.getMemoryUserRepository();

            UserRepository jdbcRepository =
                    repositoryFactory.getJdbcUserRepository();

            authService = new AuthService(
                    memoryRepository,
                    jdbcRepository
            );
        }

        return authService;
    }

    // Category
    public static CategoryService getCategoryService() {

        if (categoryService == null) {

            CategoryRepository memoryRepository =
                    repositoryFactory.getMemoryCategoryRepository();

            CategoryRepository jdbcRepository =
                    repositoryFactory.getJdbcCategoryRepository();

            categoryService = new CategoryService(
                    memoryRepository,
                    jdbcRepository
            );
        }

        return categoryService;
    }

    // Product
    public static ProductService getProductService() {

        if (productService == null) {

            ProductRepository memoryRepository =
                    repositoryFactory.getMemoryProductRepository();

            ProductRepository jdbcRepository =
                    repositoryFactory.getJdbcProductRepository();

            productService = new ProductService(
                    memoryRepository,
                    jdbcRepository
            );
        }

        return productService;
    }

    // Cart
    public static CartService getCartService() {

        if (cartService == null) {

            CartRepository memoryRepository =
                    repositoryFactory.getMemoryCartRepository();

            CartRepository jdbcRepository =
                    repositoryFactory.getJdbcCartRepository();

            cartService = new CartService(
                    memoryRepository,
                    jdbcRepository
            );
        }

        return cartService;
    }

    // Cart Item
    public static CartItemService getCartItemService() {

        if (cartItemService == null) {

            CartItemRepository memoryRepository =
                    repositoryFactory.getMemoryCartItemRepository();

            CartItemRepository jdbcRepository =
                    repositoryFactory.getJdbcCartItemRepository();

            cartItemService = new CartItemService(
                    memoryRepository,
                    jdbcRepository
            );
        }

        return cartItemService;
    }

    // Order
    public static OrderService getOrderService() {

        if (orderService == null) {

            OrderRepository memoryRepository =
                    repositoryFactory.getMemoryOrderRepository();

            OrderRepository jdbcRepository =
                    repositoryFactory.getJdbcOrderRepository();

            orderService = new OrderService(
                    memoryRepository,
                    jdbcRepository
            );
        }

        return orderService;
    }

    //OrderItem
    public static OrderItemService getOrderItemService() {

        if (orderItemService == null) {

            OrderItemRepository memoryRepository =
                    repositoryFactory.getMemoryOrderItemRepository();

            OrderItemRepository jdbcRepository =
                    repositoryFactory.getJdbcOrderItemRepository();

            orderItemService = new OrderItemService(
                    memoryRepository,
                    jdbcRepository
            );
        }

        return orderItemService;
    }
}