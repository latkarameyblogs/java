package com.insurance.platform.underwriting.service;

import com.insurance.platform.underwriting.dto.RiskEvaluationRequest;
import com.insurance.platform.underwriting.dto.RiskEvaluationResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UnderwritingService {

    public RiskEvaluationResponse evaluateRisk(RiskEvaluationRequest request) {

        BigDecimal premium = request.getPremiumAmount();
        String policyType = request.getPolicyType();

        // Simple professional risk rules simulation

        if ("HEALTH".equalsIgnoreCase(policyType)) {
            if (premium.compareTo(BigDecimal.valueOf(10000)) > 0) {
                return new RiskEvaluationResponse(false, "HIGH");
            }
            return new RiskEvaluationResponse(true, "MEDIUM");
        }

        if ("MOTOR".equalsIgnoreCase(policyType)) {
            if (premium.compareTo(BigDecimal.valueOf(50000)) > 0) {
                return new RiskEvaluationResponse(false, "HIGH");
            }
            return new RiskEvaluationResponse(true, "LOW");
        }

        // Default case
        return new RiskEvaluationResponse(true, "LOW");
    }
}