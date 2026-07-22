package com.ecommerce.repository.memory;

import java.util.ArrayList;
import java.util.Collection;

import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;

public class InMemoryProductRepository implements ProductRepository {

    // In-Memory Storage
    private final Collection<Product> products =new ArrayList<>();

    // Add product
    @Override
    public boolean addProduct(Product product) {

        if (product == null) {
            return false;
        }

        for (Product Product : products) {

            if (Product.getProductId()
                    == product.getProductId()) {

                return false;
            }
        }
        return products.add(product);
    }

    // Update product
    @Override
    public boolean updateProduct(Product product) {

        if (product == null) {
            return false;
        }

        for (Product Product : products) {

            if (Product.getProductId() == product.getProductId()) {

                Product.setProductName(product.getProductName());
                Product.setPrice(product.getPrice());
                Product.setQuantity(product.getQuantity());
                Product.setCategory(product.getCategory());

                return true;
            }
        }
        return false;
    }

    // Delete Product
    @Override
    public boolean deleteProduct(int productId) {

        return products.removeIf(product ->
                product.getProductId() == productId
        );
    }

    // Find product by id
    @Override
    public Product findById(int productId) {

        for (Product product : products) {

            if (product.getProductId() == productId) {
                return product;
            }
        }
        return null;
    }

    // Find product by name
    @Override
    public Product findByName(String productName) {

        if (productName == null) {
            return null;
        }

        for (Product product : products) {

            if (product.getProductName().equalsIgnoreCase(productName)) {

                return product;
            }
        }
        return null;
    }

    // Get all products
    @Override
    public Collection<Product> getAllProducts() {

        return new ArrayList<>(products);
    }
}