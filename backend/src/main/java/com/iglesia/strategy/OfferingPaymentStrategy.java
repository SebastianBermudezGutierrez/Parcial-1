package com.iglesia.strategy;

import com.iglesia.Payment;
import com.iglesia.PaymentType;
import com.iglesia.Offering;
import com.iglesia.OfferingStatus;
import com.iglesia.OfferingRepository;
import com.iglesia.dto.PaymentDTO;
import org.springframework.stereotype.Component;

@Component
public class OfferingPaymentStrategy implements PaymentStrategy {
    private final OfferingRepository offeringRepository;

    public OfferingPaymentStrategy(OfferingRepository offeringRepository) {
        this.offeringRepository = offeringRepository;
    }

    @Override
    public PaymentDTO process(Payment payment) {
        payment.setStatus(com.iglesia.PaymentStatus.CONFIRMADO);
        
        Offering offering = offeringRepository.findById(payment.getReferenceId())
            .orElseThrow(() -> new RuntimeException("Ofrenda no encontrada"));
        
        offering.setStatus(OfferingStatus.REGISTRADA);
        offeringRepository.save(offering);
        
        return PaymentDTO.from(payment);
    }

    @Override
    public boolean supports(PaymentType type) {
        return type == PaymentType.OFRENDA;
    }
}
