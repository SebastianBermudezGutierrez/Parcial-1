package com.iglesia.strategy;

import com.iglesia.Payment;
import com.iglesia.PaymentType;
import com.iglesia.Enrollment;
import com.iglesia.EnrollmentStatus;
import com.iglesia.EnrollmentRepository;
import com.iglesia.dto.PaymentDTO;
import org.springframework.stereotype.Component;

@Component
public class CoursePaymentStrategy implements PaymentStrategy {
    private final EnrollmentRepository enrollmentRepository;

    public CoursePaymentStrategy(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public PaymentDTO process(Payment payment) {
        payment.setStatus(com.iglesia.PaymentStatus.CONFIRMADO);
        
        Enrollment enrollment = enrollmentRepository.findById(payment.getReferenceId())
            .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));
        
        enrollment.setStatus(EnrollmentStatus.PAGADA);
        enrollmentRepository.save(enrollment);
        
        return PaymentDTO.from(payment);
    }

    @Override
    public boolean supports(PaymentType type) {
        return type == PaymentType.INSCRIPCION_CURSO;
    }
}
