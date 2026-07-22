package com.ecommerce.repository.jdbc;

import com.ecommerce.model.CartItem;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.util.DbConnection;
import com.ecommerce.util.LoggerUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class JdbcCartItemRepository implements CartItemRepository {

    @Override
    public boolean addCartItem(CartItem cartItem) {

        String sql = """
                INSERT INTO cart_item(cart_id, product_id, quantity)
                VALUES(?, ?, ?)
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, cartItem.getCartId());
            statement.setInt(2, cartItem.getProductId());
            statement.setInt(3, cartItem.getQuantity());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }

        return false;
    }

    @Override
    public boolean updateCartItem(CartItem cartItem) {

        String sql = """
                UPDATE cart_item
                SET quantity = ?
                WHERE cart_item_id = ?
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, cartItem.getQuantity());
            statement.setInt(2, cartItem.getCartItemId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }

        return false;
    }

    @Override
    public boolean deleteCartItem(int cartItemId) {

        String sql = """
                DELETE FROM cart_item
                WHERE cart_item_id = ?
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, cartItemId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }

        return false;
    }

    @Override
    public CartItem findById(int cartItemId) {

        String sql = """
                SELECT *
                FROM cart_item
                WHERE cart_item_id = ?
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, cartItemId);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {

                CartItem item = new CartItem();

                item.setCartItemId(rs.getInt("cart_item_id"));
                item.setCartId(rs.getInt("cart_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setQuantity(rs.getInt("quantity"));

                return item;
            }

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }

        return null;
    }

    @Override
    public Collection<CartItem> findByCartId(int cartId) {

        Collection<CartItem> items = new ArrayList<>();

        String sql = """
                SELECT *
                FROM cart_item
                WHERE cart_id = ?
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, cartId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                CartItem item = new CartItem();

                item.setCartItemId(rs.getInt("cart_item_id"));
                item.setCartId(rs.getInt("cart_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setQuantity(rs.getInt("quantity"));

                items.add(item);
            }

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }

        return items;
    }

    @Override
    public Collection<CartItem> findAll() {

        Collection<CartItem> items = new ArrayList<>();

        String sql = "SELECT * FROM cart_item";

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {

                CartItem item = new CartItem();

                item.setCartItemId(rs.getInt("cart_item_id"));
                item.setCartId(rs.getInt("cart_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setQuantity(rs.getInt("quantity"));

                items.add(item);
            }

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }

        return items;
    }


    @Override
    public boolean deleteByCartId(int cartId){

        String sql = """
            DELETE FROM cart_item
            WHERE cart_id = ?
            """;

        try(Connection connection = DbConnection.getConnection();
            PreparedStatement statement =
                connection.prepareStatement(sql)){

            statement.setInt(1, cartId);

            return statement.executeUpdate() >= 0;

        }catch(SQLException e){

            LoggerUtil.getInstance()
                .error(e.getMessage());
        }

        return false;
    }
}