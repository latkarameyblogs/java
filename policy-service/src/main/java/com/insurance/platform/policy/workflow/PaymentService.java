package com.insurance.platform.policy.workflow;

import org.springframework.stereotype.Component;

@Component
public class PaymentService {

    public boolean processPayment(Long customerId) {

        // Mock logic:
        // Simulate payment success
        return true;
    }
}
