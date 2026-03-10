package com.insurance.platform.payment.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

//    private final PaymentService paymentService;
//
//    public PaymentController(PaymentService paymentService) {
//        this.paymentService = paymentService;
//    }
//
//    @PostMapping("/process")
//    public PaymentResponse processPayment(@Valid @RequestBody PaymentRequest request) {
//        return paymentService.processPayment(request);
//    }
}