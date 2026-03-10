package com.insurance.platform.payment.event;

public class PaymentCompletedEvent {

    private String sagaID;
    private Long policyId;
    private boolean success;

    public PaymentCompletedEvent() {}

    public PaymentCompletedEvent(String sagaID,Long policyId, boolean success) {
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