package com.ecommerce.repository.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import com.ecommerce.model.Cart;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.util.DbConnection;
import com.ecommerce.util.LoggerUtil;

public class JdbcCartRepository implements CartRepository {

    @Override
    public boolean addToCart(Cart cart) {

        String sql = """
                INSERT INTO cart(user_id)
                VALUES(?)
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS
                     )) {

            statement.setInt(1, cart.getUserId());

            int rows = statement.executeUpdate();

            if (rows > 0) {

                ResultSet rs = statement.getGeneratedKeys();

                if (rs.next()) {
                    cart.setCartId(rs.getInt(1));
                }

                return true;
            }

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }

        return false;
    }

    // Find Cart By User
    @Override
    public Cart findByUserId(int userId) {

        String sql = """
                SELECT *
                FROM cart
                WHERE user_id = ?
                LIMIT 1
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {

                Cart cart = new Cart();

                cart.setCartId(rs.getInt("cart_id"));
                cart.setUserId(rs.getInt("user_id"));

                return cart;
            }

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }

        return null;
    }

    @Override
    public Collection<Cart> getCartByUser(int userId) {

        Collection<Cart> carts = new ArrayList<>();

        String sql = """
                SELECT *
                FROM cart
                WHERE user_id = ?
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                Cart cart = new Cart();

                cart.setCartId(rs.getInt("cart_id"));
                cart.setUserId(rs.getInt("user_id"));

                carts.add(cart);
            }

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }

        return carts;
    }

    @Override
    public boolean updateCart(Cart cart) {

        String sql = """
                UPDATE cart
                SET user_id = ?
                WHERE cart_id = ?
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, cart.getUserId());
            statement.setInt(2, cart.getCartId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }

        return false;
    }

    @Override
    public boolean removeFromCart(int cartId) {

        String sql = """
                DELETE FROM cart
                WHERE cart_id = ?
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, cartId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }

        return false;
    }

    @Override
    public boolean clearCart(int userId) {

        String sql = """
                DELETE FROM cart
                WHERE user_id = ?
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }

        return false;
    }
}