package com.insurance.platform.policy.client.dto;

import java.math.BigDecimal;

public class RiskEvaluationRequest {

    private BigDecimal premiumAmount;
    private String policyType;

    public RiskEvaluationRequest(BigDecimal premiumAmount, String policyType) {
        this.premiumAmount = premiumAmount;
        this.policyType = policyType;
    }

    public BigDecimal getPremiumAmount() {
        return premiumAmount;
    }

    public String getPolicyType() {
        return policyType;
    }
}