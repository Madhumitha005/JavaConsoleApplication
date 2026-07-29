package com.ecommerce.repository;

import com.ecommerce.model.SubCategory;
import java.util.Collection;

public interface SubCategoryRepository {

    boolean save(final SubCategory subCategory);

    boolean update(final SubCategory subCategory);

    boolean delete(final int subCategoryId);

    SubCategory findById(final int subCategoryId);

    SubCategory findByName(final String subCategoryName);

    Collection<SubCategory> findByCategoryId(final int categoryId);

    Collection<SubCategory> findAll();

    boolean existsByName(final String subCategoryName);
}