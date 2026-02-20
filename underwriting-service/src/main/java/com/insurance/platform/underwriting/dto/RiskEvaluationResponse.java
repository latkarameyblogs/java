package com.insurance.platform.underwriting.dto;

public class RiskEvaluationResponse {

    private boolean approved;
    private String riskCategory;

    public RiskEvaluationResponse(boolean approved, String riskCategory) {
        this.approved = approved;
        this.riskCategory = riskCategory;
    }

    public boolean isApproved() {
        return approved;
    }

    public String getRiskCategory() {
        return riskCategory;
    }
}