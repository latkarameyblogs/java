package com.insurance.platform.payment.service;

import com.insurance.platform.payment.dto.PaymentRequest;
import com.insurance.platform.payment.dto.PaymentResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentService {

    public boolean processApprovedPolicy(Long policyId) {
        // Simulated payment success
        return true;
    }

    public PaymentResponse processPayment(PaymentRequest request) {
        if (request.getAmount().doubleValue() > 50000) {
            return new PaymentResponse(false, "Payment amount exceeds limit");
        }
        return new PaymentResponse(true, "Payment processed successfully");
    }
}