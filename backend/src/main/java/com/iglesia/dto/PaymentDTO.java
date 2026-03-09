package com.iglesia.dto;

import com.iglesia.PaymentType;
import com.iglesia.PaymentStatus;
import java.math.BigDecimal;

public record PaymentDTO(
    Long id,
    PaymentType type,
    PaymentStatus status,
    BigDecimal amount,
    int attempts,
    Long referenceId
) {
    public static PaymentDTO from(com.iglesia.Payment payment) {
        return new PaymentDTO(
            payment.getId(),
            payment.getType(),
            payment.getStatus(),
            payment.getAmount(),
            payment.getAttempts(),
            payment.getReferenceId()
        );
    }
}
