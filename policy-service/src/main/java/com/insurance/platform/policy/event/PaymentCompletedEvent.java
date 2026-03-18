package com.insurance.platform.policy.event;

public class PaymentCompletedEvent {

    private Long policyId;
    private boolean success;

    public PaymentCompletedEvent() {
    }

    public Long getPolicyId() {
        return policyId;
    }

    public boolean isSuccess() {
        return success;
    }
}