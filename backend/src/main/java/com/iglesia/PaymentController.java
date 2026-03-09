package com.iglesia;

import com.iglesia.Service.PaymentService;
import com.iglesia.dto.PaymentDTO;
import com.iglesia.PaymentType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public PaymentDTO create(@RequestBody PaymentRequest request) {
        // Crear pago básico para pruebas
        return paymentService.createPayment(request.type(), request.amount(), request.referenceId());
    }

    @GetMapping
    public List<PaymentDTO> list(@RequestParam(name = "status", required = false) PaymentStatus status) {
        return paymentService.getAllPayments(status);
    }

    @PostMapping("/{id}/confirm")
    public PaymentDTO confirm(@PathVariable Long id) {
        return paymentService.confirmPayment(id);
    }

    @PostMapping("/{id}/fail")
    public PaymentDTO fail(@PathVariable Long id) {
        return paymentService.failPayment(id);
    }

    @PostMapping("/{id}/retry")
    public PaymentDTO retry(@PathVariable Long id) {
        return paymentService.retryPayment(id);
    }

    public record PaymentRequest(
        PaymentType type,
        BigDecimal amount,
        Long referenceId
    ) {}
}
