package com.insurance.platform.policyquery.event;

public class PaymentCompletedEvent {

    private String sagaID;
    private Long policyId;
    private boolean success;

    private String eventId;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public PaymentCompletedEvent() {
    }

    public PaymentCompletedEvent(String sagaID, Long policyId, boolean success) {
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