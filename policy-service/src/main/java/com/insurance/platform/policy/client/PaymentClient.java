package com.insurance.platform.policy.client;

import com.insurance.platform.policy.client.dto.PaymentRequest;
import com.insurance.platform.policy.client.dto.PaymentResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
public class PaymentClient {

    private final RestTemplate restTemplate;

    public PaymentClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "paymentService", fallbackMethod = "fallbackPayment")
    public PaymentResponse processPayment(Long customerId, BigDecimal amount) {

        PaymentRequest request = new PaymentRequest();
        request.setCustomerId(customerId);
        request.setAmount(amount);

        String url = "http://localhost:8084/payments/process";

        return restTemplate.postForObject(url, request, PaymentResponse.class);
    }

    public PaymentResponse fallbackPayment(Long customerId, BigDecimal amount, Exception ex) {
        System.out.println("Payment service unavailable. Fallback triggered.");

        return new PaymentResponse(false, "PAYMENT_SERVICE_UNAVAILABLE");
    }
}