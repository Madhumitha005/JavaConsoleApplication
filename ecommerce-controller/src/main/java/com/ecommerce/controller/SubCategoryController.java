package com.ecommerce.controller;

import com.ecommerce.model.SubCategory;
import com.ecommerce.service.SubCategoryService;

import org.springframework.stereotype.Controller;

import java.util.Collection;
import java.util.Objects;

@Controller
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    // Constructor Injection
    public SubCategoryController(
            final SubCategoryService subCategoryService) {

        this.subCategoryService = Objects.requireNonNull(subCategoryService, "SubCategoryService cannot be null.");
    }

    // Add sub category
    public boolean addSubCategory(final SubCategory subCategory) {

        return subCategoryService.addSubCategory(subCategory);
    }

    // Get Sub Category By Id
    public SubCategory getSubCategoryById(final int subCategoryId) {

        return subCategoryService.getSubCategoryById(subCategoryId);
    }

    // View ALL Sub categories
    public Collection<SubCategory> viewSubCategories() {

        return subCategoryService.getAllSubCategories();
    }

    // View Sub Categories By Category Id
    public Collection<SubCategory> viewSubCategoriesByCategoryId(final int categoryId) {

        return subCategoryService.getSubCategoriesByCategoryId(categoryId);
    }

    // Update Sub Category
    public boolean updateSubCategory(final SubCategory subCategory) {

        return subCategoryService.updateSubCategory(subCategory);
    }

    // Delete Sub Category
    public boolean deleteSubCategory(final int subCategoryId) {

        return subCategoryService.deleteSubCategory(subCategoryId);
    }
}