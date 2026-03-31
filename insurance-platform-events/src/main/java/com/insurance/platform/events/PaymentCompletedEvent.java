package com.insurance.platform.events;

public class PaymentCompletedEvent implements SagaEvent{

    private String sagaID;
    private Long policyId;
    private boolean success;

    private String eventId;

    public String getEventId() {
        return eventId;
    }

    public String sagaId;

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public PaymentCompletedEvent() {
    }

    public PaymentCompletedEvent(String sagaID, Long policyId, boolean success,String userId) {
        this.policyId = policyId;
        this.success = success;
        this.userId =userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String userId;
    public Long getPolicyId() {
        return policyId;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getSagaId() {
        return sagaId;
    }
}