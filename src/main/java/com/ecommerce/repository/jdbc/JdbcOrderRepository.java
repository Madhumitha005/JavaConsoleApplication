package com.ecommerce.repository.jdbc;

import com.ecommerce.model.Order;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.util.DbConnection;
import com.ecommerce.util.LoggerUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

public class JdbcOrderRepository implements OrderRepository {

    // Place order
    @Override
    public boolean placeOrder(Order order) {

        if (order == null) {
            return false;
        }

        String sql = """
                INSERT INTO "order"
                (
                    user_id,
                    customer_name,
                    phone,
                    address,
                    total_amount
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, order.getUserId());
            statement.setString(2, order.getCustomerName());
            statement.setString(3, order.getPhone());
            statement.setString(4, order.getAddress());
            statement.setDouble(5, order.getTotalAmount());

            int result = statement.executeUpdate();

            if (result > 0) {

                ResultSet keys = statement.getGeneratedKeys();

                if (keys.next()) {
                    order.setOrderId(keys.getInt(1));
                }

                return true;
            }

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }

        return false;
    }

    // Find order by id
    @Override
    public Order findById(int orderId) {

        String sql = """
                SELECT *
                FROM "order"
                WHERE order_id = ?
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                Order order = new Order();

                order.setOrderId(resultSet.getInt("order_id"));
                order.setUserId(resultSet.getInt("user_id"));
                order.setCustomerName(resultSet.getString("customer_name"));
                order.setPhone(resultSet.getString("phone"));
                order.setAddress(resultSet.getString("address"));
                order.setTotalAmount(resultSet.getDouble("total_amount"));

                return order;
            }

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }

        return null;
    }

    // View order by user
    @Override
    public Collection<Order> getOrdersByUser(int userId) {

        Collection<Order> orders = new ArrayList<>();

        String sql = """
                SELECT *
                FROM "order"
                WHERE user_id = ?
                ORDER BY order_id
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Order order = new Order();

                order.setOrderId(resultSet.getInt("order_id"));
                order.setUserId(resultSet.getInt("user_id"));
                order.setCustomerName(resultSet.getString("customer_name"));
                order.setPhone(resultSet.getString("phone"));
                order.setAddress(resultSet.getString("address"));
                order.setTotalAmount(resultSet.getDouble("total_amount"));

                orders.add(order);
            }

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }

        return orders;
    }

    // View all orders
    @Override
    public Collection<Order> getOrders() {

        Collection<Order> orders = new ArrayList<>();

        String sql = """
                SELECT *
                FROM "order"
                ORDER BY order_id
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Order order = new Order();

                order.setOrderId(resultSet.getInt("order_id"));
                order.setUserId(resultSet.getInt("user_id"));
                order.setCustomerName(resultSet.getString("customer_name"));
                order.setPhone(resultSet.getString("phone"));
                order.setAddress(resultSet.getString("address"));
                order.setTotalAmount(resultSet.getDouble("total_amount"));

                orders.add(order);
            }

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }

        return orders;
    }
}