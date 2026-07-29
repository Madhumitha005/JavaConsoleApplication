package com.ecommerce.service;

import com.ecommerce.model.SubCategory;
import com.ecommerce.repository.SubCategoryRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class SubCategoryService {

    private final SubCategoryRepository memoryRepository;
    private final SubCategoryRepository jdbcRepository;

    public SubCategoryService(

            @Qualifier("inMemorySubCategoryRepository")
            final SubCategoryRepository memoryRepository,

            @Qualifier("jdbcSubCategoryRepository")
            final SubCategoryRepository jdbcRepository) {

        this.memoryRepository = memoryRepository;
        this.jdbcRepository = jdbcRepository;
    }

    public boolean addSubCategory(final SubCategory subCategory) {

        if (subCategory == null) {
            return false;
        }

        // Save in Memory
        boolean memorySaved = memoryRepository.save(subCategory);

        // Save in Database
        boolean jdbcSaved = jdbcRepository.save(subCategory);

        // Both repositories must save successfully
        return memorySaved && jdbcSaved;
    }

    public SubCategory getSubCategoryById(final int subCategoryId) {

        // First search in Memory
        SubCategory subCategory = memoryRepository.findById(subCategoryId);

        // If not found in Memory,
        // search in Database
        if (subCategory == null) {

            subCategory = jdbcRepository.findById(subCategoryId);
        }

        return subCategory;
    }

    public Collection<SubCategory> getAllSubCategories() {

        Map<Integer, SubCategory> subCategories = new LinkedHashMap<>();

        Collection<SubCategory> memorySubCategories = memoryRepository.findAll();

        if (memorySubCategories != null) {

            for (SubCategory subCategory : memorySubCategories) {

                if (subCategory != null) {

                    subCategories.put(subCategory.getSubCategoryId(), subCategory);
                }
            }
        }

        Collection<SubCategory> jdbcSubCategories = jdbcRepository.findAll();

        if (jdbcSubCategories != null) {

            for (SubCategory subCategory : jdbcSubCategories) {

                if (subCategory != null) {

                    subCategories.put(subCategory.getSubCategoryId(), subCategory);
                }
            }
        }
        return subCategories.values();
    }

    public Collection<SubCategory> getSubCategoriesByCategoryId(final int categoryId) {

        Map<Integer, SubCategory> subCategories = new LinkedHashMap<>();

        Collection<SubCategory> memorySubCategories = memoryRepository.findByCategoryId(categoryId);

        if (memorySubCategories != null) {

            for (SubCategory subCategory : memorySubCategories) {

                if (subCategory != null) {

                    subCategories.put(subCategory.getSubCategoryId(), subCategory);
                }
            }
        }

        Collection<SubCategory> jdbcSubCategories = jdbcRepository.findByCategoryId(categoryId);

        if (jdbcSubCategories != null) {

            for (SubCategory subCategory : jdbcSubCategories) {

                if (subCategory != null) {

                    subCategories.put(subCategory.getSubCategoryId(), subCategory);
                }
            }
        }

        return subCategories.values();
    }

    public boolean updateSubCategory(final SubCategory subCategory) {

        if (subCategory == null) {
            return false;
        }

        // Update Memory
        boolean memoryUpdated = memoryRepository.update(subCategory);

        // Update Database
        boolean jdbcUpdated = jdbcRepository.update(subCategory);

        /*
         * If either repository is updated,
         * consider operation successful.
         */
        return memoryUpdated || jdbcUpdated;
    }

    public boolean deleteSubCategory(final int subCategoryId) {

        // Delete from Memory
        boolean memoryDeleted = memoryRepository.delete(subCategoryId);

        // Delete from Database
        boolean jdbcDeleted = jdbcRepository.delete(subCategoryId);

        /*
         * If either repository deletes the record,
         * consider operation successful.
         */
        return memoryDeleted || jdbcDeleted;
    }
}