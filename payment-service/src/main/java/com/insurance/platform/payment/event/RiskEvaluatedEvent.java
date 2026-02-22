package com.insurance.platform.payment.event;

public class RiskEvaluatedEvent {

    private Long policyId;
    private boolean approved;
    private String reason;

    public RiskEvaluatedEvent() {}

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