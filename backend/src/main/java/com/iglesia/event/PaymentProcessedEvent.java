package com.iglesia.event;

import com.iglesia.PaymentType;
import com.iglesia.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentProcessedEvent {
    private final Long paymentId;
    private final PaymentType type;
    private final BigDecimal amount;
    private final PaymentStatus status;
    private final LocalDateTime processedAt;

    public PaymentProcessedEvent(Long paymentId, PaymentType type, BigDecimal amount, PaymentStatus status) {
        this.paymentId = paymentId;
        this.type = type;
        this.amount = amount;
        this.status = status;
        this.processedAt = LocalDateTime.now();
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public PaymentType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }
}
