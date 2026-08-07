package com.ecommerce.product.dto;

import com.ecommerce.common.enums.ProductStatus;

public class ProductResponseDto {

    private Integer productId;
    private String productName;
    private Double price;
    private Integer quantity;
    private Double discount;
    private Double tax;
    private Integer sellerId;
    private Integer subCategoryId;
    private ProductStatus productStatus;

    public ProductResponseDto() {
    }

    public Integer getProductId() {

        return productId;
    }

    public void setProductId(final Integer productId) {

        this.productId = productId;
    }

    public String getProductName() {

        return productName;
    }

    public void setProductName(final String productName) {

        this.productName = productName;
    }

    public Double getPrice() {

        return price;
    }

    public void setPrice(final Double price) {

        this.price = price;
    }

    public Integer getQuantity() {

        return quantity;
    }

    public void setQuantity(final Integer quantity) {

        this.quantity = quantity;
    }

    public Double getDiscount() {

        return discount;
    }

    public void setDiscount(final Double discount) {

        this.discount = discount;
    }

    public Double getTax() {

        return tax;
    }

    public void setTax(final Double tax) {

        this.tax = tax;
    }

    public Integer getSellerId() {

        return sellerId;
    }

    public void setSellerId(
            final Integer sellerId) {

        this.sellerId = sellerId;
    }

    public Integer getSubCategoryId() {

        return subCategoryId;
    }

    public void setSubCategoryId(final Integer subCategoryId) {

        this.subCategoryId = subCategoryId;
    }

    public ProductStatus getProductStatus() {

        return productStatus;
    }

    public void setProductStatus(final ProductStatus productStatus) {

        this.productStatus = productStatus;
    }

    @Override
    public String toString() {

        return "ProductResponseDto{"
                + "productId=" + productId
                + ", productName='" + productName + '\''
                + ", price=" + price
                + ", quantity=" + quantity
                + ", discount=" + discount
                + ", tax=" + tax
                + ", sellerId=" + sellerId
                + ", subCategoryId=" + subCategoryId
                + ", productStatus=" + productStatus
                + '}';
    }
}