package com.insurance.platform.payment.service;

import com.insurance.platform.payment.dto.PaymentRequest;
import com.insurance.platform.payment.dto.PaymentResponse;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    /**
     * Process payment for a customer
     * Currently mocked logic: approve all payments under 50,000
     */
    public PaymentResponse processPayment(PaymentRequest request) {

        if (request.getAmount().doubleValue() > 50000) {
            return new PaymentResponse(false, "Payment amount exceeds limit");
        }

        // Simulate payment processing
        return new PaymentResponse(true, "Payment processed successfully");
    }
}