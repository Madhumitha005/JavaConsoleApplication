package com.ecommerce.repository.memory;

import com.ecommerce.common.util.IdGenerator;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

@Repository("inMemoryProductRepository")
public class InMemoryProductRepository implements ProductRepository {

    private final Collection<Product> products;

    public InMemoryProductRepository() {
        this.products = new ArrayList<>();
    }

    @Override
    public boolean save(final Product product) {

        if (product == null) {
            return false;
        }

        product.setProductId(IdGenerator.getInstance().nextProductId());

        return products.add(product);
    }

    @Override
    public boolean update(final Product product) {

        if (product == null) {
            return false;
        }

        for (Product existingProduct : products) {

            if (existingProduct.getProductId() == product.getProductId()) {

                existingProduct.setProductName(product.getProductName());
                existingProduct.setPrice(product.getPrice());
                existingProduct.setDiscountPercentage(product.getDiscountPercentage());
                existingProduct.setQuantity(product.getQuantity());
                existingProduct.setCategory(product.getCategory());
                existingProduct.setSubCategory(product.getSubCategory());
                existingProduct.setProductStatus(product.getProductStatus());

                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(final int productId) {

        return products.removeIf(product -> product.getProductId() == productId
        );
    }

    @Override
    public Product findById(final int productId) {

        for (Product product : products) {

            if (product.getProductId() == productId) {

                return product;
            }
        }

        return null;
    }

    @Override
    public Product findByName(final String productName) {

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

    @Override
    public Collection<Product> findAll() {

        return new ArrayList<>(products);
    }

    @Override
    public boolean existsByName(final String productName) {

        return findByName(productName) != null;
    }
}