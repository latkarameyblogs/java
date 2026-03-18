package com.insurance.platform.policy.event;

public class RiskEvaluatedEvent {

    private Long policyId;
    private boolean approved;
    private String reason;

    public RiskEvaluatedEvent() {
    }

    public RiskEvaluatedEvent(Long policyId, boolean approved, String reason) {
        this.policyId = policyId;
        this.approved = approved;
        this.reason = reason;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public boolean isApproved() {
        return approved;
    }

    public String getReason() {
        return reason;
    }
}