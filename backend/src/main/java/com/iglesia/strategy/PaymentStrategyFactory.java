package com.iglesia.strategy;

import com.iglesia.PaymentType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentStrategyFactory {
    private final List<PaymentStrategy> strategies;

    public PaymentStrategyFactory(List<PaymentStrategy> strategies) {
        this.strategies = strategies;
    }

    public PaymentStrategy getStrategy(PaymentType type) {
        return strategies.stream()
            .filter(strategy -> strategy.supports(type))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No strategy found for payment type: " + type));
    }
}
