package com.ecommerce.repository;

import com.ecommerce.model.Payment;
import java.util.Collection;

public interface PaymentRepository {

    boolean save(final Payment payment);

    boolean update(final Payment payment);

    boolean delete(final int paymentId);

    Payment findById(final int paymentId);

    Payment findByOrderId(final int orderId);

    Collection<Payment> findAll();
}