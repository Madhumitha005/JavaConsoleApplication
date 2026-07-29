package com.ecommerce.repository.jdbc;

import com.ecommerce.model.CartItem;
import com.ecommerce.repository.CartItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository("jdbcCartItemRepository")
public class JdbcCartItemRepository implements CartItemRepository {

    // Logger
    private final Logger LOGGER = LoggerFactory.getLogger(JdbcCartItemRepository.class);

    // JDBC Template
    private final JdbcTemplate jdbcTemplate;

    // Constructor Injection
    public JdbcCartItemRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Row Mapper - Convert Db table to Java Object
    private final RowMapper<CartItem> ROW_MAPPER = (resultSet, rowNum) -> {

        CartItem cartItem = new CartItem();

        cartItem.setCartItemId(resultSet.getInt("cart_item_id"));
        cartItem.setCartId(resultSet.getInt("cart_id"));
        cartItem.setProductId(resultSet.getInt("product_id"));
        cartItem.setQuantity(resultSet.getInt("quantity"));

        return cartItem;
    };

    // Save Cart Item
    @Override
    public boolean save(final CartItem cartItem) {

        final String sql = """
                INSERT INTO cart_item
                (cart_id, product_id, quantity)
                VALUES (?, ?, ?)
                """;

        int rows = jdbcTemplate.update(
                sql,
                cartItem.getCartId(),
                cartItem.getProductId(),
                cartItem.getQuantity()
        );

        LOGGER.info("Cart Item Saved Successfully");

        return rows > 0;
    }

    // Update Cart Item
    @Override
    public boolean update(final CartItem cartItem) {

        final String sql = """
                UPDATE cart_item
                SET quantity = ?
                WHERE cart_item_id = ?
                """;

        int rows = jdbcTemplate.update(
                sql,
                cartItem.getQuantity(),
                cartItem.getCartItemId()
        );

        LOGGER.info("Cart Item Updated Successfully");

        return rows > 0;
    }

    // Delete CartItem Id
    @Override
    public boolean delete(final int cartItemId) {

        final String sql = """
                DELETE FROM cart_item
                WHERE cart_item_id = ?
                """;

        int rows = jdbcTemplate.update(sql, cartItemId);

        LOGGER.info("Cart Item Deleted Successfully");

        return rows > 0;
    }

    // Delete By CartId
    @Override
    public boolean deleteByCartId(final int cartId) {

        final String sql = """
                DELETE FROM cart_item
                WHERE cart_id = ?
                """;

        int rows = jdbcTemplate.update(sql, cartId);

        LOGGER.info("Cart Items Deleted Successfully");

        return rows > 0;
    }

    // Find By Id
    @Override
    public CartItem findById(final int cartItemId) {

        final String sql = """
                SELECT *
                FROM cart_item
                WHERE cart_item_id = ?
                """;

        List<CartItem> cartItems = jdbcTemplate.query(sql, ROW_MAPPER, cartItemId);

        return cartItems.isEmpty() ? null : cartItems.getFirst();
    }

    // Find By Cart Id
    @Override
    public Collection<CartItem> findByCartId(final int cartId) {

        final String sql = """
                SELECT *
                FROM cart_item
                WHERE cart_id = ?
                """;

        return jdbcTemplate.query(
                sql,
                ROW_MAPPER,
                cartId
        );
    }

    // Find All Items In Cart
    @Override
    public Collection<CartItem> findAll() {

        final String sql = """
                SELECT *
                FROM cart_item
                """;

        return jdbcTemplate.query(
                sql,
                ROW_MAPPER
        );
    }
}