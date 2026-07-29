package com.ecommerce.repository.memory;

import com.ecommerce.common.util.IdGenerator;
import com.ecommerce.model.Payment;
import com.ecommerce.repository.PaymentRepository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

@Repository("inMemoryPaymentRepository")
public class InMemoryPaymentRepository
        implements PaymentRepository {

    private final Collection<Payment> payments;

    public InMemoryPaymentRepository() {

        this.payments = new ArrayList<>();
    }

    @Override
    public boolean save(final Payment payment) {

        if (payment == null) {
            return false;
        }
        payment.setPaymentId(IdGenerator.getInstance().nextPaymentId());

        return payments.add(payment);
    }

    @Override
    public Payment findById(final int paymentId) {

        if (paymentId <= 0) {
            return null;
        }

        for (Payment payment : payments) {

            if (payment.getPaymentId() == paymentId) {

                return payment;
            }
        }
        return null;
    }

    @Override
    public Payment findByOrderId(final int orderId) {

        if (orderId <= 0) {
            return null;
        }

        for (Payment payment : payments) {

            if (payment.getOrderId() == orderId) {

                return payment;
            }
        }

        return null;
    }

    @Override
    public Collection<Payment> findAll() {

        return new ArrayList<>(payments);
    }

    @Override
    public boolean update(final Payment updatedPayment) {

        if (updatedPayment == null) {
            return false;
        }

        for (Payment payment : payments) {

            if (payment.getPaymentId() == updatedPayment.getPaymentId()) {

                payment.setOrderId(updatedPayment.getOrderId());
                payment.setPaymentMethod(updatedPayment.getPaymentMethod());
                payment.setPaymentStatus(updatedPayment.getPaymentStatus());
                payment.setAmount(updatedPayment.getAmount());
                payment.setTransactionId(updatedPayment.getTransactionId());

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean delete(final int paymentId) {

        return payments.removeIf(payment -> payment.getPaymentId() == paymentId);
    }
}