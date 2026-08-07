/*
 * PaymentController.java
 *
 * Version 1.2
 *
 * July 26, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.

package com.ecommerce.order.controller;

import com.ecommerce.order.entity.Payment;
import com.ecommerce.order.service.PaymentService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Objects;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(final PaymentService paymentService) {

        this.paymentService = Objects.requireNonNull(paymentService, "PaymentService cannot be null.");
    }

    // Add Payment
    @PostMapping
    public ResponseEntity<Boolean> addPayment(@RequestBody final Payment payment) {

        if (payment == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.addPayment(payment));
    }

    // Find Payment By Id
    @GetMapping("/{paymentId}")
    public ResponseEntity<Payment> findById(@PathVariable final int paymentId) {

        if (paymentId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Payment payment = paymentService.getPaymentById(paymentId);
        if (payment == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(payment);
    }

    // Find Payment By Order Id
    @GetMapping("/order/{orderId}")
    public ResponseEntity<Payment> findByOrderId(@PathVariable final int orderId) {

        if (orderId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Payment payment = paymentService.getPaymentByOrderId(orderId);
        if (payment == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(payment);
    }

    // View All Payments
    @GetMapping
    public ResponseEntity<Collection<Payment>> viewPayments() {

        return ResponseEntity.ok(paymentService.viewPayments());
    }
}
*/