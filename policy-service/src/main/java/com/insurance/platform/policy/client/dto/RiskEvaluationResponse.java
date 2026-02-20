package com.insurance.platform.policy.client.dto;

public class RiskEvaluationResponse {

    private boolean approved;
    private String riskCategory;

    // Default constructor (needed for RestTemplate)
    public RiskEvaluationResponse() {
    }

    // Constructor for manual creation (fallback)
    public RiskEvaluationResponse(boolean approved, String riskCategory) {
        this.approved = approved;
        this.riskCategory = riskCategory;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getRiskCategory() {
        return riskCategory;
    }

    public void setRiskCategory(String riskCategory) {
        this.riskCategory = riskCategory;
    }
}