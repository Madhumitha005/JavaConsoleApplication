package com.ecommerce.repository.memory;

import com.ecommerce.model.SubCategory;
import com.ecommerce.repository.SubCategoryRepository;
import com.ecommerce.common.util.IdGenerator;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

@Repository("inMemorySubCategoryRepository")
public class InMemorySubCategoryRepository implements SubCategoryRepository {

    private final Collection<SubCategory> subCategories;

    public InMemorySubCategoryRepository() {
        this.subCategories = new ArrayList<>();
    }

    @Override
    public boolean save(final SubCategory subCategory) {

        if (subCategory == null || existsByName(subCategory.getSubCategoryName())) {
            return false;
        }

        subCategory.setSubCategoryId(IdGenerator.getInstance().nextSubCategoryId());

        return subCategories.add(subCategory);
    }

    @Override
    public boolean update(final SubCategory updatedSubCategory) {

        if (updatedSubCategory == null) {
            return false;
        }

        for (SubCategory subCategory : subCategories) {

            if (subCategory.getSubCategoryId() == updatedSubCategory.getSubCategoryId()) {

                subCategory.setSubCategoryName(updatedSubCategory.getSubCategoryName());
                subCategory.setCategory(updatedSubCategory.getCategory());

                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(final int subCategoryId) {

        return subCategories.removeIf(subCategory -> subCategory.getSubCategoryId() == subCategoryId);
    }

    @Override
    public SubCategory findById(final int subCategoryId) {

        for (SubCategory subCategory : subCategories) {

            if (subCategory.getSubCategoryId() == subCategoryId) {
                return subCategory;
            }
        }

        return null;
    }

    @Override
    public SubCategory findByName(final String subCategoryName) {

        if (subCategoryName == null) {
            return null;
        }

        for (SubCategory subCategory : subCategories) {

            if (subCategory.getSubCategoryName().equalsIgnoreCase(subCategoryName)) {

                return subCategory;
            }
        }

        return null;
    }

    @Override
    public Collection<SubCategory> findByCategoryId(final int categoryId) {

        Collection<SubCategory> result = new ArrayList<>();

        for (SubCategory subCategory : subCategories) {

            if (subCategory.getCategory() != null && subCategory.getCategory().getCategoryId() == categoryId) {

                result.add(subCategory);
            }
        }

        return result;
    }

    @Override
    public Collection<SubCategory> findAll() {

        return new ArrayList<>(subCategories);
    }

    @Override
    public boolean existsByName(final String subCategoryName) {

        return findByName(subCategoryName) != null;
    }
}