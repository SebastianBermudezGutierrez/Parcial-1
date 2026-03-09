package com.iglesia.strategy;

import com.iglesia.Payment;
import com.iglesia.dto.PaymentDTO;

public interface PaymentStrategy {
    PaymentDTO process(Payment payment);
    boolean supports(com.iglesia.PaymentType type);
}
