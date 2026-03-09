package com.iglesia.repository;

import com.iglesia.Payment;
import com.iglesia.PaymentStatus;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository {
    Payment save(Payment payment);
    Optional<Payment> findById(Long id);
    List<Payment> findAll();
    List<Payment> findAllByStatus(PaymentStatus status);
    void deleteById(Long id);
    boolean existsById(Long id);
}
