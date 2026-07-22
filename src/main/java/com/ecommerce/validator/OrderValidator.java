package com.ecommerce.validator;

import com.ecommerce.exception.ValidationException;
import com.ecommerce.model.Order;

public final class OrderValidator {

    private static OrderValidator instance;

    private OrderValidator() {
    }

    public static OrderValidator getInstance() {

        if (instance == null) {
            instance = new OrderValidator();
        }

        return instance;
    }

    // Validate Complete Order
    public void validate(Order order) {

        if (order == null) {
            throw new ValidationException("Order cannot be null.");
        }

        validateUserId(order.getUserId());
        validateCustomerName(order.getCustomerName());
        validatePhone(order.getPhone());
        validateAddress(order.getAddress());
        validateTotalAmount(order.getTotalAmount());
    }

    // Validate User ID
    public void validateUserId(int userId) {

        if (userId <= 0) {
            throw new ValidationException("Invalid User ID.");
        }
    }

    // Validate Customer Name
    public void validateCustomerName(String customerName) {

        if (customerName == null || customerName.trim().isEmpty()) {
            throw new ValidationException("Customer name cannot be empty.");
        }

        if (!customerName.matches("[A-Za-z ]{3,30}")) {
            throw new ValidationException("Invalid customer name.");
        }
    }

    // Validate Phone Number
    public void validatePhone(String phone) {

        if (phone == null || phone.trim().isEmpty()) {
            throw new ValidationException("Phone number cannot be empty.");
        }

        if (!phone.matches("\\d{10}")) {
            throw new ValidationException("Invalid phone number.");
        }
    }

    // Validate Address
    public void validateAddress(String address) {

        if (address == null || address.trim().isEmpty()) {
            throw new ValidationException("Address cannot be empty.");
        }

        if (address.trim().length() < 5) {
            throw new ValidationException("Address is too short.");
        }
    }

    // Validate Total Amount
    public void validateTotalAmount(double totalAmount) {

        if (totalAmount <= 0) {
            throw new ValidationException("Total amount must be greater than zero.");
        }
    }
}