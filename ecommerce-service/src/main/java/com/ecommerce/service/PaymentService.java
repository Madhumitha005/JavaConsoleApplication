package com.ecommerce.service;

import com.ecommerce.model.Payment;
import com.ecommerce.repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class PaymentService {

    private final PaymentRepository memoryRepository;

    private final PaymentRepository jdbcRepository;

    public PaymentService(

            @Qualifier("inMemoryPaymentRepository")
            final PaymentRepository memoryRepository,

            @Qualifier("jdbcPaymentRepository")
            final PaymentRepository jdbcRepository) {

        this.memoryRepository = memoryRepository;
        this.jdbcRepository = jdbcRepository;
    }

    public boolean addPayment(final Payment payment) {

        if (payment == null) {
            return false;
        }

        boolean jdbcSaved = jdbcRepository.save(payment);

        if (!jdbcSaved) {
            return false;
        }

        boolean memorySaved = memoryRepository.save(payment);
        return memorySaved;
    }

    public Payment getPaymentById(final int paymentId) {

        Payment payment = jdbcRepository.findById(paymentId);

        if (payment == null) {

            payment = memoryRepository.findById(paymentId);
        }
        return payment;
    }

    public Payment getPaymentByOrderId(final int orderId) {

        Payment payment = jdbcRepository.findByOrderId(orderId);

        if (payment == null) {

            payment = memoryRepository.findByOrderId(orderId);
        }
        return payment;
    }

    public Collection<Payment> viewPayments() {

        Map<Integer, Payment> payments = new LinkedHashMap<>();

        Collection<Payment> jdbcPayments = jdbcRepository.findAll();

        if (jdbcPayments != null) {

            for (Payment payment : jdbcPayments) {

                payments.put(payment.getPaymentId(), payment);
            }
        }

        Collection<Payment> memoryPayments = memoryRepository.findAll();

        if (memoryPayments != null) {

            for (Payment payment : memoryPayments) {

                payments.putIfAbsent(payment.getPaymentId(), payment);
            }
        }
        return payments.values();
    }
}