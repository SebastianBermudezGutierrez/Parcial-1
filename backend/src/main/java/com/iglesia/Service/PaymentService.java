package com.iglesia.Service;

import com.iglesia.Payment;
import com.iglesia.PaymentStatus;
import com.iglesia.PaymentType;
import com.iglesia.PaymentRepository;
import com.iglesia.strategy.PaymentStrategyFactory;
import com.iglesia.event.DomainEventPublisher;
import com.iglesia.dto.PaymentDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentStrategyFactory strategyFactory;
    private final DomainEventPublisher eventPublisher;

    public PaymentService(PaymentRepository paymentRepository,
                         PaymentStrategyFactory strategyFactory,
                         DomainEventPublisher eventPublisher) {
        this.paymentRepository = paymentRepository;
        this.strategyFactory = strategyFactory;
        this.eventPublisher = eventPublisher;
    }

    public PaymentDTO createPayment(PaymentType type, BigDecimal amount, Long referenceId) {
        Payment payment = new Payment();
        payment.setType(type);
        payment.setAmount(amount);
        payment.setReferenceId(referenceId);
        payment.setStatus(PaymentStatus.INICIADO);
        payment.setAttempts(0);
        
        Payment savedPayment = paymentRepository.save(payment);
        return PaymentDTO.from(savedPayment);
    }

    @Transactional(readOnly = true)
    public List<PaymentDTO> getAllPayments(PaymentStatus status) {
        List<Payment> payments = status == null ? 
            paymentRepository.findAll() : 
            paymentRepository.findAllByStatus(status);
        return payments.stream().map(PaymentDTO::from).toList();
    }

    public PaymentDTO confirmPayment(Long id) {
        Payment payment = findPayment(id);
        
        // Usar Strategy Pattern para procesar el pago
        PaymentDTO result = strategyFactory.getStrategy(payment.getType()).process(payment);
        
        // Guardar el pago actualizado
        paymentRepository.save(payment);
        
        // Publicar evento de dominio
        eventPublisher.publishPaymentProcessed(payment);
        
        return result;
    }

    public PaymentDTO failPayment(Long id) {
        Payment payment = findPayment(id);

        if (payment.getStatus() == PaymentStatus.CONFIRMADO) {
            throw new RuntimeException("El pago ya fue confirmado");
        }

        payment.setAttempts(payment.getAttempts() + 1);
        payment.setStatus(PaymentStatus.FALLIDO);
        
        Payment savedPayment = paymentRepository.save(payment);
        eventPublisher.publishPaymentProcessed(savedPayment);
        
        return PaymentDTO.from(savedPayment);
    }

    public PaymentDTO retryPayment(Long id) {
        Payment payment = findPayment(id);

        if (payment.getStatus() != PaymentStatus.FALLIDO) {
            throw new RuntimeException("Solo se reintenta un pago fallido");
        }

        if (payment.getAttempts() >= 3) {
            throw new RuntimeException("Se superó el máximo de reintentos");
        }

        payment.setStatus(PaymentStatus.INICIADO);
        Payment savedPayment = paymentRepository.save(payment);
        
        return PaymentDTO.from(savedPayment);
    }

    private Payment findPayment(Long id) {
        return paymentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
    }
}
