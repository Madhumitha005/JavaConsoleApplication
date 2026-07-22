package com.ecommerce.repository.jdbc;

import com.ecommerce.model.OrderItem;
import com.ecommerce.repository.OrderItemRepository;
import com.ecommerce.util.DbConnection;
import com.ecommerce.util.LoggerUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class JdbcOrderItemRepository implements OrderItemRepository {

    @Override
    public boolean save(OrderItem orderItem) {

        String sql = """
                INSERT INTO order_item
                (
                    order_id,
                    product_id,
                    quantity,
                    price
                )
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, orderItem.getOrderId());
            statement.setInt(2, orderItem.getProductId());
            statement.setInt(3, orderItem.getQuantity());
            statement.setDouble(4, orderItem.getPrice());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }

        return false;
    }

    @Override
    public OrderItem findById(int orderItemId) {

        String sql = """
                SELECT *
                FROM order_item
                WHERE order_item_id = ?
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, orderItemId);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {

                return mapOrderItem(rs);
            }

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }

        return null;
    }

    @Override
    public Collection<OrderItem> findByOrderId(int orderId) {

        Collection<OrderItem> orderItems = new ArrayList<>();

        String sql = """
                SELECT *
                FROM order_item
                WHERE order_id = ?
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                orderItems.add(mapOrderItem(rs));
            }

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }

        return orderItems;
    }

    @Override
    public Collection<OrderItem> findAll() {

        Collection<OrderItem> orderItems = new ArrayList<>();

        String sql = """
                SELECT *
                FROM order_item
                ORDER BY order_item_id
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {

                orderItems.add(mapOrderItem(rs));
            }

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }

        return orderItems;
    }

    @Override
    public boolean update(OrderItem orderItem) {

        String sql = """
                UPDATE order_item
                SET
                    quantity = ?,
                    price = ?
                WHERE order_item_id = ?
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, orderItem.getQuantity());
            statement.setDouble(2, orderItem.getPrice());
            statement.setInt(3, orderItem.getOrderItemId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }

        return false;
    }

    @Override
    public boolean delete(int orderItemId) {

        String sql = """
                DELETE FROM order_item
                WHERE order_item_id = ?
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, orderItemId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }

        return false;
    }

    @Override
    public boolean deleteByOrderId(int orderId) {

        String sql = """
                DELETE FROM order_item
                WHERE order_id = ?
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }

        return false;
    }

    private OrderItem mapOrderItem(ResultSet rs)
            throws SQLException {

        OrderItem orderItem = new OrderItem();

        orderItem.setOrderItemId(
                rs.getInt("order_item_id")
        );

        orderItem.setOrderId(
                rs.getInt("order_id")
        );

        orderItem.setProductId(
                rs.getInt("product_id")
        );

        orderItem.setQuantity(
                rs.getInt("quantity")
        );

        orderItem.setPrice(
                rs.getDouble("price")
        );

        return orderItem;
    }
}