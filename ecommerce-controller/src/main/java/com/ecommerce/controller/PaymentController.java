package com.ecommerce.controller;

import com.ecommerce.model.Payment;
import com.ecommerce.service.PaymentService;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(final PaymentService paymentService) {

        this.paymentService = paymentService;
    }

   // Add Payment
    public boolean addPayment(final Payment payment) {

        if (payment == null) {
            return false;
        }

        return paymentService.addPayment(payment);
    }

    // Find Payment By Id
    public Payment findById(final int paymentId) {

        if (paymentId <= 0) {
            return null;
        }

        return paymentService.getPaymentById(paymentId);
    }

    // Find Payment By Order Id
    public Payment findByOrderId(final int orderId) {

        if (orderId <= 0) {
            return null;
        }

        return paymentService.getPaymentByOrderId(orderId);
    }

    // View All Payments
    public Collection<Payment> viewPayments() {

        return paymentService.viewPayments();
    }
}