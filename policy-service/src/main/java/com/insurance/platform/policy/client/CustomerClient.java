package com.insurance.platform.policy.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@Component
public class CustomerClient {

    private final RestTemplate restTemplate;

    public CustomerClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "customerService", fallbackMethod = "fallbackCustomerCheck")
    public boolean customerExists(Long customerId) {
        String url = "http://localhost:8081/customers/" + customerId;
        restTemplate.getForObject(url, Object.class);
        return true;
    }

    public boolean fallbackCustomerCheck(Long customerId, Exception ex) {
        System.out.println("Customer service unavailable. Fallback triggered.");
        return false;
    }

}
