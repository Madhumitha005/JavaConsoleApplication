/*
 * OrmCategoryRepository.java
 *
 * Version 1.1
 *
 * August 21, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.category.repository.orm;

import java.util.Collection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ecommerce.category.entity.Category;
import com.ecommerce.category.repository.CategoryRepository;

@Repository
public class OrmCategoryRepository implements CategoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public boolean save(final Category category) {

        if (category == null) {
            return false;
        }
        entityManager.persist(category);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Category> findAll() {

        TypedQuery<Category> query =
                entityManager.createQuery(
                        "SELECT c "
                                + "FROM Category c "
                                + "ORDER BY c.categoryId",
                        Category.class
                );
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Category findById(final Integer categoryId) {

        if (categoryId == null) {
            return null;
        }
        return entityManager.find(Category.class, categoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public Category findByName(final String categoryName) {

        if (categoryName == null) {
            return null;
        }
        try {

            TypedQuery<Category> query =
                    entityManager.createQuery(
                            "SELECT c "
                                    + "FROM Category c "
                                    + "WHERE LOWER(c.categoryName) "
                                    + "= LOWER(:categoryName)",
                            Category.class
                    );

            query.setParameter("categoryName", categoryName);
            return query.getSingleResult();

        } catch (NoResultException exception) {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByName(final String categoryName) {

        if (categoryName == null) {
            return false;
        }
        Long count =
                entityManager.createQuery(
                                "SELECT COUNT(c) "
                                        + "FROM Category c "
                                        + "WHERE LOWER(c.categoryName) "
                                        + "= LOWER(:categoryName)",
                                Long.class
                        )
                        .setParameter(
                                "categoryName",
                                categoryName
                        )
                        .getSingleResult();
        return count > 0;
    }

    @Override
    @Transactional
    public boolean update(final Category category) {

        if (category == null || category.getCategoryId() == null) {
            return false;
        }

        /*
         * Hibernate automatically detects the changed fields
         * and executes UPDATE during transaction commit
         */
        Category managedCategory = entityManager.find(Category.class, category.getCategoryId());

        if (managedCategory == null) {
            return false;
        }
        managedCategory.setCategoryName(category.getCategoryName());
        managedCategory.setUpdatedAt(category.getUpdatedAt());
        return true;
    }

    @Override
    @Transactional
    public boolean delete(final Integer categoryId) {

        if (categoryId == null) {
            return false;
        }
        Category category = entityManager.find(Category.class, categoryId);

        if (category == null) {
            return false;
        }
        entityManager.remove(category);
        return true;
    }
}