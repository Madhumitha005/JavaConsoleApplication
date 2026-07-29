package com.ecommerce.view.customer;

import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ecommerce.common.enums.PaymentMethod;
import com.ecommerce.common.enums.PaymentStatus;
import com.ecommerce.common.util.InputUtil;
import com.ecommerce.controller.PaymentController;
import com.ecommerce.model.Payment;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@Component
public class CustomerPaymentView {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerPaymentView.class);

    private final Scanner scanner;
    private final PaymentController paymentController;
    private final Validator validator;

    public CustomerPaymentView(final PaymentController paymentController) {

        this.paymentController = Objects.requireNonNull(paymentController, "PaymentController cannot be null.");
        this.scanner = InputUtil.getInstance().getScanner();
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    public boolean processPayment(final int orderId, final double amount) {

        LOGGER.info("Starting payment process. " + "Order ID: {}", orderId);

        if (orderId <= 0) {

            System.out.println("Invalid Order ID.");

            return false;
        }

        if (amount <= 0) {

            System.out.println("Invalid Payment Amount.");

            return false;
        }

        System.out.println("\n========== PAYMENT ==========");
        System.out.println("Order ID : " + orderId);
        System.out.println("Amount   : $" + amount);

        PaymentMethod paymentMethod = selectPaymentMethod();

        if (paymentMethod == null) {

            return false;
        }

        Payment payment = new Payment();

        payment.setOrderId(orderId);
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);

        if (paymentMethod == PaymentMethod.CASH_ON_DELIVERY) {
            payment.setPaymentStatus(PaymentStatus.PENDING);
            payment.setTransactionId("COD-" + orderId + "-" + System.currentTimeMillis());

        } else {

            payment.setPaymentStatus(PaymentStatus.SUCCESS);
            payment.setTransactionId(generateTransactionId(orderId));
        }

        if (!validatePayment(payment)) {

            LOGGER.warn("Payment validation failed. " + "Order ID: {}", orderId);

            return false;
        }

        boolean paymentSaved = paymentController.addPayment(payment);

        if (!paymentSaved) {

            LOGGER.error("Payment save failed. " + "Order ID: {}", orderId);

            System.out.println("\nPayment Failed.");
            return false;
        }

        LOGGER.info("Payment saved successfully. " + "Order ID: {}", orderId);

        System.out.println("\n========== PAYMENT DETAILS ==========");
        System.out.println("Order ID       : " + orderId);
        System.out.println("Amount         : $" + amount);
        System.out.println("Payment Method : " + paymentMethod);
        System.out.println("Transaction ID : " + payment.getTransactionId());
        System.out.println("Payment Status : " + payment.getPaymentStatus());

        if (paymentMethod == PaymentMethod.CASH_ON_DELIVERY) {

            System.out.println("\nOrder placed successfully.");
            System.out.println("Payment will be collected " + "during delivery.");

        } else {

            System.out.println("\nPayment Successful.");
        }
        return true;
    }

    private PaymentMethod selectPaymentMethod() {

        System.out.println("\n========== PAYMENT METHOD ==========");

        PaymentMethod[] methods = PaymentMethod.values();

        for (PaymentMethod method : methods) {

            System.out.println(method.getId() + ". " + method);
        }

        while (true) {

            System.out.print("Enter Payment Method : ");

            try {

                int methodId = Integer.parseInt(scanner.nextLine().trim());

                PaymentMethod method = PaymentMethod.fromId(methodId);

                LOGGER.info("Selected payment method: {}", method);

                return method;

            } catch (NumberFormatException exception) {

                System.out.println("Please enter a valid number.");

            } catch (RuntimeException exception) {

                System.out.println("Invalid Payment Method.");
            }
        }
    }

    private String generateTransactionId(final int orderId) {

        // Universal Unique Identifier
        return UUID.randomUUID().toString();
    }

    private boolean validatePayment(final Payment payment) {

        Set<ConstraintViolation<Payment>> violations = validator.validate(payment);

        if (violations.isEmpty()) {

            return true;
        }

        System.out.println("\nPlease correct " + "the following errors:");

        for (ConstraintViolation<Payment> violation : violations) {

            System.out.println("- " + violation.getMessage());
        }
        return false;
    }
}
