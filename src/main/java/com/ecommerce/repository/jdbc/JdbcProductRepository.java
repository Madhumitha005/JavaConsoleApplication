package com.ecommerce.repository.jdbc;

import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.util.DbConnection;
import com.ecommerce.util.LoggerUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class JdbcProductRepository implements ProductRepository {

    @Override
    public boolean addProduct(Product product) {

        if (product == null || product.getCategory() == null) {
            return false;
        }

        String sql = """
                INSERT INTO product
                (
                    product_name,
                    price,
                    quantity,
                    category_id
                )
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sql,
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, product.getProductName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getQuantity());
            statement.setInt(4, product.getCategory().getCategoryId());

            int rows = statement.executeUpdate();

            if (rows > 0) {

                ResultSet keys = statement.getGeneratedKeys();

                if (keys.next()) {
                    product.setProductId(keys.getInt(1));
                }

                return true;
            }

        } catch (SQLException e) {

            LoggerUtil.getInstance().error("Add Product Error : " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean updateProduct(Product product) {

        if (product == null || product.getCategory() == null) {
            return false;
        }

        String sql = """
                UPDATE product
                SET
                    product_name = ?,
                    price = ?,
                    quantity = ?,
                    category_id = ?
                WHERE product_id = ?
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, product.getProductName());
            statement.setDouble(2, product.getPrice());
            statement.setInt(3, product.getQuantity());
            statement.setInt(4, product.getCategory().getCategoryId());
            statement.setInt(5, product.getProductId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            LoggerUtil.getInstance().error("Update Product Error : " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean deleteProduct(int productId) {

        String sql = """
                DELETE FROM product
                WHERE product_id = ?
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, productId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            LoggerUtil.getInstance().error("Delete Product Error : " + e.getMessage());
        }

        return false;
    }

    @Override
    public Product findById(int productId) {

        String sql = """
                SELECT
                    p.product_id,
                    p.product_name,
                    p.price,
                    p.quantity,
                    c.category_id,
                    c.name
                FROM product p
                LEFT JOIN category c
                ON p.category_id = c.category_id
                WHERE p.product_id = ?
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, productId);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return mapProduct(rs);
            }

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }

        return null;
    }

    @Override
    public Product findByName(String productName) {

        String sql = """
                SELECT
                    p.product_id,
                    p.product_name,
                    p.price,
                    p.quantity,
                    c.category_id,
                    c.name
                FROM product p
                LEFT JOIN category c
                ON p.category_id = c.category_id
                WHERE LOWER(p.product_name) LIKE LOWER(?)
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + productName + "%");

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return mapProduct(rs);
            }

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }

        return null;
    }

    @Override
    public Collection<Product> getAllProducts() {

        Collection<Product> products = new ArrayList<>();

        String sql = """
                SELECT
                    p.product_id,
                    p.product_name,
                    p.price,
                    p.quantity,
                    c.category_id,
                    c.name
                FROM product p
                LEFT JOIN category c
                ON p.category_id = c.category_id
                ORDER BY p.product_id
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                products.add(mapProduct(rs));
            }

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }
        return products;
    }

    private Product mapProduct(ResultSet rs) throws SQLException {

        Product product = new Product();

        product.setProductId(rs.getInt("product_id"));
        product.setProductName(rs.getString("product_name"));
        product.setPrice(rs.getDouble("price"));
        product.setQuantity(rs.getInt("quantity"));

        Category category = new Category();
        category.setCategoryId(rs.getInt("category_id"));
        category.setCategoryName(rs.getString("name"));

        product.setCategory(category);

        return product;
    }
}