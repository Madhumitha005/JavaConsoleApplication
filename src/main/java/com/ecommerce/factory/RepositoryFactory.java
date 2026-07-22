package com.ecommerce.factory;

import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.OrderItemRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.repository.jdbc.JdbcCartItemRepository;
import com.ecommerce.repository.jdbc.JdbcCartRepository;
import com.ecommerce.repository.jdbc.JdbcCategoryRepository;
import com.ecommerce.repository.jdbc.JdbcOrderItemRepository;
import com.ecommerce.repository.jdbc.JdbcOrderRepository;
import com.ecommerce.repository.jdbc.JdbcProductRepository;
import com.ecommerce.repository.jdbc.JdbcUserRepository;
import com.ecommerce.repository.memory.InMemoryCartItemRepository;
import com.ecommerce.repository.memory.InMemoryCartRepository;
import com.ecommerce.repository.memory.InMemoryCategoryRepository;
import com.ecommerce.repository.memory.InMemoryOrderItemRepository;
import com.ecommerce.repository.memory.InMemoryOrderRepository;
import com.ecommerce.repository.memory.InMemoryProductRepository;
import com.ecommerce.repository.memory.InMemoryUserRepository;

public final class RepositoryFactory {

    private static RepositoryFactory instance;

    // MEMORY
    private UserRepository memoryUserRepository;
    private CategoryRepository memoryCategoryRepository;
    private ProductRepository memoryProductRepository;
    private CartRepository memoryCartRepository;
    private CartItemRepository memoryCartItemRepository;
    private OrderRepository memoryOrderRepository;
    private OrderItemRepository memoryOrderItemRepository;

    // JDBC
    private UserRepository jdbcUserRepository;
    private CategoryRepository jdbcCategoryRepository;
    private ProductRepository jdbcProductRepository;
    private CartRepository jdbcCartRepository;
    private CartItemRepository jdbcCartItemRepository;
    private OrderRepository jdbcOrderRepository;
    private OrderItemRepository jdbcOrderItemRepository;

    private RepositoryFactory() {
    }

    public static RepositoryFactory getInstance() {

        if (instance == null) {
            instance = new RepositoryFactory();
        }

        return instance;
    }

    // User

    public UserRepository getMemoryUserRepository() {

        if (memoryUserRepository == null) {
            memoryUserRepository = new InMemoryUserRepository();
        }

        return memoryUserRepository;
    }

    public UserRepository getJdbcUserRepository() {

        if (jdbcUserRepository == null) {
            jdbcUserRepository = new JdbcUserRepository();
        }

        return jdbcUserRepository;
    }

    //Category

    public CategoryRepository getMemoryCategoryRepository() {

        if (memoryCategoryRepository == null) {
            memoryCategoryRepository = new InMemoryCategoryRepository();
        }

        return memoryCategoryRepository;
    }

    public CategoryRepository getJdbcCategoryRepository() {

        if (jdbcCategoryRepository == null) {
            jdbcCategoryRepository = new JdbcCategoryRepository();
        }

        return jdbcCategoryRepository;
    }

    // Product

    public ProductRepository getMemoryProductRepository() {

        if (memoryProductRepository == null) {
            memoryProductRepository = new InMemoryProductRepository();
        }

        return memoryProductRepository;
    }

    public ProductRepository getJdbcProductRepository() {

        if (jdbcProductRepository == null) {
            jdbcProductRepository = new JdbcProductRepository();
        }

        return jdbcProductRepository;
    }

    // Cart

    public CartRepository getMemoryCartRepository() {

        if (memoryCartRepository == null) {
            memoryCartRepository = new InMemoryCartRepository();
        }

        return memoryCartRepository;
    }

    public CartRepository getJdbcCartRepository() {

        if (jdbcCartRepository == null) {
            jdbcCartRepository = new JdbcCartRepository();
        }

        return jdbcCartRepository;
    }

    // CartItem

    public CartItemRepository getMemoryCartItemRepository() {

        if (memoryCartItemRepository == null) {
            memoryCartItemRepository = new InMemoryCartItemRepository();
        }

        return memoryCartItemRepository;
    }

    public CartItemRepository getJdbcCartItemRepository() {

        if (jdbcCartItemRepository == null) {
            jdbcCartItemRepository = new JdbcCartItemRepository();
        }

        return jdbcCartItemRepository;
    }

    // Order

    public OrderRepository getMemoryOrderRepository() {

        if (memoryOrderRepository == null) {
            memoryOrderRepository = new InMemoryOrderRepository();
        }

        return memoryOrderRepository;
    }

    public OrderRepository getJdbcOrderRepository() {

        if (jdbcOrderRepository == null) {
            jdbcOrderRepository = new JdbcOrderRepository();
        }

        return jdbcOrderRepository;
    }

    // OrderItem

    public OrderItemRepository getMemoryOrderItemRepository() {

        if (memoryOrderItemRepository == null) {
            memoryOrderItemRepository = new InMemoryOrderItemRepository();
        }

        return memoryOrderItemRepository;
    }

    public OrderItemRepository getJdbcOrderItemRepository() {

        if (jdbcOrderItemRepository == null) {
            jdbcOrderItemRepository = new JdbcOrderItemRepository();
        }

        return jdbcOrderItemRepository;
    }
}