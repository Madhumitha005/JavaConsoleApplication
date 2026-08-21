/*
 * OrmSubCategoryRepository.java
 *
 * Version 1.0
 *
 * August 21, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */

package com.ecommerce.subcategory.repository.orm;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.subcategory.entity.SubCategory;
import com.ecommerce.subcategory.repository.SubCategoryRepository;

@Repository
@Transactional
public class OrmSubCategoryRepository
        implements SubCategoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean save(
            final SubCategory subCategory) {

        entityManager.persist(subCategory);
        return true;
    }

    @Override
    public boolean update(
            final SubCategory subCategory) {

        entityManager.merge(subCategory);
        return true;
    }

    @Override
    public boolean delete(
            final Integer subCategoryId) {

        SubCategory entity =
                entityManager.find(
                        SubCategory.class,
                        subCategoryId
                );

        if (entity == null) {
            return false;
        }

        entityManager.remove(entity);
        return true;
    }

    @Override
    public SubCategory findById(
            final Integer subCategoryId) {

        return entityManager.find(
                SubCategory.class,
                subCategoryId
        );
    }

    @Override
    public SubCategory findByName(
            final String subCategoryName) {

        TypedQuery<SubCategory> query =
                entityManager.createQuery(
                        "SELECT s "
                                + "FROM SubCategory s "
                                + "WHERE s.subCategoryName "
                                + "= :subCategoryName",
                        SubCategory.class
                );

        query.setParameter(
                "subCategoryName",
                subCategoryName
        );

        List<SubCategory> results =
                query.getResultList();

        return results.isEmpty()
                ? null
                : results.getFirst();
    }

    @Override
    public Collection<SubCategory> findByCategoryId(
            final Integer categoryId) {

        TypedQuery<SubCategory> query =
                entityManager.createQuery(
                        "SELECT s "
                                + "FROM SubCategory s "
                                + "WHERE s.category.categoryId "
                                + "= :categoryId",
                        SubCategory.class
                );

        query.setParameter(
                "categoryId",
                categoryId
        );

        return query.getResultList();
    }

    @Override
    public Collection<SubCategory> findAll() {

        return entityManager.createQuery(
                "SELECT s FROM SubCategory s",
                SubCategory.class
        ).getResultList();
    }

    @Override
    public boolean existsByName(
            final String subCategoryName) {

        TypedQuery<Long> query =
                entityManager.createQuery(
                        "SELECT COUNT(s) "
                                + "FROM SubCategory s "
                                + "WHERE s.subCategoryName "
                                + "= :subCategoryName",
                        Long.class
                );

        query.setParameter(
                "subCategoryName",
                subCategoryName
        );

        return query.getSingleResult() > 0;
    }
}