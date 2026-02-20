package com.insurance.platform.underwriting.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class RiskEvaluationRequest {

    @NotNull
    private BigDecimal premiumAmount;

    @NotNull
    private String policyType;

    public BigDecimal getPremiumAmount() {
        return premiumAmount;
    }

    public void setPremiumAmount(BigDecimal premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }
}