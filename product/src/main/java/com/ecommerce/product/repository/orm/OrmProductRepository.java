package com.ecommerce.product.repository.orm;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.product.entity.Product;
import com.ecommerce.product.repository.ProductRepository;

@Repository
@Transactional
public class OrmProductRepository implements ProductRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean save(final Product product) {

        entityManager.persist(product);

        return true;
    }

    @Override
    public boolean update(final Product product) {

        entityManager.merge(product);

        return true;
    }

    @Override
    public boolean delete(final Integer productId) {

        Product entity =
                entityManager.find(
                        Product.class,
                        productId);

        if (entity != null) {

            entityManager.remove(entity);

            return true;
        }

        return false;
    }

    @Override
    public Product findById(
            final Integer productId) {

        return entityManager.find(
                Product.class,
                productId);
    }

    @Override
    public Product findByName(
            final String productName) {

        TypedQuery<Product> query =
                entityManager.createQuery(
                        "SELECT e "
                                + "FROM Product e "
                                + "WHERE e.name = :productName",
                        Product.class);

        query.setParameter(
                "productName",
                productName);

        List<Product> results =
                query.getResultList();

        return results.isEmpty()
                ? null
                : results.get(0);
    }

    @Override
    public Collection<Product> findAll() {

        return entityManager.createQuery(
                        "SELECT e FROM Product e",
                        Product.class)
                .getResultList();
    }

    @Override
    public boolean existsByName(
            final String productName,
            final Integer sellerId) {

        TypedQuery<Long> query =
                entityManager.createQuery(
                        "SELECT COUNT(e) "
                                + "FROM Product e "
                                + "WHERE e.name = :productName "
                                + "AND e.seller.id = :sellerId",
                        Long.class);

        query.setParameter(
                "productName",
                productName);

        query.setParameter(
                "sellerId",
                sellerId);

        return query.getSingleResult() > 0;
    }

    @Override
    public Collection<Product> findBySellerId(
            final Integer sellerId) {

        TypedQuery<Product> query =
                entityManager.createQuery(
                        "SELECT e "
                                + "FROM Product e "
                                + "WHERE e.seller.id = :sellerId",
                        Product.class);

        query.setParameter(
                "sellerId",
                sellerId);

        return query.getResultList();
    }
}