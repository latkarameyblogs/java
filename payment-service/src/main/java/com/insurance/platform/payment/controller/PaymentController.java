package com.insurance.platform.payment.controller;

import com.insurance.platform.payment.dto.PaymentRequest;
import com.insurance.platform.payment.dto.PaymentResponse;
import com.insurance.platform.payment.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/process")
    public PaymentResponse processPayment(@Valid @RequestBody PaymentRequest request) {
        return paymentService.processPayment(request);
    }
}