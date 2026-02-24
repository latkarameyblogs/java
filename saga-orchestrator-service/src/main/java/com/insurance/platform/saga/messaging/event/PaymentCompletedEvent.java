package com.insurance.platform.saga.messaging.event;

public class PaymentCompletedEvent {

    private Long policyId;
    private boolean success;

    public PaymentCompletedEvent() {}

    public PaymentCompletedEvent(Long policyId, boolean success) {
        this.policyId = policyId;
        this.success = success;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public boolean isSuccess() {
        return success;
    }
}