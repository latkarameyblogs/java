package com.insurance.platform.policy.client;

import com.insurance.platform.policy.client.dto.RiskEvaluationRequest;
import com.insurance.platform.policy.client.dto.RiskEvaluationResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UnderwritingClient {

    private final RestTemplate restTemplate;

    public UnderwritingClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "underwritingService", fallbackMethod = "fallbackUnderwriting")
    public RiskEvaluationResponse evaluateRisk(RiskEvaluationRequest request) {

        String url = "http://localhost:8083/underwriting/evaluate";

        return restTemplate.postForObject(
                url,
                request,
                RiskEvaluationResponse.class
        );
    }

    public RiskEvaluationResponse fallbackUnderwriting(RiskEvaluationRequest request, Exception ex) {
        System.out.println("Underwriting service unavailable. Fallback triggered.");

        // Safe default: reject if underwriting is unavailable
        return new RiskEvaluationResponse(false, "SERVICE_UNAVAILABLE");
    }
}